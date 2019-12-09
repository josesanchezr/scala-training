package com.example.domain

import cats.data.ValidatedNel

package object validation {

  type EitherValidation[A] = Either[DomainValidation, A]
  type ValidationResult[A] = ValidatedNel[DomainValidation, A]
}
