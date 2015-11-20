package myrx

import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject
import scala.concurrent.duration._

object NestedSubscribe extends App {

  val stream = PublishSubject[Int]()

  val scanned = stream.scan(Option.empty[Int]) {
    case (None, i) => Some(i)
    case (Some(r), i) if i % 2 == 0 => Some(r + i)
    case _ => None
  }.collect { case Some(i) => i }

  scanned.foreach { x =>
    println("### x: " + x)
    scanned.foreach(y =>
      println("### y: " + y)
    )
  }

  new Thread(new Runnable {
    override def run(): Unit = {
      (0 to 20).foreach { n =>
        stream.onNext(n)
        Thread.sleep(100)
      }
    }
  }).start()

  Thread.sleep(5000)
}
