package com.wws.crawling.storage.cassandra

import com.outworkers.phantom.connectors.ContactPoint

/**
  * Created by smartnose on 3/31/17.
  */
object Defaults {
  implicit val connector = ContactPoint.local.keySpace("crawl_state")
}


