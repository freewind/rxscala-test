package myrx

import rx.lang.scala.Observable

object MultiSubscribe extends App{

  val s = Observable.just(1,2,3)

  s.foreach(println)
  s.foreach(println)
  s.foreach(println)

}
