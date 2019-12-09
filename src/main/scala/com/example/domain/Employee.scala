package com.example.domain

import cats.implicits._
import com.example.domain.validation.{EitherValidation, EmployeeStatusNotExists, EmptyValue, ValidationResult}

final case class Employee private (
                     identificationNumber: IdentificationNumber,
                     name: String,
                     workCenter: WorkCenter,
                     employeeStatus: EmployeeStatus
                   )

object Employee {
  def apply(
             identificationNumber: Long,
             name: String,
             registeredName: String,
             street: String,
             telephoneNumber: Long,
             employeeStatus: String
           ): ValidationResult[Employee] = {

    (
      IdentificationNumber(identificationNumber),
      validateName(name),
      WorkCenter(
        registeredName = registeredName,
        street = street,
        telephoneNumber = telephoneNumber),
      validateEmployeeStatus(employeeStatus)
    ).mapN(new Employee(_,_,_,_)).toValidatedNel
  }

  private def validateName(name: String): EitherValidation[String] = {
    Either.cond(
      !name.isEmpty,
      name,
      EmptyValue("Employee name")
    )
  }

  private def validateEmployeeStatus(employeeStatus: String): EitherValidation[EmployeeStatus] = {
    employeeStatus match {
      case "ACTIVE" => Right(ACTIVE)
      case "INACTIVE" => Right(INACTIVE)
      case _ => Left(EmployeeStatusNotExists)
    }
  }
}
