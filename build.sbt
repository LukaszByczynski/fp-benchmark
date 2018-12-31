
name := "fp-benchmark"
version := "1.0"
scalaVersion := "2.12.8"

scalacOptions ++= Seq(
  "-feature",
  "-language:higherKinds",
  "-Ypartial-unification"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.9")

libraryDependencies ++= {
  val CatsEffect = Seq(
    "org.typelevel" %% "cats-effect" % "1.1.0"
  )

  val CatsMtl = Seq(
    "org.typelevel" %% "cats-mtl-core" % "0.4.0",
    "com.olegpy" %% "meow-mtl" % "0.2.0"
  )

  val ScalaTest = Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )

  CatsEffect ++ CatsMtl ++ ScalaTest
}

enablePlugins(JmhPlugin)