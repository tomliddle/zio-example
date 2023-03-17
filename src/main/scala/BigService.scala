import zio.{Task, ULayer, ZIO, ZLayer}

trait BigService {
  def doStuff: Task[Double]
}

case class BigServiceLive(numberService: NumberService) extends BigService {

  override def doStuff: Task[Double] = numberService.divide(5)
}

object BigService {

  val bigServiceLayer: ZLayer[NumberService, Nothing, BigServiceLive] = ZLayer.fromFunction(BigServiceLive(_))

  // Other ways to create layers:
  case class Service()
  val anotherLayer: ULayer  [Service] = ZLayer { ZIO.succeed(Service())  }
  val anotherLayer2: ULayer[Service] = ZLayer.succeed(Service())
}