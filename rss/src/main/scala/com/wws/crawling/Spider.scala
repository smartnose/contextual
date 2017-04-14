package com.wws.crawling

import com.wws.crawling.storage.CrawlingRepo
import com.wws.crawling.storage.cassandra.CassandraCrawlingRepo
import edu.uci.ics.crawler4j.crawler.{CrawlConfig, CrawlController}
import edu.uci.ics.crawler4j.fetcher.PageFetcher
import edu.uci.ics.crawler4j.robotstxt.{RobotstxtConfig, RobotstxtServer}

class ProdCrawler extends HtmlCrawler with CassandraCrawlingRepo {

}
/**
  * Created by smartnose on 4/3/17.
  */
object Spider {
  def main(args: Array[String]): Unit = {
    val config = new CrawlConfig()
    config.setCrawlStorageFolder("/Users/smartnose/data/crawl/root")
    val fetcher = new PageFetcher(config)
    val robotstxtConfig = new RobotstxtConfig()
    val robotstxtServer = new RobotstxtServer(robotstxtConfig, fetcher)
    val controller = new CrawlController(config, fetcher, robotstxtServer)
    controller.addSeed("https://www.theatlantic.com/")

    controller.start[ProdCrawler](classOf[ProdCrawler], 2)
  }
}
