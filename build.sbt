name := """play-java-2.3"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
										.enablePlugins(PlayJava)
										.enablePlugins(SbtWeb)
										.enablePlugins(SbtWeb)
lazy val hello = taskKey[Unit]("Prints 'Hello World'")

hello := println("hello world!")

compile <<= (compile in Compile) dependsOn hello
scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.18"
libraryDependencies += filters
libraryDependencies += "org.jsoup" % "jsoup" % "1.7.2"

pipelineStages := Seq(digest)

fork in run := false