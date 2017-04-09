package com.wws.crawling

import java.util.regex.Pattern

import com.wws.crawling.storage.{CrawlingRecord, CrawlingRepo}
import edu.uci.ics.crawler4j.crawler.{Page, WebCrawler}
import edu.uci.ics.crawler4j.parser.HtmlParseData
import edu.uci.ics.crawler4j.url.WebURL

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by smartnose on 4/2/17.
  */
abstract class HtmlCrawler extends WebCrawler {
  this: CrawlingRepo =>

  /**
    * We are only interested in HTML text, so filtering out all other file types
    */
  private val FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg" + "|png|mp3|mp3|zip|gz))$")

  override def shouldVisit(referringpage: Page, url: WebURL): Boolean = {
     val href = url.getURL.toLowerCase
     (!FILTERS.matcher(href).matches
       && href.contains(url.getDomain) // limit the crawl within a single domain
       && !hasCrawled(href))
  }

  override def visit(page: Page) = {

      val webUrl = page.getWebURL()
      // System.out.println("URL: " + url);

      if (page.getParseData().isInstanceOf[HtmlParseData]) {
        val htmlParseData = page.getParseData.asInstanceOf[HtmlParseData]
        val text = htmlParseData.getText
        store(CrawlingRecord(webUrl.getURL, webUrl.getDomain, text))
      }
  }
}
