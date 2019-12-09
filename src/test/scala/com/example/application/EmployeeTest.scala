package com.example.application

import java.sql.SQLIntegrityConstraintViolationException
import java.util.NoSuchElementException

import com.example.application.service.EmployeeService
import com.example.application.service.interpreter.EmployeeServiceInterpreter
import com.example.domain.{Employee, IdentificationNumber}
import com.example.domain.repositories.EmployeeRepository
import com.example.infrastructure.repositories.slick.config.DbConnection
import com.example.infrastructure.repositories.slick.interpreter.EmployeeRepositorySlickInterpreter
import monix.execution.Scheduler.Implicits.global
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}

class EmployeeTest() extends FlatSpec with Matchers with ScalaFutures {

  val db = DbConnection.db
  val employeeRepository: EmployeeRepository = new EmployeeRepositorySlickInterpreter(db)
  val employeeService: EmployeeService[Employee, IdentificationNumber] = new EmployeeServiceInterpreter

  it should "eventually return a Int after to store an employee" in {
    for {
      emp <- Employee(
        identificationNumber = 1000,
        name = "Julian Fernandez",
        registeredName = "Google",
        street = "Mountain View, California, United States",
        telephoneNumber = 3005004003L,
        employeeStatus = "ACTIVE").toEither
    } yield {
      whenReady(employeeService.storeEmployee(emp).run(employeeRepository).runToFuture) { res =>
        res shouldEqual 1
      }
    }
  }

  it should "eventually return an Employee" in {
    for {
      id <- IdentificationNumber(identificationNumber = 1000)
      emp <- Employee(
        identificationNumber = 1000,
        name = "Julian Fernandez",
        registeredName = "Google",
        street = "Mountain View, California, United States",
        telephoneNumber = 3005004003L,
        employeeStatus = "ACTIVE").toEither
    } yield {
      whenReady(employeeService.fetchEmployee(id).run(employeeRepository).runToFuture) { res =>
        res shouldEqual Some(emp)
      }
    }
  }

  it should "eventually return a NoSuchElementException," in {
    for {
      id <- IdentificationNumber(identificationNumber = 2000)
    } yield {
      whenReady(employeeService.fetchEmployee(id).run(employeeRepository).runToFuture.failed) { error =>
        error shouldBe an[NoSuchElementException]
      }
    }
  }

  it should "eventually return a java.sql.SQLIntegrityConstraintViolationException" in {
    for {
      emp <- Employee(
        identificationNumber = 1000,
        name = "Julian Fernandez",
        registeredName = "Google",
        street = "Mountain View, California, United States",
        telephoneNumber = 3005004003L,
        employeeStatus = "ACTIVE").toEither
    } yield {
      whenReady(employeeService.storeEmployee(emp).run(employeeRepository).runToFuture.failed) { error =>
        error shouldBe an[SQLIntegrityConstraintViolationException]
      }
    }
  }

  it should "eventually return a scala.MatchError after to try deleting an employee" in {
    for {
      id <- IdentificationNumber(identificationNumber = 2000)
    } yield {
      whenReady(employeeService.deleteEmployee(id).run(employeeRepository).failed) { error =>
        error shouldBe an[MatchError]
      }
    }
  }

  it should "eventually return a Int after to update an employee" in {
    for {
      emp <- Employee(
        identificationNumber = 1000,
        name = "Catalina Duque",
        registeredName = "Facebook",
        street = "Mountain View, California, United States",
        telephoneNumber = 33014502091L,
        employeeStatus = "ACTIVE").toEither
    } yield {
      whenReady(employeeService.updateEmployee(emp).run(employeeRepository)) { res =>
        res shouldEqual 1
      }
    }
  }

  it should "eventually return a Int after to delete an employee" in {
    for {
      id <- IdentificationNumber(identificationNumber = 1000)
    } yield {
      whenReady(employeeService.deleteEmployee(id).run(employeeRepository)) { res =>
        res shouldEqual 1
      }
    }
  }

  it should "eventually return a scala.MatchError after to try updating an employee" in {
    for {
      emp <- Employee(
        identificationNumber = 1000,
        name = "Catalina Duque",
        registeredName = "Facebook",
        street = "Mountain View, California, United States",
        telephoneNumber = 33014502091L,
        employeeStatus = "ACTIVE").toEither
    } yield {
      whenReady(employeeService.updateEmployee(emp).run(employeeRepository).failed) { error =>
        error shouldBe an[MatchError]
      }
    }
  }
}
