package myrx

import rx.lang.scala.Observable
import rx.lang.scala.subjects.ReplaySubject

object HelloWithLatestWith1 extends App {

  val ob1 = ReplaySubject[Int]()
  val ob2 = ReplaySubject[String]()

  val sss = ob1.withLatestFrom(ob2)(_ + _)

//  Thread.sleep(1000)
//  ob1.onNext(1)
//  Thread.sleep(1000)
//  ob2.onNext("a")
//  Thread.sleep(1000)
//  ob2.onNext("b")
//  Thread.sleep(1000)
//  ob2.onNext("c")
//  Thread.sleep(1000)
//  ob1.onNext(2)
//  Thread.sleep(1000)
//  ob1.onNext(3)
//  Thread.sleep(1000)
//  ob2.onNext("d")
//  ob1.onCompleted()
//  ob2.onCompleted()

  new Thread(new Runnable {
    override def run(): Unit = {
      Thread.sleep(1000)
      ob1.onNext(1)

      Thread.sleep(1000)
      ob2.onNext("a")
      Thread.sleep(1000)
      ob2.onNext("b")
      Thread.sleep(1000)
      ob2.onNext("c")
      Thread.sleep(1000)
      ob1.onNext(2)
      Thread.sleep(1000)
      ob1.onNext(3)
      Thread.sleep(1000)
      ob2.onNext("d")

      ob1.onCompleted()
      ob2.onCompleted()

      println("thread ends")
    }
  }).start()

  println(sss.toBlocking.toList)

}
