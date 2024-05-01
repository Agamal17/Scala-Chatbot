ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.4.1"

val http4sVersion = "0.23.16"

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-dsl"          % http4sVersion,
  "org.http4s" %% "http4s-core"          % http4sVersion,
  "org.http4s" %% "http4s-circe"          % http4sVersion,
  "org.typelevel" %% "log4cats-slf4j" % "2.6.0",
  "org.xerial" % "sqlite-jdbc" % "3.39.2.1",

  "io.circe" %% "circe-core" % "0.14.6",
  "io.circe" %% "circe-generic" % "0.14.6",

)