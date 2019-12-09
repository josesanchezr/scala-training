package com.example.infrastructure.api.dtos

import com.example.domain.Employee

case class EmployeeResponseDTO (identification: Long,
                                name: String,
                                company: String,
                                street: String,
                                telephone: Long,
                                status: String)

object EmployeeResponseDTO {
  def apply(employee: Employee): EmployeeResponseDTO =
    new EmployeeResponseDTO(
      identification = employee.identificationNumber.identificationNumber,
      name = employee.name,
      company = employee.workCenter.registeredName,
      street = employee.workCenter.street,
      telephone = employee.workCenter.telephoneNumber,
      status = employee.employeeStatus.toString)
}
