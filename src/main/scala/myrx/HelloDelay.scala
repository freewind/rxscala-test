package myrx

import rx.lang.scala.Observable

import scala.concurrent.duration.Duration

object HelloDelay extends App {

  def intervalObservable[T](duration: String, events: Seq[T]): Observable[T] = {
    Observable.interval(Duration(duration)).zip(events).map(_._2)
  }


  println("start")
  val s = Observable.interval(Duration("100 millis")).zip(Seq(1, 2, 3)).map(_._2).delay(Duration("2 s"))
  println(s.toBlocking.toList)
  println("end")

}
