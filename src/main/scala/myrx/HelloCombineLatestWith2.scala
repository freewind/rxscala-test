package myrx

import rx.lang.scala.subjects.ReplaySubject

object HelloCombineLatestWith2 extends App {

  val ob1 = ReplaySubject[Int]()
  val ob2 = ReplaySubject[String]()

  ob2.combineLatestWith(ob1)(_ + _).foreach(println)

  Seq("a", "b").foreach(ob2.onNext)
  Seq(1, 2, 3).foreach(ob1.onNext)

//  ob1.combineLatestWith(ob2)(_ + _).foreach(println)
//  println("-------")

}
