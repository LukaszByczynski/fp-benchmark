name := "fp-benchmark"
version := "1.0"
scalaVersion := "2.12.11"

scalacOptions ++= Seq(
  "-feature",
  "-language:higherKinds",
  "-Ypartial-unification"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.10")

libraryDependencies ++= {
  val CatsEffect = Seq(
    "org.typelevel" %% "cats-effect" % "2.3.3" % VersionScheme.Always
  )

  val ZIO = Seq(
    "dev.zio" %% "zio" % "1.0.4"
  )

  val CatsMtl = Seq(
    "org.typelevel" %% "cats-mtl-core" % "0.7.1",
    "com.olegpy" %% "meow-mtl" % "0.2.1"
  )

  val ScalaTest = Seq(
    "org.scalatest" %% "scalatest" % "3.1.1" % Test
  )

  // val MinifpIO = Seq(
  // "minifp" %% "io" % "0.1"
  // )

  val Monix = Seq(
    "io.monix" %% "monix" % "3.3.0"
  )

  Monix ++ ZIO ++ CatsEffect ++ CatsMtl ++ ScalaTest // ++ MinifpIO
}

enablePlugins(JmhPlugin)
