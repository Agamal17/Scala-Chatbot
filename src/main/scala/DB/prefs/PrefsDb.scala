package PrefsDB

import java.sql._

object PrefsDB{
  val url = "jdbc:sqlite:src/main/scala/DB/prefs/prefs.db"
  Class.forName("org.sqlite.JDBC")
  val connection = DriverManager.getConnection(url)

  def getPrefs: Option[String] = {
    val statement = connection.createStatement()
    val sql =
      """
          SELECT pref
      FROM prefs;
      """
    val resultSet = statement.executeQuery(sql)

    val sb = new StringBuilder

    while (resultSet.next()) {
      sb.append(resultSet.getString("pref"))
      sb.append(", ")
    }

    resultSet.close()
    statement.close()
    Option(sb.toString().dropRight(2))
  }

  def addPrefs(prefs: List[String]): Unit = {
    val statement = connection.prepareStatement("INSERT INTO prefs (pref) VALUES (?)")

    for (pref <- prefs) {
      statement.setString(1, pref)
      statement.executeUpdate()
    }

    statement.close()
  }
  println(getPrefs)

}
