package fp.workshop.benchmark

import org.openjdk.jmh.annotations.Benchmark

class MtlBenchmark {

  @Benchmark
  def mtlStack(): Unit = {
    val codebase = new Codebase
    1.to(1000).foreach { _ =>
      codebase.mtlStack.unsafeRunSync()
    }
  }

  @Benchmark
  def mtlStackOpt(): Unit = {
    val codebase = new Codebase
    1.to(1000).foreach { _ =>
      codebase.mtlStackOpt.unsafeRunSync()
    }
  }

  @Benchmark
  def mtlMeow(): Unit = {
    val codebase = new Codebase
    1.to(1000).foreach { _ =>
      codebase.mtlMeow.unsafeRunSync()
    }
  }

  @Benchmark
  def imperative(): Unit = {
    val codebase = new Codebase
    1.to(1000).foreach { _ =>
      codebase.imperative(Config("", 1), 0, List[String]())
    }
  }

  @Benchmark
  def io(): Unit = {
    val codebase = new Codebase
    1.to(1000).foreach { _ =>
      codebase.io(Config("", 1), 0, List[String]()).unsafeRunSync()
    }
  }

}