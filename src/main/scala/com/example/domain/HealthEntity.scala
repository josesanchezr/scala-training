package com.example.domain

case class HealthEntity(
                         registeredName: String,
                         healthEntityType: HealthEntityType
                       )

object HealthEntity {
  def apply(
             registeredName: String,
             healthEntityType: HealthEntityType
           ): HealthEntity = {

    HealthEntity(
      registeredName = registeredName,
      healthEntityType = healthEntityType
    )
  }
}
