package com.example.domain.repositories

import scala.concurrent.Future

trait Repository[A, IdType] {
  def query(id: IdType): Future[Option[A]]
  def store(a: A): Future[Int]
}
