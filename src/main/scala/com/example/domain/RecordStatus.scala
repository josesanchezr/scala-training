package com.example.domain

sealed trait InabilityRecordStatus

case object Reported extends InabilityRecordStatus
case object Rejected extends InabilityRecordStatus
case object Approved extends InabilityRecordStatus
case object Paid extends  InabilityRecordStatus
