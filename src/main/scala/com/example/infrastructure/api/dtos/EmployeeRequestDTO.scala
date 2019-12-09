package com.example.infrastructure.api.dtos

import com.example.domain.Employee
import com.example.domain.validation.ValidationResult

case class EmployeeRequestDTO (identification: Long,
                                             name: String,
                                             company: String,
                                             street: String,
                                             telephone: Long,
                                             status: String)

object EmployeeRequestDTO {
  def toEmployee(request: EmployeeRequestDTO): ValidationResult[Employee] = {
    Employee(
      identificationNumber = request.identification,
      name = request.name,
      registeredName = request.company,
      street = request.street,
      telephoneNumber = request.telephone,
      employeeStatus = request.status
    )
  }
}
