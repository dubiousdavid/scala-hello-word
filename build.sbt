lazy val root = (project in file(".")).
  settings(
    name := "hello",
    version := "1.0",
    scalaVersion := "2.11.7"
  ).
  settings(
    libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"
  )
