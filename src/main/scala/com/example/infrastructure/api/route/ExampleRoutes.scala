package com.example.infrastructure.api.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.{as, complete, entity, get, path, post, _}
import cats.data.Validated.{Invalid, Valid}
import com.example.domain.IdentificationNumber
import com.example.infrastructure.api.dtos.{EmployeeRequestDTO, EmployeeResponseDTO}
import com.example.application.env.Environment
import com.example.infrastructure.logger.Logger
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import io.swagger.annotations._
import javax.ws.rs.Path
import monix.execution.Scheduler

import scala.util.{Failure, Success, Try}

@Api(value = "/example", produces = "application/json")
@Path("/example")
class ExampleRoutes(env: Environment)(implicit scheduler: Scheduler) extends Logger {

  def routes: Route = storeEmployee ~ fetchEmployee

  @ApiOperation(
    value = "Store employee",
    notes = "Store employee",
    nickname = "storeEmployee",
    httpMethod = "POST",
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        name = "data",
        value = "Store employee",
        required = true,
        paramType = "body",
        dataTypeClass = classOf[EmployeeRequestDTO]
      )
    )
  )
  @ApiResponses(
    Array(
      new ApiResponse(code = 200, message = "OK", response = classOf[Int]),
      new ApiResponse(code = 500, message = "Internal server error")
    )
  )
  @Path("/employee/store")
  def storeEmployee: Route = path("example" / "employee" / "store") {
    post {
      entity(as[EmployeeRequestDTO]) { employeeReq =>
        logger.debug("storeEmployee method")

        val validatedEmployee =
          EmployeeRequestDTO
            .toEmployee(employeeReq)

        validatedEmployee match {
          case Valid(a) => {
            onComplete(
              env.employeeService
                .storeEmployee(a)
                .run(env.employeeRepository)
                .runToFuture
            ) {
              case Success(_) =>
                complete(StatusCodes.OK)
              case Failure(error) =>
                logger.error(error.toString)
                ManagmentErrors.buildError(error)
            }
          }
          case Invalid(error) => {
            complete(StatusCodes.BadRequest -> error)
          }
        }
      }
    }
  }

  @ApiOperation(
    value = "Fetch an employee",
    notes = "Fetch an employee",
    nickname = "fetchEmployee",
    httpMethod = "GET"
  )
  @ApiImplicitParams(
    Array(
      new ApiImplicitParam(
        name = "identificationNumber",
        value = "Id of employee",
        required = true,
        dataType = "string",
        paramType = "path"
      )
    )
  )
  @ApiResponses(
    Array(
      new ApiResponse(code = 200, message = "OK", response = classOf[Int]),
      new ApiResponse(code = 500, message = "Internal server error")
    )
  )
  @Path("/employee/fetch/{id}")
  def fetchEmployee: Route = path("example" / "employee" / "fetch" / Segment) { identificationNumber =>
    get {
      logger.debug("fetch method")
      val id = Try(identificationNumber.toLong)

      id match {
        case Success(value) => {
          val validatedIdent = IdentificationNumber(value)

          validatedIdent match {
            case Right(ident) =>
              onComplete(
                env.employeeService
                  .fetchEmployee(ident)
                  .run(env.employeeRepository)
                  .runToFuture
              ) {
                case Success(emp) =>
                  complete(StatusCodes.OK -> EmployeeResponseDTO.apply(emp.get))
                case Failure(e) =>
                  logger.error(e.toString)
                  ManagmentErrors.buildError(e)
              }
            case Left(e) =>
              complete(StatusCodes.BadRequest -> e)
          }
        }
        case Failure(e) =>
          logger.error(e.toString)
          ManagmentErrors.buildError(e)
      }
    }
  }
}
