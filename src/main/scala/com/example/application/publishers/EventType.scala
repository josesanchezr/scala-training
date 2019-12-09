package com.example.application.publishers

sealed trait EventType

case object EmployeeEvent extends EventType
