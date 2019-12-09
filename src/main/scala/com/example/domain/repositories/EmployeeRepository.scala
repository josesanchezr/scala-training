package com.example.domain.repositories

import com.example.domain.{Employee, IdentificationNumber}

import scala.concurrent.Future

trait EmployeeRepository extends Repository[Employee, IdentificationNumber] {
  def query(id: IdentificationNumber): Future[Option[Employee]]
  def store(a: Employee): Future[Int]
  def delete(id: IdentificationNumber): Future[Int]
  def update(a: Employee): Future[Int]
}
