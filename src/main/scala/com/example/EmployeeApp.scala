package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.example.domain.repositories.EmployeeRepository
import com.example.infrastructure.api.ExampleAPI
import com.example.application.env.Environment
import com.example.application.publishers.EventProducer
import com.example.infrastructure.logger.Logger
import com.example.infrastructure.publishers.EventProducerKafkaInterpreter
import com.example.infrastructure.publishers.config.KafkaConfig
import com.example.infrastructure.repositories.slick.config.DbConnection
import com.example.infrastructure.repositories.slick.interpreter.EmployeeRepositorySlickInterpreter
import monix.execution.Scheduler
import org.flywaydb.core.Flyway
import slick.jdbc.MySQLProfile.api.Database

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object EmployeeApp extends App with Logger {

  logger.info("Starting Example1 Microservice")

  private val systemName = "Example1-Microservice"
  implicit val system: ActorSystem = ActorSystem(systemName)
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val scheduler: Scheduler = Scheduler(system.dispatcher)

  logger.info("Loading Configuration for Example Microservice")

  val db = DbConnection.db
  val kafkaConfig = loadKafkaConfig

  startApplication(
    startAPIApplication(db, kafkaConfig.get)
  )

  def startMigrationDb(): Unit = {
    logger.info("Start migration db...")
    val resultMigration: Int = dbSetup(
      "jdbc:mysql://localhost:3306/exampledb2?useUnicode=true&characterEncoding=UTF-8&useSSL=false",
      "root",
      "root"
    )
    logger.info(s"Result migration db: $resultMigration")
  }

  def dbSetup(url: String, user: String, password: String): Int = {
    val flyway = Flyway.configure().dataSource(url, user, password).load()

    flyway.repair()
    flyway.clean()
    flyway.migrate()
  }

  def loadKafkaConfig: Option[KafkaConfig] = {
    val resultFor = for {
      kafkConfig <- KafkaConfig.kafkaConfig
    } yield kafkConfig

    resultFor match {
      case Success(kafkaConf) => Some(kafkaConf)
      case Failure(error) =>
        logger.error(s"Error loading kafka config $error")
        None
    }
  }

  def createEnvironment(database: Database, kafkaConf: KafkaConfig): Environment = {
    new Environment {
      override val employeeRepository: EmployeeRepository = new EmployeeRepositorySlickInterpreter(database)
      override val eventProducer: EventProducer = new EventProducerKafkaInterpreter(kafkaConf)
    }
  }

  def startAPIApplication(database: Database, kafkaConf: KafkaConfig): ExampleAPI = {
    startMigrationDb()
    logger.debug(s"start startAPIApplication method")
    val environment = createEnvironment(database, kafkaConf)
    new ExampleAPI(environment)
  }

  def startApplication(exampleAPI: ExampleAPI): Unit = {
    logger.info("StartApplication in Example microservice")
    val applicationPort = "8083"

    Http().bindAndHandle(exampleAPI.routes, "0.0.0.0", applicationPort.toInt) onComplete {
      case Success(Http.ServerBinding(localAddress)) =>
        logger.info(s"Http service started - Listening for HTTP on $localAddress")
      case Failure(exception) =>
        logger.error(s"There was an exception bindings http service  $exception")
        Await.ready(system.terminate(), 10.seconds)
    }
  }
}
