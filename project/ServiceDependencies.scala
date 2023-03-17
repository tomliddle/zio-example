import sbt._

object ServiceDependencies {
  def apply(): Seq[ModuleID] =  Seq(zioServiceDependencies ++ logging).flatten

  val zioVersion = "2.0.10"

  val zioServiceDependencies: Seq[ModuleID] = Seq(
    "dev.zio" %% "zio"            % zioVersion,
    "dev.zio" %% "zio-logging"    % "2.1.10",
    "dev.zio" %% "zio-concurrent" % zioVersion,
    "dev.zio" %% "zio-test"       % zioVersion % Test,
    "dev.zio" %% "zio-test-sbt"   % zioVersion % Test
  )

  val logging = Seq("ch.qos.logback" % "logback-classic" % "1.2.11")
}
