package com.example.infrastructure.api.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives.{complete, get, path, _}
import com.example.infrastructure.logger.Logger
import io.swagger.annotations.{Api, ApiOperation, ApiResponse, ApiResponses}
import javax.ws.rs.Path

@Api(value = "/example/healthcheck", produces = "text/plain")
@Path("/example/healthcheck")
class CommonRoutes() extends Logger {

  @ApiOperation(
    value = "Return healthcheck information from example microservice",
    notes = "",
    nickname = "returnHealthcheck",
    httpMethod = "GET")
  @ApiResponses(
    Array(
      new ApiResponse(code = 200, message = "OK", response = classOf[String]),
      new ApiResponse(code = 500, message = "Internal server error")
    )
  )
  def routes: Route = path("example" / "healthcheck") {
    get {
      logger.debug(s"route method used for health check")
      complete(StatusCodes.OK)
    }
  }
}
