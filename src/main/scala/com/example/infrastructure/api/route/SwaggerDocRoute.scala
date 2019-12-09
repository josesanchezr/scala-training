package com.example.infrastructure.api.route

import com.github.swagger.akka.SwaggerHttpService
import com.github.swagger.akka.model.Info

object SwaggerDocRoute extends SwaggerHttpService {
  override def apiClasses: Set[Class[_]] = Set(
    classOf[ExampleRoutes]
  )
  override val info = Info(version = "1.0")
  override val unwantedDefinitions =
    Seq("Function1", "Function1RequestContextFutureRouteResult")
}
