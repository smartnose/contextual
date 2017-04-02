package com.wws.crawling.storage

import java.net.URI

import org.joda.time.{DateTime, DateTimeZone}


case class CrawlingState(uri: String, host: String, lastCrawled: DateTime)

object CrawlingState {
  def apply(uri: URI): CrawlingState = {
    CrawlingState(uri.toString, uri.getHost, new DateTime(DateTimeZone.UTC))
  }
}

/**
  * Created by smartnose on 3/31/17.
  */
trait CrawlingStateRepo {

}
