package com.example.infrastructure.repositories.slick.models

import cats.data.Validated.{Invalid, Valid}
import com.example.domain.Employee

object EmployeeTranslate {
  def translateToDomain(employeeDb: EmployeeDbDTO): Option[Employee] = {
    Employee(
      identificationNumber = employeeDb.id,
      name = employeeDb.name,
      registeredName = employeeDb.company,
      street = employeeDb.street,
      telephoneNumber = employeeDb.telephone,
      employeeStatus = employeeDb.status
    ) match {
      case Valid(v) => Some(v)
      case Invalid(_) => None
    }
  }

  def translateToModel(employee: Employee): EmployeeDbDTO = {
    EmployeeDbDTO(
      id = employee.identificationNumber.identificationNumber,
      name = employee.name,
      company = employee.workCenter.registeredName,
      street = employee.workCenter.street,
      telephone = employee.workCenter.telephoneNumber,
      status = employee.employeeStatus.toString
    )
  }
}
