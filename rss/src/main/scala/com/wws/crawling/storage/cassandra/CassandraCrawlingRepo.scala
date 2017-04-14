package com.wws.crawling.storage.cassandra

import com.wws.crawling.storage.{CrawlingRecord, CrawlingRepo}

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by smartnose on 3/31/17.
  */
trait CassandraCrawlingRepo extends CrawlingRepo {
  override def hasCrawled(url: String): Boolean = {
    Await.result(CrawlingDatabase.hasCrawled(url), 5000 millis)
  }

  override def store(crawlingRecord: CrawlingRecord) = CrawlingDatabase.store(crawlingRecord)
}
