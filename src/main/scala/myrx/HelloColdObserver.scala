package myrx

import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject

object HelloColdObserver extends App {

  val ob = PublishSubject[Int]()

  val ob2 = ob.scan(List.empty[Int])((r, a) => a :: r)


  ob2.subscribe(x => println("a: " + x))

  ob.onNext(1)

  //  ob2.subscribe(x => println("b: " + x))

  ob.onNext(2)
  ob.onCompleted()

  ob2.last.foreach(x => println(x))

}
