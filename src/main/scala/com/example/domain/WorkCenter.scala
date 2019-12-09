package com.example.domain

import cats.implicits._
import com.example.domain.validation.{EitherValidation, EmptyValue, NonZeroOrNegative}

final case class WorkCenter private (
                       registeredName: String,
                       street: String,
                       telephoneNumber: Long
                     )

object WorkCenter {
  def apply(
             registeredName: String,
             street: String,
             telephoneNumber: Long
           ): EitherValidation[WorkCenter] = {
    (
      validateRegisteredName(registeredName),
      validateStreet(street),
      validateTelephoneNumber(telephoneNumber)
    ).mapN(new WorkCenter(_,_,_))
  }

  private def validateRegisteredName(registeredName: String): EitherValidation[String] = {
    Either.cond(
      !registeredName.isEmpty,
      registeredName,
      EmptyValue("Registered name of workercenter")
    )
  }

  private def validateStreet(street: String): EitherValidation[String] = {
    Either.cond(
      !street.isEmpty,
      street,
      EmptyValue("Street of workercenter")
    )
  }

  private def validateTelephoneNumber(telephoneNumber: Long): EitherValidation[Long] = {
    Either.cond(
      telephoneNumber > 0,
      telephoneNumber,
      NonZeroOrNegative("Telephone number of workercenter")
    )
  }
}
