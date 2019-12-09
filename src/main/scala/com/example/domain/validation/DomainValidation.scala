package com.example.domain.validation

sealed trait DomainValidation {
  def errorMessage: String
}

// Common validation errors

case class EmptyValue(message: String) extends DomainValidation {
  override def errorMessage: String = s"$message cannot be blank"
}

case class NonZeroOrNegative(message: String) extends DomainValidation {
  override def errorMessage: String = s"$message cannot be zero or negative"
}

// Validation errors for Employee

case object EmployeeStatusNotExists extends DomainValidation {
  override def errorMessage: String = "Employee status not exists"
}

// Validation errors for InabilityType

case object InabilityTypeIsEmpty extends DomainValidation {
  override def errorMessage: String = "Inability Type cannot cannot be blank"
}

case object InabilityTypeHasSpecialCharacters extends DomainValidation {
  override def errorMessage: String = "Inability Type cannot contain numbers, lowercase letters or special characters"
}