package backend

import ChatsDB.ChatsDB, PrefsDB.PrefsDB

def parseInput(input: String): Option[List[String]] = {
  Option(List("1"))
}

def generateResponse(query: String, ID: Int): String = {
//  val parsed = parseInput(query).getOrElse(Nil)
//  if (parsed != Nil) storeUserPreferences(parsed)
  val pref = getUserPreferences

  // Send Api request to AI Model with pref
  val response = "hello" + " Preferences: " + pref.getOrElse("")

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

def createChat(name: String) : Unit = {
  ChatsDB.createChat(name)
}

def getID: Int = ChatsDB.getLastID
