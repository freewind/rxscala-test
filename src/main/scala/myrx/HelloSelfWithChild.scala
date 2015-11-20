package myrx

import rx.lang.scala.Observable
import scala.concurrent.duration._

object HelloSelfWithChild extends App {

  val stream = Observable.interval(1.second)

  val child = stream.map(x => x * x)

  val last1 = stream.withLatestFrom(child)((i1, i2) => i1 + "-" + i2)
//  val last1 = stream.combineLatest(child)

    val last2 = child.withLatestFrom(stream)((i1, i2) => i1 + "-" + i2)
  //  val last2 = child.combineLatest(stream)

//  last1.foreach(println)
    last2.foreach(println)

  Thread.sleep(5000)

}
