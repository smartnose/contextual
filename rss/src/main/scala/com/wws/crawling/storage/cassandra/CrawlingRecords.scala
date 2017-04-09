package com.wws.crawling.storage.cassandra

import com.outworkers.phantom.dsl._
import com.wws.crawling.storage.CrawlingRecord

/**
  * Created by smartnose on 3/31/17.
  */
abstract class CrawlingRecords extends CassandraTable[CrawlingRecords, CrawlingRecord] with RootConnector {

  object uri extends StringColumn(this) with PartitionKey

  object host extends StringColumn(this)

  object lastCrawled extends DateTimeColumn(this)

  object text extends StringColumn(this)

  def store(input: CrawlingRecord) = {
    insert.value(_.uri, input.uri)
      .value(_.host, input.host)
      .value(_.lastCrawled, input.lastCrawled)
      .value(_.text, input.text)
  }

  def findStateByURI(input: String) = select.where(_.uri eqs input).one

  def hasCrawled(input: String) = findStateByURI(input).map(e => e.isDefined)
}
