package com.example.infrastructure.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.example.infrastructure.api.route.{CommonRoutes, ExampleRoutes, SwaggerDocRoute}
import com.example.application.env.Environment
import monix.execution.Scheduler

class ExampleAPI(env: Environment)(implicit scheduler: Scheduler) {

  lazy val routes: Route = SwaggerDocRoute.routes ~ exampleAPI.routes ~ commonsAPI.routes

  private val commonsAPI: CommonRoutes = new CommonRoutes()
  private val exampleAPI: ExampleRoutes = new ExampleRoutes(env)
}
