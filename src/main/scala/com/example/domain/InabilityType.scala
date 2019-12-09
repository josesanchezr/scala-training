package com.example.domain

sealed trait InabilityType

case object EG extends InabilityType
case object AT extends InabilityType
case object SOAT extends InabilityType
case object LM extends InabilityType
case object LT extends InabilityType

object InabilityType {
  def apply(inabilityType: String): InabilityType = {
    // TODO Validate when inability type not exists

    inabilityType match {
      case "EG" => EG
      case "AT" => AT
      case "SOAT" => SOAT
      case "LM" => LM
      case "LT" => LT
    }
  }
}

