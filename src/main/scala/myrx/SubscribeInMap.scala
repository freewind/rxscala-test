package myrx

import rx.lang.scala.{Subject, Observable}
import rx.lang.scala.subjects.PublishSubject
import scala.concurrent.duration._

object SubscribeInMap extends App {

  case class Conf(topics: Seq[Topic])
  case class Topic(title: String, totalValue: Int)
  case class Vote(title: String, value: Int)

//  val receivedVotes: Subject[Vote] = PublishSubject[Vote]()
//  val selectedTopicTitles: Subject[Seq[String]] = PublishSubject[Seq[String]]()

    val receivedVotes: Observable[Vote] = Observable.interval(100.millis).zip(Seq(Vote("aaa", 1), Vote("ccc", 3), Vote("aaa", 5), Vote("bbb", 1), Vote("aaa", 2), Vote("ccc", 4), Vote("bbb", 4), Vote("ccc", 2), Vote("aaa", 1), Vote("bbb", 2), Vote("aaa", 1))).map(_._2)
    val selectedTopicTitles: Observable[Seq[String]] = Observable.interval(100.millis).zip(Seq(Seq("aaa", "ccc"), Seq("aaa"), Seq("bbb"), Seq("aaa", "bbb"), Seq("aaa", "ccc"), Seq("aaa"), Seq("bbb", "ccc"), Seq("aaa", "bbb", "ccc"), Seq("aaa", "ccc"))).map(_._2)

  lazy val confSnapshots: Observable[Conf] = receivedVotes.scan(Conf(Nil)) {
    case (conf, vote) => {
      val newTopics = if (conf.topics.exists(_.title == vote.title)) {
        conf.topics.map {
          case Topic(title, value) if title == vote.title => Topic(title, value + vote.value)
          case t => t
        }
      } else {
        conf.topics :+ Topic(vote.title, vote.value)
      }

      conf.copy(topics = newTopics)
    }
  }


  lazy val selectedTopics: Observable[Seq[Topic]] = selectedTopicTitles.withLatestFrom(confSnapshots) { (titles, conf) =>
    titles.flatMap(title => conf.topics.find(_.title == title))
  }

  selectedTopics foreach { topics =>

    confSnapshots.foreach(x => println("### 333: " + topics.size))

    topics.foreach(t =>
      confSnapshots.subscribe(x => println("### 888: " + x))
    )
  }

//  new Thread(new Runnable {
//    override def run(): Unit = {
//      Seq(Vote("aaa", 1), Vote("ccc", 3), Vote("aaa", 5), Vote("bbb", 1), Vote("aaa", 2), Vote("ccc", 4), Vote("bbb", 4), Vote("ccc", 2), Vote("aaa", 1), Vote("bbb", 2), Vote("aaa", 1))
//        .foreach { vote =>
//          receivedVotes.onNext(vote)
//          Thread.sleep(500)
//        }
//    }
//  }).start()
//
//  new Thread(new Runnable {
//    override def run(): Unit = {
//      Seq(Seq("aaa", "ccc"), Seq("aaa"), Seq("bbb"), Seq("aaa", "bbb"), Seq("aaa", "ccc"), Seq("aaa"), Seq("bbb", "ccc"), Seq("aaa", "bbb", "ccc"), Seq("aaa", "ccc"))
//        .foreach { vote =>
//          selectedTopicTitles.onNext(vote)
//          Thread.sleep(100)
//        }
//    }
//  }).start()

  Thread.sleep(10000)

}
