package com.bcatarino.berlinclock

import org.scalatest.{Matchers, WordSpec}
import BerlinClock._
import BerlinClockSpec._

import scala.util.Properties

class BerlinClockSpec extends WordSpec with Matchers {

  val clock = new BerlinClock

  "BerlinClock#forTime" should {
    "return Failure if hour is under 0" in {
      val exception = intercept[IllegalArgumentException] (clock.forTime(-1, 0, 0).get)
      exception.getMessage.endsWith(HOURS_ERROR) should be(true)
    }

    "return Failure if hour is over 23" in {
      val exception = intercept[IllegalArgumentException] (clock.forTime(24, 0, 0).get)
      exception.getMessage.endsWith(HOURS_ERROR) should be(true)
    }

    "return Failure if minutes is under 0" in {
      val exception = intercept[IllegalArgumentException] (clock.forTime(0, -1, 0).get)
      exception.getMessage.endsWith(MINUTES_ERROR) should be(true)
    }

    "return Failure if minutes is over 59" in {
      val exception = intercept[IllegalArgumentException] (clock.forTime(0, 60, 0).get)
      exception.getMessage.endsWith(MINUTES_ERROR) should be(true)
    }

    "return Failure if seconds is under 0" in {
      val exception = intercept[IllegalArgumentException] (clock.forTime(0, 0, -1).get)
      exception.getMessage.endsWith(SECONDS_ERROR) should be(true)
    }

    "return Failure if seconds is over 59" in {
      val exception = intercept[IllegalArgumentException] (clock.forTime(0, 0, 60).get)
      exception.getMessage.endsWith(SECONDS_ERROR) should be(true)
    }

    "return seconds lantern on if seconds is odd" in {
      clock.forTime(0, 0, 1).get.split(Properties.lineSeparator)(SECONDS_LAMP) should be("Y")
      clock.forTime(13, 11, 59).get.split(Properties.lineSeparator)(SECONDS_LAMP) should be("Y")
      clock.forTime(23, 59, 59).get.split(Properties.lineSeparator)(SECONDS_LAMP) should be("Y")
    }

    "return seconds lantern off if seconds is even" in {
      clock.forTime(0, 0, 0).get.split(Properties.lineSeparator)(SECONDS_LAMP) should be("O")
      clock.forTime(12, 15, 20).get.split(Properties.lineSeparator)(SECONDS_LAMP) should be("O")
      clock.forTime(23, 59, 58).get.split(Properties.lineSeparator)(SECONDS_LAMP) should be("O")
    }

    "return no hour lanterns on if hour is 0" in {
      val result = clock.forTime(0, 50, 20).get.split(Properties.lineSeparator)
      result(HOURS_TOP_PANEL) should be("OOOO")
      result(HOURS_BOTTOM_PANEL) should be("OOOO")
    }

    "return one bottom lantern if hour is 1" in {
      val result = clock.forTime(1, 50, 20).get.split(Properties.lineSeparator)
      result(HOURS_TOP_PANEL) should be("OOOO")
      result(HOURS_BOTTOM_PANEL) should be("ROOO")
    }

    "return four bottom lanterns if hour is 4" in {
      val result = clock.forTime(4, 50, 20).get.split(Properties.lineSeparator)
      result(HOURS_TOP_PANEL) should be("OOOO")
      result(HOURS_BOTTOM_PANEL) should be("RRRR")
    }

    "return one top lantern if hour is 5" in {
      val result = clock.forTime(5, 50, 20).get.split(Properties.lineSeparator)
      result(HOURS_TOP_PANEL) should be("ROOO")
      result(HOURS_BOTTOM_PANEL) should be("OOOO")
    }

    "return two top lanterns and three bottom one if hour is 13" in {
      val result = clock.forTime(13, 50, 20).get.split(Properties.lineSeparator)
      result(HOURS_TOP_PANEL) should be("RROO")
      result(HOURS_BOTTOM_PANEL) should be("RRRO")
    }

    "return all lanterns on top and bottom if hour is 23" in {
      val result = clock.forTime(23, 50, 20).get.split(Properties.lineSeparator)
      result(HOURS_TOP_PANEL) should be("RRRR")
      result(HOURS_BOTTOM_PANEL) should be("RRRO")
    }

    "return no minute lanterns on if minutes is 0" in {
      val result = clock.forTime(0, 0, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("OOOOOOOOOOO")
      result(MINUTES_BOTTOM_PANEL) should be("OOOO")
    }

    "return one yellow bottom lantern on if minutes is 1" in {
      val result = clock.forTime(0, 1, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("OOOOOOOOOOO")
      result(MINUTES_BOTTOM_PANEL) should be("YOOO")
    }

    "return two yellow bottom lanterns on if minutes is 2" in {
      val result = clock.forTime(0, 2, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("OOOOOOOOOOO")
      result(MINUTES_BOTTOM_PANEL) should be("YYOO")
    }

    "return four yellow bottom lanterns on if minutes is 4" in {
      val result = clock.forTime(0, 4, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("OOOOOOOOOOO")
      result(MINUTES_BOTTOM_PANEL) should be("YYYY")
    }

    "return one yellow top lantern on if minutes is 5" in {
      val result = clock.forTime(0, 5, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("YOOOOOOOOOO")
      result(MINUTES_BOTTOM_PANEL) should be("OOOO")
    }

    "return one yellow top lantern and 3 bottom lanterns on if minutes is 8" in {
      val result = clock.forTime(0, 8, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("YOOOOOOOOOO")
      result(MINUTES_BOTTOM_PANEL) should be("YYYO")
    }

    "return one red top lantern if minutes is 15" in {
      val result = clock.forTime(0, 15, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("YYROOOOOOOO")
      result(MINUTES_BOTTOM_PANEL) should be("OOOO")
    }

    "return one red top lantern and four bottom yellow on if minutes is 24" in {
      val result = clock.forTime(0, 24, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("YYRYOOOOOOO")
      result(MINUTES_BOTTOM_PANEL) should be("YYYY")
    }

    "return two red top lantern and five yellow if minutes is 35" in {
      val result = clock.forTime(0, 35, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("YYRYYRYOOOO")
      result(MINUTES_BOTTOM_PANEL) should be("OOOO")
    }

    "return three red top lantern if minutes is 45" in {
      val result = clock.forTime(0, 45, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("YYRYYRYYROO")
      result(MINUTES_BOTTOM_PANEL) should be("OOOO")
    }

    "return three red top lantern and four yellow bottom on if minutes is 49" in {
      val result = clock.forTime(0, 49, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("YYRYYRYYROO")
      result(MINUTES_BOTTOM_PANEL) should be("YYYY")
    }

    "return all minute lights on if minutes is 59" in {
      val result = clock.forTime(0, 59, 20).get.split(Properties.lineSeparator)
      result(MINUTES_TOP_PANEL) should be("YYRYYRYYRYY")
      result(MINUTES_BOTTOM_PANEL) should be("YYYY")
    }

    "return correct lights for 13:33:22" in {
      val result = clock.forTime(13, 33, 22).get.split(Properties.lineSeparator)
      result(SECONDS_LAMP) should be("O")
      result(HOURS_TOP_PANEL) should be("RROO")
      result(HOURS_BOTTOM_PANEL) should be("RRRO")
      result(MINUTES_TOP_PANEL) should be("YYRYYROOOOO")
      result(MINUTES_BOTTOM_PANEL) should be("YYYO")
    }

    "return correct lights for 02:12:59" in {
      val result = clock.forTime(2, 12, 59).get.split(Properties.lineSeparator)
      result(SECONDS_LAMP) should be("Y")
      result(HOURS_TOP_PANEL) should be("OOOO")
      result(HOURS_BOTTOM_PANEL) should be("RROO")
      result(MINUTES_TOP_PANEL) should be("YYOOOOOOOOO")
      result(MINUTES_BOTTOM_PANEL) should be("YYOO")
    }
  }
}

object BerlinClockSpec {
  lazy val SECONDS_LAMP = 0
  lazy val HOURS_TOP_PANEL = 1
  lazy val HOURS_BOTTOM_PANEL = 2
  lazy val MINUTES_TOP_PANEL = 3
  lazy val MINUTES_BOTTOM_PANEL = 4
}