package com.example.application.dtos

sealed trait Event

case class EmployeeCreated(id: Long, name: String) extends Event
