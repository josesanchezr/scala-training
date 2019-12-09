package com.example.domain

sealed trait EmployeeStatus

case object ACTIVE extends EmployeeStatus
case object INACTIVE extends EmployeeStatus
