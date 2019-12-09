package com.example.infrastructure.repositories.slick.mappings

import com.example.infrastructure.repositories.slick.models.EmployeeDbDTO
import slick.jdbc.MySQLProfile.api._

trait EmployeeSchema {

  class Employees(tag: Tag) extends Table[EmployeeDbDTO](tag, "employee") {
    def id = column[Long]("id", O.PrimaryKey)
    def name = column[String]("name")
    def company = column[String]("company")
    def street = column[String]("street")
    def telephone = column[Long]("telephone")
    def status = column[String]("status")
    def * = (
      id,
      name,
      company,
      street,
      telephone,
      status
    ) <> (EmployeeDbDTO.tupled, EmployeeDbDTO.unapply)
  }

  lazy val employees = TableQuery[Employees]

}
