package com.wws.crawling.storage

import java.net.URI

import org.joda.time.{DateTime, DateTimeZone}


case class CrawlingRecord(uri: String, host: String, text: String, lastCrawled: DateTime)

object CrawlingRecord {
  def utcNow = new DateTime(DateTimeZone.UTC)
  def apply(uri: URI, text: String): CrawlingRecord = CrawlingRecord(uri.toString, uri.getHost, text, utcNow)
  def apply(uri: String, host: String, text: String): CrawlingRecord = CrawlingRecord(uri, host, text, utcNow)
}

/**
  * Created by smartnose on 3/31/17.
  */
trait CrawlingRepo {
  def hasCrawled(url: String): Boolean
  def save(crawlingRecord: CrawlingRecord)
}
