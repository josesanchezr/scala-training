package com.example.application.service

import com.example.domain.repositories.EmployeeRepository
import monix.eval.Task

import scala.concurrent.Future

trait EmployeeService[E, I] {
  def fetchEmployee(identificationNumber: I): Reader[EmployeeRepository, Task[Option[E]]]
  def storeEmployee(employee: E): Reader[EmployeeRepository, Task[Int]]
  def deleteEmployee(identificationNumber: I): Reader[EmployeeRepository, Future[Int]]
  def updateEmployee(employee: E): Reader[EmployeeRepository, Future[Int]]
}
