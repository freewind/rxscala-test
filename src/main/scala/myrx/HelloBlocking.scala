package myrx

import rx.lang.scala.subjects.ReplaySubject

object HelloBlocking extends App {

  val ob1 = ReplaySubject[Int]()
  val ob2 = ReplaySubject[String]()

  Seq("a", "b").foreach(ob2.onNext)
  Seq(1, 2, 3).foreach(ob1.onNext)

  val ob = ob2.combineLatestWith(ob1)(_ + _)

  ob.toBlocking.foreach(println)

}
