import sbt._

object Dependencies {
  val slickVersion = "3.3.1"
  val akkaHttpVersion = "10.1.8"
  val akkaVersion = "2.5.22"
  val akkaCirceVersion = "1.24.3"
  val circeVersion = "0.10.0"
  
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val cats = "org.typelevel" %% "cats-core" % "2.0.0-M1"
  lazy val slick = "com.typesafe.slick" %% "slick" % slickVersion
  lazy val hikaricp = "com.typesafe.slick" %% "slick-hikaricp" % slickVersion
  lazy val mysql = "mysql" % "mysql-connector-java" % "6.0.6"
  lazy val flywaydb = "org.flywaydb" % "flyway-core" % "5.2.4"
  lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion
  lazy val akka = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  lazy val akkaCirce = ("de.heikoseeberger" %% "akka-http-circe" % akkaCirceVersion)
    .exclude("com.typesafe.akka", "akka-actor_2.12")
  lazy val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
  lazy val circeParser = "io.circe" %% "circe-parser" % circeVersion
  lazy val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.10.2"
  lazy val swagger = "com.github.swagger-akka-http" %% "swagger-akka-http" % "1.0.0"
  lazy val monix = "io.monix" %% "monix" % "3.0.0-RC2"
  lazy val slf4jApi = "org.slf4j" % "slf4j-api" % "1.7.27"
  lazy val slf4jSimple = "org.slf4j" % "slf4j-simple" % "1.7.27"
  lazy val kafka = "org.apache.kafka" %% "kafka" % "2.1.0"
  lazy val monixKafka = "io.monix" %% "monix-kafka-1x" % "1.0.0-RC5"
}
