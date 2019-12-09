package com.example.application.env

import com.example.application.publishers.EventProducer
import com.example.application.service.EmployeeService
import com.example.application.service.interpreter.EmployeeServiceInterpreter
import com.example.domain.{Employee, IdentificationNumber}
import com.example.domain.repositories.EmployeeRepository
import monix.execution.Scheduler.Implicits.global

trait Environment {
  lazy val employeeService: EmployeeService[Employee, IdentificationNumber] = new EmployeeServiceInterpreter(eventProducer)

  val employeeRepository: EmployeeRepository
  val eventProducer: EventProducer
}
