package com.example.infrastructure.api.route

import java.sql.SQLIntegrityConstraintViolationException
import java.util.NoSuchElementException

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.StandardRoute
import com.example.infrastructure.api.dtos.ErrorMessage
import com.example.infrastructure.logger.Logger
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

object ManagmentErrors extends Logger {

  def buildError(ex: Throwable): StandardRoute =
    ex match {
      case _: SQLIntegrityConstraintViolationException =>
        complete(StatusCodes.InternalServerError -> createErrorMessage("1001", "The employee already exists"))
      case error: NumberFormatException =>
        complete(StatusCodes.BadRequest -> createErrorMessage(code = "1002", message = error.toString))
      case _: NoSuchElementException =>
        complete(StatusCodes.InternalServerError -> createErrorMessage("1003", "The employee not exists"))
      case _ =>
        complete(StatusCodes.InternalServerError -> createErrorMessage("1000", s"Unknown error - ${ex.getMessage}"))
    }

  private def createErrorMessage(code: String, message: String): ErrorMessage =
    ErrorMessage(
      code = code,
      message = message
    )
}
