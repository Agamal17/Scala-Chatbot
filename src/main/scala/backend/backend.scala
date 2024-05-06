package backend

import cats.effect.unsafe.implicits.global
import ChatsDB.ChatsDB
import PrefsDB.PrefsDB
import cats.effect.{IO, IOApp}
import org.http4s.client.JavaNetClientBuilder
import org.http4s.client.Client
import org.http4s.circe.*
import io.circe.generic.auto.*
import org.http4s.headers.Accept
import org.http4s.implicits.*
import org.http4s.{EntityDecoder, Headers, MediaType, Method, Request, UrlForm}
import io.circe.Json
import io.circe.Encoder.encodeString


def parseInput (input:String):List[String]={
  val splitted=input.split(" ").toList
  def helper(list:List[String]):List[String]={
    list match{
      case Nil=>Nil
      case h1::h2::h3::tail if (h1.toLowerCase=="love"||h1=="hate")=>h1+" "+h2+" "+h3::helper(tail)
      case h1::h2::h3::tail if (h1.toLowerCase()=="name"&&h2=="is")=>h1+" "+h2+" "+h3::helper(tail)
      case h1::h2::h3::tail if (h1.toUpperCase()=="GPA")=>h1+" "+h3::helper(tail)
      case h1::h2::h3::tail if (h1.toLowerCase()=="skill"||h1=="experience")=>h1+" "+h3::helper(tail)
      case h1::h2::tail if (h1.toLowerCase()=="year"||h1=="grade")=>h1+" "+h2::helper(tail)
      case _::tail=>helper(tail)
    }
  }
  helper(splitted)
}

def postage(query: String): IO[String] = {
  val postRequest = Request[IO](
    method = Method.POST,
    uri = uri"http://localhost:2000/generate",
    headers = Headers(
      Accept(MediaType.application.json)
    ),
  ).withEntity(
      Json.obj( "msg" -> encodeString(query))
  )

  val httpClient: Client[IO] = JavaNetClientBuilder[IO].create
  val response = httpClient.expect[String](postRequest)
  response
}

def generateResponse(query: String, ID: Int): String = {
    val parsed = parseInput(query)
    if (parsed != Nil) storeUserPreferences(parsed)
    val pref = getUserPreferences

    val responsePrms = postage("Human Data (if needed for AI response):\n" + pref.getOrElse("None") + "\n\nQuery:\n" + query)
    val responseVal = responsePrms.unsafeRunSync()
    val response = responseVal.substring(1, responseVal.length - 1)
    println(response)
    ChatsDB.saveMsg(query, ID)
    ChatsDB.saveMsg(response, ID)
    response
}

def storeUserPreferences(preference: List[String]): Unit = {
  PrefsDB.addPrefs(preference)
}

def getUserPreferences: Option[String] = {
  PrefsDB.getPrefs
}

def getChat(ID: Int): List[String] = {
  ChatsDB.getChat(ID)
}

def getChats: List[String] = {
  ChatsDB.getChats
}

def createChat(name: String) : Int = {
  ChatsDB.createChat(name)
}

def deleteChat(ID: Int) : Unit = {
  ChatsDB.deleteChat(ID)
}

def getID: Int = ChatsDB.getLastID
