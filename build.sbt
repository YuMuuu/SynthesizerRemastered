

lazy val root = (project in file(".")).
  settings(
    name := "synthsizerRemastered",
    version := "0.1",
    scalaVersion := "2.13.1",
    scalacOptions := Seq(
      "-Ylog-classpath",
      //            "-target:jvm-1.8",
    ),
    libraryDependencies ++= Seq(
      //OpenAL
      "org.lwjgl" % "lwjgl-openal" % "3.2.3",
      "org.lwjgl" % "lwjgl" % "3.2.3",
      "org.lwjgl" % "lwjgl-openal" % "3.2.3" classifier "natives-macos",
      "org.lwjgl" % "lwjgl" % "3.2.3" classifier "natives-macos",
      //cats
      "org.typelevel" %% "cats-core" % "2.0.0",
      //scala-fx
      "org.scalafx" %% "scalafx" % "12.0.2-R18",
    )
  )

// Determine OS version of JavaFX binaries
lazy val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}

// Add dependency on JavaFX libraries, OS dependent
lazy val javaFXModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
libraryDependencies ++= javaFXModules.map(m =>
  "org.openjfx" % s"javafx-$m" % "12.0.2" classifier osName
)

