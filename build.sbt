name := "synthsizerRemastered"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "org.lwjgl" % "lwjgl-openal" % "3.2.3" ,
  "org.lwjgl" % "lwjgl" % "3.2.3" ,
  "org.lwjgl" % "lwjgl-openal" % "3.2.3" classifier "natives-macos",
  "org.lwjgl" % "lwjgl" % "3.2.3" classifier "natives-macos",
)
