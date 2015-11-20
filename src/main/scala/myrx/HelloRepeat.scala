package myrx

import rx.lang.scala.Observable

import scala.concurrent.duration.Duration

object HelloRepeat extends App {

  val s = Observable.interval(Duration("1000 millis")).zip(List(1, 2, 3)).repeat.take(2)

  s.foreach(println)

  println(s.toBlocking.toList)
  Thread.sleep(4000)
}
