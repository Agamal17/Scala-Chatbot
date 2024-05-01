package ChatsDB

import java.sql._
import scala.collection.mutable

object ChatsDB{
    val url = "jdbc:sqlite:src/main/scala/DB/chats/chats.db"
    Class.forName("org.sqlite.JDBC")

    val connection = DriverManager.getConnection(url)

    def createChat(name: String): Unit = {
      val statement = connection.createStatement()
      val lastID = getLastID+1
      val sql =
        s"""
            CREATE TABLE IF NOT EXISTS "$lastID" (msg TEXT)
        """

      statement.execute(sql)

      val query =
        s"""
            INSERT INTO chats (ID, Name) Values ($lastID, '$name');
        """

      statement.execute(query)


      val incr =
        s"""
            UPDATE ID
        SET id = $lastID
        """

      statement.execute(incr)
      statement.close()
    }

    def getChats: List[String] = {
      val statement = connection.createStatement()
      val resultSet = statement.executeQuery("SELECT Name FROM chats")

      val entriesList = List.newBuilder[String]
      while (resultSet.next()) {
        entriesList += resultSet.getString("Name")
      }
      resultSet.close()
      statement.close()
      entriesList.result()
    }

    def getChat(ID: Int): List[String] = {
      val sql =
        s"""
            SELECT msg
        FROM "$ID";
        """
      val statement = connection.prepareStatement(sql)

      val resultSet = statement.executeQuery()

      val messages = List.newBuilder[String]
      while (resultSet.next()) {
        messages += resultSet.getString("msg")
      }

      val allMessages = messages.result()

      resultSet.close()
      statement.close()
      allMessages
    }

    def getLastID : Int = {
      val statement = connection.createStatement()

      val query = "SELECT id FROM ID LIMIT 1"
      val resultSet = statement.executeQuery(query)

      resultSet.next()
      val Value = resultSet.getInt(1)
      resultSet.close()
      statement.close()
      Value
    }

    def saveMsg(msg: String, ID: Int) : Unit = {
      val statement = connection.createStatement()
      val query = s"INSERT INTO '$ID' (msg) VALUES ('$msg')"
      statement.executeUpdate(query)
      statement.close()
    }

  }
