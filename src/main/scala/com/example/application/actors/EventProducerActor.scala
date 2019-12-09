package com.example.application.actors

import akka.actor.Actor
import com.example.application.dtos.EmployeeCreated
import com.example.application.publishers.{EmployeeEvent, EventProducer}
import com.example.infrastructure.logger.Logger
import io.circe.generic.semiauto._
import io.circe.syntax._

class EventProducerActor(eventProducer: EventProducer) extends Actor with Logger{

  override def receive: Receive = {
    case EmployeeCreated(id, name) => publishEmployeeEvent(id, name)
    case _ => logger.error("Message not identified.")
  }

  private def publishEmployeeEvent(id: Long, name: String): Unit = {
    implicit val employeeEventEncoder = deriveEncoder[EmployeeCreated]
    val employeeEvent = EmployeeCreated(id = id, name = name)
    val event = employeeEvent.asJson.toString

    logger.debug(s"Publishing employee event: $event")
    eventProducer.publishEvent(EmployeeEvent, id.toString, event)
  }
}
