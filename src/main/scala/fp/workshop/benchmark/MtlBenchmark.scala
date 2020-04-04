package fp.workshop.benchmark

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.State
import org.openjdk.jmh.annotations.Scope
import monix.execution.Scheduler
import monix.execution.schedulers.CanBlock

@State(value = Scope.Benchmark)
class MtlBenchmarkState {
  val codebase = new Codebase
}

class MtlBenchmark {

  @Benchmark
  def mtlStack(state: MtlBenchmarkState): Unit = {
    state.codebase.mtlStack.unsafeRunSync()
  }

  @Benchmark
  def mtlStackOpt(state: MtlBenchmarkState): Unit = {
    state.codebase.mtlStackOpt.unsafeRunSync()
  }

  @Benchmark
  def mtlMeow(state: MtlBenchmarkState): Unit = {
    state.codebase.mtlMeow.unsafeRunSync()
  }

  @Benchmark
  def imperative(state: MtlBenchmarkState): Unit = {
    state.codebase.imperative(Config("", 1), 0, List[String]())
  }

  @Benchmark
  def io(state: MtlBenchmarkState): Unit = {
    state.codebase.io(Config("", 1), 0, List[String]()).unsafeRunSync()
  }

  @Benchmark
  def io_zio(state: MtlBenchmarkState): Unit = {
    state.codebase.rts
      .unsafeRun(state.codebase.zio(Config("", 1), 0, List[String]()))
  }

  // @Benchmark
  // def minifpIo(state: MtlBenchmarkState): Unit = {
  //   minifp.io.Runtime
  //     .unsafeRun(state.codebase.minifpIO(Config("", 1), 0, List[String]()))
  // }

  @Benchmark
  def monxIo(state: MtlBenchmarkState): Unit = {
    state.codebase
      .monixIO(Config("", 1), 0, List[String]())
      .runSyncUnsafe()(Scheduler.global, CanBlock.permit)
  }

}
