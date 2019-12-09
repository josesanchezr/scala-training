package com.example.infrastructure.publishers.config

import pureconfig.generic.auto._
import scala.util.Try

case class KafkaConfig(brokers: String,
                       keySerializer: String,
                       valueSerializer: String,
                       topicEmployee: String)

object KafkaConfig {
  val kafkaConfig: Try[KafkaConfig] = Try {
    pureconfig.loadConfigOrThrow[KafkaConfig]("kafka")
  }
}
