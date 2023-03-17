import zio.{ULayer, ZIO}

trait NumberService {

  def randomNumber(from: Double, to: Double): ZIO[Any, Throwable, Double]

  def divide(num: Double): ZIO[Any, Throwable, Double]

  def multiply(num: Double): ZIO[Any, Nothing, Double]
}

case class NumberServiceLive() extends NumberService {

  override def randomNumber(from: Double, to: Double): ZIO[Any, Throwable, Double] =
    ZIO.attempt(scala.util.Random.between(from, to))

  override def divide(num: Double): ZIO[Any, Throwable, Double] =
    ZIO.attempt(num / num)

  override def multiply(num: Double): ZIO[Any, Nothing, Double] =
    ZIO.succeed(num * num)
}

object NumberService {

  val myRandomNumberLayer: ULayer[NumberServiceLive] = zio.ZLayer.succeed(NumberServiceLive())
}
