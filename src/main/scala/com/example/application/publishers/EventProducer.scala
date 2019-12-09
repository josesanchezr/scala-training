package com.example.application.publishers

import com.example.application.dtos.Event

import scala.concurrent.Future

trait EventProducer {
  def publishEvent(eventType: EventType, key: String, event: String): Unit
  def publishEvent2(eventType: EventType, key: String, event: Event): Future[Unit]
}
