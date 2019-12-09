package com.example.application.service.interpreter

/*import akka.actor.{ActorRef, ActorSystem, Props}
import com.example.application.actors.EventProducerActor*/
import com.example.application.dtos.EmployeeCreated
import com.example.application.publishers.{EmployeeEvent, EventProducer}
import com.example.application.service.{EmployeeService, Reader}
import com.example.domain.repositories.EmployeeRepository
import com.example.domain.{ACTIVE, Employee, IdentificationNumber}
import com.example.infrastructure.logger.Logger
import monix.eval.Task
import monix.execution.Scheduler

import scala.concurrent.Future
import scala.util.{Failure, Success}

class EmployeeServiceInterpreter(eventProducer: EventProducer)(implicit scheduler: Scheduler)
  extends EmployeeService[Employee, IdentificationNumber] with Logger {

  //val system: ActorSystem = ActorSystem("EmployeeActorSystem")
  //val eventProducerActor: ActorRef = system.actorOf(Props(new EventProducerActor(eventProducer)))

  override def fetchEmployee(identificationNumber: IdentificationNumber): Reader[EmployeeRepository, Task[Option[Employee]]] = Reader {
    repo =>
      Task.fromFuture(repo.query(identificationNumber).filter(_.get.employeeStatus == ACTIVE))
  }

  override def storeEmployee(employee: Employee): Reader[EmployeeRepository, Task[Int]] = Reader {
    logger.debug(s"storeEmployee method started with $employee")
    repo => Task.fromFuture(
      repo.store(employee).transformWith {
        case Success(v) =>
          logger.debug("Employee stored")
          //eventProducerActor ! EmployeeCreated(id = employee.identificationNumber.identificationNumber, name = employee.name)
          val employeeEvent = EmployeeCreated(id = employee.identificationNumber.identificationNumber, name = employee.name)
          eventProducer.publishEvent2(EmployeeEvent, employeeEvent.id.toString, employeeEvent)
          Future.successful(v)
        case Failure(error) =>
          Future.failed(error)
      }
    )
  }

  override def deleteEmployee(identificationNumber: IdentificationNumber): Reader[EmployeeRepository, Future[Int]] = Reader {
    repo => repo.delete(identificationNumber)
  }

  override def updateEmployee(employee: Employee): Reader[EmployeeRepository, Future[Int]] = Reader {
    repo => repo.update(employee)
  }
}
