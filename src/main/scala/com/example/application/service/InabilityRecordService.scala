package com.example.application.service

import java.util.{Calendar, Date}

import com.example.domain.{InabilityType, LM}

import scala.util.{Failure, Success, Try}

trait InabilityRecordService {
  def validateInabilityStartDate(inabilityStartDate: Date): Try[Boolean] = {
    val calendarA: Calendar = Calendar.getInstance()
    calendarA.setTime(inabilityStartDate)

    val calendarB: Calendar = Calendar.getInstance()
    calendarB.setTime(new Date())

    if(calendarA.get(Calendar.YEAR) == calendarB.get(Calendar.YEAR)) {
      Success(true)
    } else {
      Failure(new Exception("Error: Invalid inability start date"))
    }
  }

  def validateInabilityDays(inabilityDays: Int, inabilityType: InabilityType): Try[Boolean] = {
    inabilityType match {
      case LM if inabilityDays > 120 => Failure(new Exception("Error: Inability days greater than permitted"))
      case _ if inabilityDays > 30 => Failure(new Exception("Error: Inability days greater than permitted"))
      case _ => Success(true)
    }
  }
}
