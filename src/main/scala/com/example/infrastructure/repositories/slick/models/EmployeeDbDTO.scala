package com.example.infrastructure.repositories.slick.models

case class EmployeeDbDTO(id: Long,
                         name: String,
                         company: String,
                         street: String,
                         telephone: Long,
                         status: String)
