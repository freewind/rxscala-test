package myrx

import rx.lang.scala.Observable
import scala.concurrent.duration._

object Intervals extends App {

  def intervalObservable[T](duration: String, events: Seq[T]): Observable[T] = {
    Observable.interval(Duration(duration)).zip(events).map(_._2)
  }

  val bbb = intervalObservable("50 millis", Seq(1))
  val sss = intervalObservable("100 millis", Seq(1, 2, 3, 4, 5))
  val mmm = bbb.withLatestFrom(sss)(_ + _)

  println(mmm.toBlocking.toList)

}
