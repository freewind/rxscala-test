package myrx

import rx.lang.scala.Observable
import scala.concurrent.duration._

object HelloLastItem extends App {

  val source = Observable.interval(1.second)

  val reduced = source.scan(0L)(_ + _)

  Thread.sleep(2500)
  reduced.subscribe(x => println(x))


  Thread.sleep(5000)
}
