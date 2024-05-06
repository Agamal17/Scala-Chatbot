import cats.effect.*
//import org.http4s.implicits.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.middleware.*
import org.http4s.dsl.*
import org.http4s.*
import API._
import com.comcast.ip4s.{ipv4, port}


object server extends IOApp.Simple{

  val corsService = CORS.policy
    .withAllowOriginAll.apply(service) // Allow all origins (adjust as needed)

  val serverResource = EmberServerBuilder
    .default[IO]
    .withHost(ipv4"0.0.0.0")
    .withPort(port"4000")
    .withHttpApp(corsService.orNotFound)
    .build

  override def run = serverResource.use(_ => IO.println("Server...") *> IO.never) //Set the output as Success
}
