package com.wws.crawling.storage.cassandra

import com.outworkers.phantom.connectors
import com.outworkers.phantom.connectors.{CassandraConnection, Connector, ContactPoint}
import com.outworkers.phantom.dsl.Database
import com.wws.crawling.storage.CrawlingState

/**
  * Created by smartnose on 3/31/17.
  */
class CrawlingDatabase(override val connector: CassandraConnection) extends Database[CrawlingDatabase](connector) {
  object CrawlingStateTable extends CrawlingStateTable with Connector

  def store(input: CrawlingState) = CrawlingStateTable.store(input)
}

object CrawlingDatabase extends CrawlingDatabase(ContactPoint.local.keySpace("crawling"))
