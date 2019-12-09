package com.example.infrastructure.publishers

import java.util.Properties

import com.example.application.dtos.{EmployeeCreated, Event}
import com.example.application.publishers.{EmployeeEvent, EventProducer, EventType}
import com.example.infrastructure.logger.Logger
import com.example.infrastructure.publishers.config.KafkaConfig
import io.circe.generic.semiauto.deriveEncoder
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import io.circe.syntax._
import monix.execution.Scheduler
import monix.kafka
import monix.kafka.KafkaProducerConfig

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.{Failure, Success}

class EventProducerKafkaInterpreter(kafkaConfig: KafkaConfig) extends EventProducer with Logger {
  override def publishEvent(eventType: EventType, key: String, event: String): Unit = {
    val props = new Properties()
    props.put("bootstrap.servers", kafkaConfig.brokers)
    props.put("key.serializer", kafkaConfig.keySerializer)
    props.put("value.serializer", kafkaConfig.valueSerializer)

    val topic = getTopic(eventType)
    val producer = new KafkaProducer[String, String](props)
    val record = new ProducerRecord[String, String](topic, key, event)

    producer.send(record)
    producer.close()
  }

  override def publishEvent2(eventType: EventType, key: String, event: Event): Future[Unit] = {
    implicit val scheduler: Scheduler = Scheduler.global

    val producerCfg = KafkaProducerConfig.default.copy(
      bootstrapServers = kafkaConfig.brokers.split(",").toList,
      retries = 3,
      retryBackoffTime = 5.seconds,
      reconnectBackoffTime = 20.seconds
    )
    val topic = getTopic(eventType)
    val eventString = getSerializedEvent(eventType, event)

    logger.debug(s"Topic: $topic")
    logger.debug(s"Message: $eventString")

    val producer = kafka.KafkaProducer[String, String](producerCfg, scheduler)

    producer.send(topic, eventString).runToFuture.transformWith {
      case Success(value) =>
        logger.debug(s"Metadata: $value")
        producer.close().runToFuture
      case Failure(error) =>
        logger.error(s"Error: $error")
        producer.close().runToFuture
    }
  }

  private def getSerializedEvent(eventType: EventType, event: Event): String = {
    eventType match {
      case EmployeeEvent =>
        val employeeEvent: EmployeeCreated = event.asInstanceOf[EmployeeCreated]
        employeeEvent.asJson(deriveEncoder[EmployeeCreated]).toString
    }
  }

  private def getTopic(eventType: EventType): String = {
    eventType match {
      case EmployeeEvent => kafkaConfig.topicEmployee
      case _ => ""
    }
  }
}
