package com.bcatarino.berlinclock

import java.time.LocalDateTime
import java.util.TimerTask
import java.util.concurrent.{ScheduledThreadPoolExecutor, TimeUnit}

import scala.io.StdIn

object ConsoleClock extends App {

  val clock = new BerlinClock

  val task = new TimerTask {
    override def run(): Unit = {
      val now = LocalDateTime.now()
      val stringRepresentation = clock.forTime(now.getHour, now.getMinute, now.getSecond)
      println(stringRepresentation.getOrElse("Unable to show time..."))
      println
    }
  }

  println("press Enter to terminate")
  val executor = new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS)

  StdIn.readLine()
  executor.cancel(true)
  sys.exit
}
