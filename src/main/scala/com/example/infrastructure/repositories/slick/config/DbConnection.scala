package com.example.infrastructure.repositories.slick.config

import slick.jdbc.MySQLProfile.api.Database

trait DbConnection {
  val db = Database.forConfig("database")
}

object DbConnection extends DbConnection
