package com.wws.crawling.storage.cassandra

import java.net.URI

import com.datastax.driver.core.ResultSet
import com.outworkers.util.samplers.{Generators, Sample}
import com.wws.crawling.storage.CrawlingState
import org.joda.time.{DateTime, DateTimeZone, LocalDate}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}
import com.outworkers.phantom.dsl.context


/**
  * Created by smartnose on 3/31/17.
  */
class CassandraCrawlingStateRepoSpec extends FlatSpec with Matchers with BeforeAndAfterAll with Generators with ScalaFutures with CrawlingDatabase.Connector {
  implicit object URISampler extends Sample[String] {
    override def sample: String = "http://wws.com/futurama.html"
  }

  implicit object JodaTimeSampler extends Sample[DateTime] {
    override def sample: DateTime = DateTime.now(DateTimeZone.UTC)
  }

  implicit object JodaLocalDateSampler extends Sample[LocalDate] {
    override def sample: LocalDate = LocalDate.now(DateTimeZone.UTC)
  }

  def database = CrawlingDatabase

  override def beforeAll(): Unit = {
    super.beforeAll()
    database.create()
  }

  it should "insert new crawling state in the table and retrieve it" in {
      val sample = gen[CrawlingState]
      val store = database.store(sample).future
      whenReady(store) { res =>
        res.isExhausted shouldBe true
      }

      val retrieve = database.CrawlingStateTable.findStateByURI("http://wws.com/futurama.html")
      whenReady(retrieve) {
        res => res.get.uri should equal (gen[String])
      }
  }
}
