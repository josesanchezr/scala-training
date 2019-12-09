package com.example.infrastructure.repositories.slick.interpreter

import com.example.domain.repositories.EmployeeRepository
import com.example.domain.{Employee, IdentificationNumber}
import com.example.infrastructure.repositories.slick.mappings.EmployeeSchema
import com.example.infrastructure.repositories.slick.models.EmployeeTranslate
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}

class EmployeeRepositorySlickInterpreter(db: Database) extends EmployeeRepository with EmployeeSchema {
  override def query(id: IdentificationNumber): Future[Option[Employee]] = {
    val query = employees.filter(_.id === id.identificationNumber).result.head
    db.run(query).transformWith {
      case Success(v) => Future.successful(EmployeeTranslate.translateToDomain(v))
      case Failure(e) => Future.failed(e)
    }
  }

  override def store(employee: Employee): Future[Int] = {
    val insertAction = employees += EmployeeTranslate.translateToModel(employee)
    db.run(insertAction).transformWith {
      case Success(v) if v > 0 => Future.successful(v)
      case Failure(e) => Future.failed(e)
    }
  }

  override def delete(id: IdentificationNumber): Future[Int] = {
    val deleteAction = employees.filter(_.id === id.identificationNumber).delete
    db.run(deleteAction).transformWith {
      case Success(v) if v > 0 => Future.successful(v)
      case Failure(e) => Future.failed(e)
    }
  }

  override def update(employee: Employee): Future[Int] = {
    val query = employees.filter(_.id === employee.identificationNumber.identificationNumber)
    val updateAction = query.update(EmployeeTranslate.translateToModel(employee))
    db.run(updateAction).transformWith {
      case Success(v) if v > 0 => Future.successful(v)
      case Failure(e) => Future.failed(e)
    }
  }
}
