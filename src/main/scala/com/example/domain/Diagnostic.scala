package com.example.domain

case class Diagnostic(
                       code: String,
                       description: String
                     )

object Diagnostic {
  def apply(
             code: String,
             description: String
           ): Diagnostic = {

    Diagnostic(
      code = code,
      description = description
    )
  }
}
