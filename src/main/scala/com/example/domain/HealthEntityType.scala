package com.example.domain

sealed trait HealthEntityType

case object EPS extends HealthEntityType
case object ARL extends HealthEntityType
