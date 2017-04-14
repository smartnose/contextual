package com.wws.crawling.storage.cassandra

import com.outworkers.phantom.connectors
import com.outworkers.phantom.connectors.{CassandraConnection, Connector, ContactPoint}
import com.outworkers.phantom.dsl.Database
import com.wws.crawling.storage.CrawlingRecord

import scala.concurrent.duration._
import scala.concurrent.Await

/**
  * Created by smartnose on 3/31/17.
  */
class CrawlingDatabase(override val connector: CassandraConnection) extends Database[CrawlingDatabase](connector) {
  object CrawlingStateTable extends CrawlingRecords with Connector

  def store(input: CrawlingRecord) = {
    Await.result(CrawlingStateTable.store(input), 500 milli)
  }
  def hasCrawled(uri: String) = CrawlingStateTable.hasCrawled(uri)
}

object CrawlingDatabase extends CrawlingDatabase(ContactPoint.local.keySpace("crawling"))
