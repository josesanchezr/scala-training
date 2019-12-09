package com.example.domain

import com.example.domain.validation.{EitherValidation, NonZeroOrNegative}

final case class IdentificationNumber private (identificationNumber: Long)

object IdentificationNumber {
  def apply(identificationNumber: Long): EitherValidation[IdentificationNumber] = {
    if(identificationNumber > 0) {
      Right(new IdentificationNumber(identificationNumber))
    } else {
      Left(NonZeroOrNegative("Identification number"))
    }
  }
}
