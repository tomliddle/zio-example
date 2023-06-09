import zio.{CancelableFuture, Duration, IO, Task, URIO, ZIO, ZIOAppDefault}

import java.io.IOException
import java.util.concurrent.CompletableFuture
import scala.concurrent.Future

object Main extends ZIOAppDefault {

  override def run: ZIO[Any, Throwable, Unit] = {
    example1.flatMap(_ => example2)
    example13 *> ZIO.unit
  }

  val example1: ZIO[Any, IOException, Unit] = zio.Console.printLine("write to console")

  val example2: IO[IOException, Unit] = zio.Console.printLine("write to console 2")

  val example3: IO[IOException, Unit] =
    example1.flatMap(_ => example2)

  val example4: ZIO[Any, IOException, Unit] = for {
    _ <- example1
    - <- example2
  } yield ()

  val example5: ZIO[Any, IOException, Unit] =
    example1 <* example2

  case class MyErrorType(message: String)
  case class MySuccessType()

  val example6: IO[MyErrorType, MySuccessType] = {

    val myFunc: IO[MyErrorType, MySuccessType] =
      if (false) ZIO.fail(MyErrorType("Its false!"))
      else ZIO.succeed(MySuccessType())

    for {
      _ <- example1
        .mapError(e => MyErrorType(e.getMessage))
      result <- myFunc
    } yield result
  }

  val example7: Task[Int] = ZIO.attempt(1 / 0)

  val example8 = for {
      _             <- example1
      numberService <- ZIO.service[NumberService]
      result        <- numberService.divide(1000)
      res           <- numberService.randomNumber(0, result)
    } yield res

  val example9: ZIO[Any, Throwable, Double] = example8.provideLayer(NumberService.myRandomNumberLayer)

  val example10: ZIO[BigService, Throwable, Double] = for {
    bigService <- ZIO.service[BigService]
    result <- bigService.doStuff
  } yield result

  val example11: ZIO[Any, Throwable, Double] = example10.provide(BigService.bigServiceLayer, NumberService.myRandomNumberLayer)

  val waitAndLog = ZIO.sleep(Duration.fromSeconds(5)) &> ZIO.log("finished after 5 seconds")

  val example12: ZIO[Any, Nothing, List[Unit]] = ZIO.collectAll(List.fill(2)(waitAndLog))

  val example13: ZIO[Any, Nothing, List[Unit]] = ZIO.collectAllPar(List.fill(5)(waitAndLog))

  // N.b. needs to be a def otherwise it will have already run before converting to ZIO
  def fut = Future.successful("")

  val example14: Task[String] = ZIO.fromFuture(e => fut)

  val example15: Task[URIO[Any, CancelableFuture[String]]] = ZIO.attemptUnsafe(_ => ZIO.fromFuture(_ => fut).toFuture)

  val example16: ZIO[Any, Option[Nothing], String] = ZIO.fromOption(Some("")) // or ZIO.from(Some("")) or ZIO.some("")

  val example17: IO[Exception, String] = ZIO.fromEither(Right("").withLeft[Exception]) // or ZIO.from(Right("").withLeft[Exception]) or ZIO.right("")

}
