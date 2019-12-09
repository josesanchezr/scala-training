package com.example.domain

import java.util._

case class InabilityRecord(
                            employee: Employee,
                            inabilityType: InabilityType,
                            inabilityStartDate: Date,
                            inabilityDays: Int,
                            diagnostic: Diagnostic,
                            healthEntity: HealthEntity,
                            inabilityRecordStatus: InabilityRecordStatus
                          )

object InabilityRecord {
  def apply(
             employee: Employee,
             inabilityType: InabilityType,
             inabilityStartDate: Date,
             inabilityDays: Int,
             diagnostic: Diagnostic,
             healthEntity: HealthEntity,
             inabilityRecordStatus: InabilityRecordStatus
           ): InabilityRecord = {
    ???
  }

    /*val emp = Employee(
      identificationNumber = employee.identificationNumber,
      name = employee.name,
      workCenter = WorkCenter (
        registeredName = employee.workCenter.registeredName,
        street = employee.workCenter.street,
        telephoneNumber = employee.workCenter.telephoneNumber
      ),
      employeeStatus = employee.employeeStatus
    )

    val diag = Diagnostic(
      code = diagnostic.code,
      description = diagnostic.description
    )

    val healthEnt = HealthEntity(
      registeredName = healthEntity.registeredName,
      healthEntityType = healthEntity.healthEntityType
    )

    InabilityRecord(
      employee = emp,
      inabilityType = inabilityType,
      inabilityStartDate = inabilityStartDate,
      inabilityDays = inabilityDays,
      diagnostic = diag,
      healthEntity = healthEnt,
      inabilityRecordStatus = inabilityRecordStatus)
  }*/
}
