package com.wws.crawling.storage.cassandra

import com.outworkers.phantom.dsl.context
import com.outworkers.util.samplers.{Generators, Sample}
import com.wws.crawling.storage.CrawlingRecord
import org.joda.time.{DateTime, DateTimeZone, LocalDate}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfterAll, FlatSpec, Matchers}


/**
  * Created by smartnose on 3/31/17.
  */
class CassandraCrawlingStateRepoSpec extends FlatSpec with Matchers with BeforeAndAfterAll with Generators with ScalaFutures with CrawlingDatabase.Connector {
  implicit object URISampler extends Sample[String] {
    override def sample: String = "http://wws.com/futurama1.html"
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

  override def afterAll(): Unit = {
    super.afterAll()

    // If you need to reset the tables, enable the following line
    // Note that it will wipe all data in the test database
    // database.drop()
  }

  it should "insert new crawling state in the table and retrieve it" in {
      val sample = gen[CrawlingRecord]
      // database.store(sample)
      val res = database.store(sample)

        res.isExhausted shouldBe true


      val retrieve = database.CrawlingStateTable.findStateByURI("http://wws.com/futurama1.html")
      whenReady(retrieve) {
        res => res.get.uri should equal (gen[String])
      }
  }
}
