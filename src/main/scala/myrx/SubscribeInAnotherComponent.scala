package myrx

import rx.lang.scala.Observable
import rx.lang.scala.subjects.PublishSubject

import scala.swing._

object MyStream {
  val source = PublishSubject[Int]()
  val childStream = source.scan(0)(_ + _)
}

object SubscribeInAnotherComponent extends SimpleSwingApplication {

  import scala.swing.Orientation._


  override def top: Frame = new MainFrame {
    contents = new BoxPanel(Vertical) {panel =>
      contents += new Button("Increase number") {
        var i = 1
        reactions += {
          case _ => {
            MyStream.source.onNext(i)
            i += 1
          }
        }
      }
      contents += new Button("show hidden panel") {
        reactions += {
          case _ =>
            panel.contents += new Label() {
              MyStream.childStream.foreach(x => this.text = x.toString)
            }
            panel.contents += new MyComponent(MyStream.childStream)
        }
      }
    }
  }
}

class MyComponent(childStream: Observable[Int]) extends Button {
  MyStream.childStream.subscribe(x => this.text = x.toString)
}
