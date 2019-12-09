package com.example.application.service

import com.example.domain._

import scala.util.{Failure, Success, Try}

trait HealthEntityService {
  def validateInabilityType(inabilityType: InabilityType): Try[Boolean] = {
    inabilityType match {
      case EG => Success(true)
      case AT => Success(true)
      case SOAT => Success(true)
      case LM => Success(true)
      case LT => Success(true)
      case _ => Failure(new Exception("Error: Invalid inability type"))
    }
  }
}
