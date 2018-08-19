
name := "fp-benchmark"
version := "1.0"
scalaVersion := "2.12.6"

scalacOptions ++= Seq(
  "-feature",
  "-language:higherKinds",
  "-Ypartial-unification"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.7")

libraryDependencies ++= {
  val CatsEffect = Seq(
    "org.typelevel" %% "cats-effect" % "1.0.0-RC2"
  )

  val CatsMtl = Seq(
    "org.typelevel" %% "cats-mtl-core" % "0.3.1-SNAPSHOT",
    "com.olegpy" %% "meow-mtl" % "0.1.1"
  )

  val ScalaTest = Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )

  CatsEffect ++ CatsMtl ++ ScalaTest
}

enablePlugins(JmhPlugin)