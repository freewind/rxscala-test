package myrx

import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject

object SubtleBug extends App {

  case class Projects(projects: List[Project] = Nil)
  case class Project(name: String, docs: List[Doc] = Nil)
  case class Doc(path: String, baseContent: String)

  sealed trait ServerEvent
  case class ProjectNames(projects: Seq[String]) extends ServerEvent
  case class NewDocument(projectName: String, path: String, version: Int, content: String) extends ServerEvent

  val receivedEvents = PublishSubject[ServerEvent]

  new Thread(new Runnable {
    override def run(): Unit = {
      val events = Seq(
        new ProjectNames(Seq("p1", "p2")),
        NewDocument("p1", "/aaa", 1, "my-content"),
        NewDocument("p1", "/bbb", 1, "my-content")
      )
      events.foreach { event =>
        receivedEvents.onNext(event)
        Thread.sleep(200)
      }
    }
  }).start()


  //  lazy val projects: Observable[Projects] = receivedEvents.scan(Projects(Nil)) {
  //    case (_, ProjectNames(names)) => {
  //      Projects(names.map(name => Project(name)).toList)
  //    }
  //    case (ps, NewDocument(projectName, docPath, version, content)) => {
  //      val doc = Doc(docPath, content)
  //      ps.copy(projects = ps.projects.map {
  //        case project if project.name == projectName => project.copy(docs = project.docs ::: List(doc))
  //        case p => p
  //      })
  //    }
  //  }

  lazy val projects: Observable[Option[Projects]] = receivedEvents.scan(Option.empty[Projects]) {
    case (_, ProjectNames(names)) => {
      Some(Projects(names.map(name => Project(name)).toList))
    }
    case (Some(ps), NewDocument(projectName, docPath, version, content)) => {
      val doc = Doc(docPath, content)
      val newPs = ps.copy(projects = ps.projects.map {
        case project if project.name == projectName => project.copy(docs = project.docs ::: List(doc))
        case p => p
      })
      Some(newPs)
    }
    case _ => None
  }.collect({
    case Some(p) => Some(p)
    case None => None
  })

  projects.foreach(ps => {
    println("### 111: " + ps.map(_.projects))
    projects.foreach(x => println("### 222: " + ps.map(_.projects))) // !!!(2)
  })

  Thread.sleep(2000)

}
