package myrx

import rx.lang.scala.Observable

object ThreadObservableDemo extends App {

  val obs = Observable[Int] { subscriber =>
      new Thread() {
        var i = 0
        override def run(): Unit = {
        println("######################## new thread")
          while(true ){
            subscriber.onNext(i)
            i += 1
            Thread.sleep(1000)
          }
        }
      }.start()
    }

  obs.subscribe(x => println())

  Thread.sleep(3000)

  obs.subscribe(x => println("subscriber b: " + x))

  Thread.sleep(3000)

  obs.subscribe(x => println("subscriber c: " + x))


}
