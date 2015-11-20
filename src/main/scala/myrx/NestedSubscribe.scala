package myrx

import rx.lang.scala.Observable
import scala.concurrent.duration._

object NestedSubscribe extends App {

  val stream = Observable.interval(100.millis)

  val scanned = stream.scan(0L)(_ + _)

  scanned.foreach { x =>
    println("### x: " + x)
    scanned.foreach(y =>
      println("### y: " + y)
    )
  }

  Thread.sleep(5000)
}
