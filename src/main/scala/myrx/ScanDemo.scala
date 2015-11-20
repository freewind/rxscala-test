package myrx

import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject



object ScanDemo extends App {

  sealed trait PairEvent
  case class Doc(path: String) extends PairEvent
  case class Project(projectName: String, docs: List[Doc]) extends PairEvent

  val subject = PublishSubject[PairEvent]

  val projects: Observable[Project] = subject.scan(Option.empty[Project]) {
    case (Some(p), d: Doc) => Some(p.copy(docs = p.docs :+ d))
    case (_, p: Project) => Some(p)
    case _ => println("###"); None
  }.collect({ case Some(p) => p })
  projects.foreach(println)

  subject.onNext(Project("p1", Nil))
  subject.onNext(Doc("/a"))
  subject.onNext(Doc("/b"))
  subject.onCompleted()


}
