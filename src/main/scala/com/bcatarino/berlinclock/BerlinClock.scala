package com.bcatarino.berlinclock

import com.bcatarino.berlinclock.BerlinClock._

import scala.annotation.tailrec
import scala.util.{Properties, Try}

class BerlinClock {

  def forTime(hours: Int, minutes: Int, seconds: Int): Try[String] = validateInput(hours, minutes, seconds)
      .map(time => buildClockString(time._1, time._2, time._3).mkString(Properties.lineSeparator))

  private def validateInput(hours: Int, minutes: Int, seconds: Int) = Try {
    require(hours >= 0 && hours <= 23, HOURS_ERROR)
    require(minutes >= 0 && minutes <= 59, MINUTES_ERROR)
    require(seconds >= 0 && seconds <= 59, SECONDS_ERROR)
    (hours, minutes, seconds)
  }

  private def buildClockString(hours: Int, minutes: Int, seconds: Int) = Seq(
    buildSecondsLamp(seconds),
    buildHoursTopPanel(hours, ""),
    buildHoursBottomPanel(hours % 5, ""),
    buildMinutesTopPanel(minutes, ""),
    buildMinutesBottomPanel(minutes % 5, "")
  )

  private def buildSecondsLamp(seconds: Int) = if (seconds % 2 == 0) "O" else "Y"

  @tailrec
  private def buildHoursTopPanel(hours: Int, result: String): String = {
    if (hours < 5) result + "O" * (4 - result.length) else buildHoursTopPanel(hours - 5, result + "R")
  }

  @tailrec
  private def buildHoursBottomPanel(hours: Int, result: String): String = {
    if (hours == 0) result + "O" * (4 - result.length) else buildHoursBottomPanel(hours - 1, result + "R")
  }

  @tailrec
  private def buildMinutesTopPanel(minutes: Int, result: String): String = {
    if (minutes < 5) result + "O" * (11 - result.length)
    else buildMinutesTopPanel(minutes - 5, result + getColor(result.length + 1))
  }

  private def getColor(lightPosition: Int) = if (lightPosition % 3 == 0) "R" else "Y"

  @tailrec
  private def buildMinutesBottomPanel(minutes: Int, result: String): String = {
    if (minutes == 0) result + "O" * (4 - result.length) else buildMinutesBottomPanel(minutes - 1, result + "Y")
  }
}

object BerlinClock {
  lazy val HOURS_ERROR = "Valid hours are between 00 and 23"
  lazy val MINUTES_ERROR = "Valid minutes are between 00 and 59"
  lazy val SECONDS_ERROR = "Valid seconds are between 00 and 59"
}