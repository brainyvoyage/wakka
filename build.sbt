import Dependencies._


lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.brainyvoyage.wakka",
      scalaVersion := "2.12.2",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Wakka-akka",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      akkaActor,
      akkaStream,
      akkaTestkit
    )
  )