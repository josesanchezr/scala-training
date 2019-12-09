package com.example.infrastructure.logger

import org.slf4j.LoggerFactory

trait Logger {
  protected lazy val logger: org.slf4j.Logger =
    LoggerFactory.getLogger(getClass.getName.stripSuffix("$"))
}
