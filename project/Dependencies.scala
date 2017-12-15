import sbt._

object Dependencies {
  lazy val akkaVersion = "2.5.3"
  lazy val   scalaTest = "org.scalatest"      %% "scalatest"    % "3.0.3"
  lazy val   akkaActor = "com.typesafe.akka"  %% "akka-actor"   % akkaVersion
  lazy val akkaTestkit = "com.typesafe.akka"  %% "akka-testkit" % akkaVersion
}
