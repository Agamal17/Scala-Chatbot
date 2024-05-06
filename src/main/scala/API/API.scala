package API

import org.http4s.server.middleware.*
import org.http4s.implicits.*
import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory
import cats.effect.*
import io.circe.generic.auto.*
import io.circe.syntax.*
import org.http4s.*
import org.http4s.dsl.io.*
import org.http4s.circe.*
import backend.getID
import backend.getChat
import backend.getChats
import backend.generateResponse
import backend.createChat
import backend.deleteChat
import org.http4s.dsl.Http4sDsl

case class chatName(name: String)
object chatName {
  implicit val decoder: EntityDecoder[IO, chatName] = jsonOf[IO, chatName]
}

case class Message(msg: String, id: Int)
object Message {
  implicit val decoder: EntityDecoder[IO, Message] = jsonOf[IO, Message]
}

val service: HttpRoutes[IO] = HttpRoutes.of[IO] {

  case GET -> Root / "fetch" / IntVar(id) =>
    Ok(getChat(id).asJson.noSpaces)

  case GET -> Root / "id"  =>
    Ok(getID.asJson.noSpaces)

  case GET -> Root / "chats"  =>
    Ok(getChats.asJson.noSpaces)

  case DELETE -> Root / "delete" / IntVar(id) =>
    Ok(deleteChat(id).asJson.noSpaces)

  case req @ POST -> Root / "send" =>
    req.as[Message].flatMap { message =>
      Ok(generateResponse(message.msg, message.id).asJson.noSpaces)
    }

  case req @ POST -> Root / "create" =>
    req.as[chatName].flatMap { name =>
      Ok(createChat(name.name).asJson.noSpaces)
    }
}
