
name := "TwitterStreamApi"

version := "0.1"

scalaVersion := "2.12.8"

val akkaV = "2.5.3"
val akkaHttpV = "10.1.3"
val scalaTestV = "3.0.1"
val aaaSdkVersion = "2.2.0"

assemblyMergeStrategy in assembly in ThisBuild := {
  case "application.conf" => MergeStrategy.rename
  case x => MergeStrategy.first
}

mainClass in assembly := Some("com.nike.hackathon.amigos.Main")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
  "com.typesafe.akka" %% "akka-stream" % "2.5.18",
  "org.slf4j" % "slf4j-simple" % "1.6.1",
  "org.twitter4j" % "twitter4j-core" % "4.0.7",
  "org.twitter4j" % "twitter4j-stream" % "4.0.7",
  "com.amazonaws" % "aws-java-sdk" % "1.11.463" excludeAll(
    ExclusionRule(organization = "io.netty")
  ))