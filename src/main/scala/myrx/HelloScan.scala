package myrx

import rx.lang.scala.subjects.ReplaySubject

object HelloScan extends App {

  val events = ReplaySubject[String]()

  val scan = events.scan(0) { case (r, s) => r + s.toInt }

  Seq("1", "2").foreach(events.onNext)

  scan.take(2).toBlocking.toList.foreach(println)
}
