name := "AR-Storyboard"

version := "0.1.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-feature")

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  specs2 % Test,
  ws
)

scalacOptions in Test ++= Seq("-Yrangepos")
