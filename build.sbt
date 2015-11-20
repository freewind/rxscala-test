name := "rxscala-test"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies in ThisBuild ++= Seq(
  "org.scala-lang.modules" %% "scala-swing" % "1.0.2",
  "io.reactivex" %% "rxscala" % "0.25.0",
  "io.reactivex" % "rxswing" % "0.21.0",
  "com.github.benhutchison" %% "scalaswingcontrib" % "1.5"
)
