package fp.workshop.benchmark

import java.util.concurrent.atomic.AtomicReference

import cats.Monad
import cats.data._
import cats.effect.IO
import cats.effect.concurrent.Ref
import cats.mtl.{ApplicativeAsk, FunctorTell, MonadState}
import monix.eval.Task
import monix.execution.atomic.Atomic

case class Config(name: String, token: Int)

class Codebase {

  import cats.syntax.flatMap._
  import cats.syntax.functor._

  type Log = List[String]

  val rts = _root_.zio.Runtime.default

  def imperative(
      R: => Config,
      state: Int,
      log: List[String]
  ): (List[String], String, Int) = {
    val stateR = new AtomicReference[Int](state)
    val logR = new AtomicReference[List[String]](log)

    val result = R
    stateR.updateAndGet(_ + 1)
    logR.updateAndGet(_ ++ List(s"test + $result"))
    stateR.updateAndGet(_ + 1)
    val st = stateR.get()
    logR.updateAndGet(_ ++ List(s"test2 + $st"))

    (logR.get(), "pk", stateR.get())
  }

  // def minifpIO(
  //     R: => Config,
  //     state: Int,
  //     log: List[String]
  // ) = {
  //   import minifp.io.{IO => MIO}

  //   for {
  //     stateR <- MIO.effect(new AtomicReference[Int](state))
  //     logR <- MIO.effect(new AtomicReference[List[String]](log))
  //     result <- MIO.pure(R)
  //     _ <- MIO.effect(stateR.updateAndGet(_ + 1))
  //     _ <- MIO.effect(logR.updateAndGet(_ ++ List(s"test + $result")))
  //     _ <- MIO.effect(stateR.updateAndGet(_ + 1))
  //     st <- MIO.effect(stateR.get())
  //     _ <- MIO.effect(logR.updateAndGet(_ ++ List(s"test2 + $st")))
  //     a <- MIO.effect(logR.get())
  //     b <- MIO.effect(stateR.get())
  //   } yield (a, "pk", b)
  // }

  def monixIO(
      R: => Config,
      state: Int,
      log: List[String]
  ) = {

    for {
      stateR <- Task(Atomic(state))
      logR <- Task(Atomic(log))
      result <- Task.pure(R)
      _ <- Task(stateR.addAndGet(1))
      _ <- Task(logR.transform(_ ++ List(s"test + $result")))
      _ <- Task(stateR.transform(_ + 1))
      st <- Task(stateR.get())
      _ <- Task(logR.transform(_ ++ List(s"test2 + $st")))
      a <- Task(logR.get())
      b <- Task(stateR.get())
    } yield (a, "pk", b)
  }

  def io(
      R: => Config,
      state: Int,
      log: List[String]
  ): IO[(List[String], String, Int)] = {
    for {
      stateR <- Ref[IO].of(state)
      logR <- Ref[IO].of(log)
      result <- IO(R)
      _ <- stateR.update(_ + 1)
      _ <- logR.update(_ ++ List(s"test + $result"))
      st <- stateR.modify(s => (s + 1, s + 1))
      _ <- logR.update(_ ++ List(s"test2 + $st"))
      log2 <- logR.get
      state2 <- stateR.get
    } yield (log2, "pk", state2)
  }

  def zio(
      R: => Config,
      state: Int,
      log: List[String]
  ) = {
    import _root_.zio.{ZIO, Ref => ZRef}

    for {
      stateR <- ZRef.make(state)
      logR <- ZRef.make(log)
      result <- ZIO.succeed(R)
      _ <- stateR.update(_ + 1)
      _ <- logR.update(_ ++ List(s"test + $result"))
      st <- stateR.modify(s => (s + 1, s + 1))
      _ <- logR.update(_ ++ List(s"test2 + $st"))
      log2 <- logR.get
      state2 <- stateR.get
    } yield (log2, "pk", state2)
  }

  def program[F[_]: Monad](
      implicit
      W: FunctorTell[F, Log],
      R: ApplicativeAsk[F, Config],
      MS: MonadState[F, Int]
  ): F[String] = {
    for {
      result <- R.ask
      _ <- MS.modify(_ + 1)
      _ <- W.tell(List(s"test + $result"))
      _ <- MS.modify(_ + 1)
      st <- MS.get
      _ <- W.tell(List(s"test2 + $st"))
    } yield "pk"
  }

  def mtlMeow: IO[(List[String], String, Int)] = {

    import cats.instances.list._
    import com.olegpy.meow.effects._

    for {
      readerRef <- Ref[IO].of(Config("", 1))
      writerRef <- Ref[IO].of(List[String]())
      stateRef <- Ref[IO].of(0)
      result <- readerRef.runAsk { implicit reader =>
        writerRef.runTell { implicit writer =>
          stateRef.runState { implicit state =>
            program[IO]
          }
        }
      }
      writerRes <- writerRef.get
      stateRes <- stateRef.get
    } yield (writerRes, result, stateRes)
  }

  def mtlStack: IO[(Int, (Log, String))] = {
    import cats.implicits._
    import cats.mtl.implicits._

    program[ReaderT[WriterT[StateT[IO, Int, ?], Log, ?], Config, ?]]
      .run(Config("", 1))
      .run
      .run(0)
  }

  def mtlStackOpt: IO[(Log, Int, String)] = {
    import cats.implicits._
    import cats.mtl.implicits._

    program[ReaderWriterStateT[IO, Config, Log, Int, ?]].run(Config("", 1), 0)
  }
}
