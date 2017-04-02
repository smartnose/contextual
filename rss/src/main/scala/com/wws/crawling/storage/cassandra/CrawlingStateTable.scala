package com.wws.crawling.storage.cassandra

import java.net.URI

import com.outworkers.phantom.dsl._
import com.wws.crawling.storage.CrawlingState
/**
  * Created by smartnose on 3/31/17.
  */
abstract class CrawlingStateTable extends CassandraTable[CrawlingStateTable, CrawlingState] with RootConnector {
  object uri extends StringColumn(this) with PartitionKey
  object host extends StringColumn(this)
  object lastCrawled extends DateTimeColumn(this)

  def store(input: CrawlingState) = {
    insert.value(_.uri, input.uri)
      .value(_.host, input.host)
        .value(_.lastCrawled, input.lastCrawled)
  }

  def findStateByURI(input: String) =  select.where(_.uri eqs input).one
}
