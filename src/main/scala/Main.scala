import zio.{IO, Task, ZIO, ZIOAppDefault}

import java.io.IOException

object Main extends ZIOAppDefault {

  override def run: ZIO[Any, Throwable, Unit] = {
    example1
    example2
  }

  val example1: IO[IOException, Unit] = zio.Console.printLine("write to console")

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

  val myFunc: IO[MyErrorType, MySuccessType] =
    if (false) ZIO.fail(MyErrorType("Its false!"))
    else ZIO.succeed(MySuccessType())

  val example6: IO[MyErrorType, MySuccessType] = for {
    _ <- example1
           .mapError(e => MyErrorType(e.getMessage))
    result <- myFunc
  } yield result

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

}
