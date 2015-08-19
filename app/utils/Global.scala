package utils

import play.api.Play._

/**
 * Created by Lapanti on 04/08/15.
 */
object Global {
  val BASE_URL = current.configuration.getString("trello.api.baseurl").get
  val BOARD_ID = current.configuration.getString("trello.api.boardid").get

  // Authorization keys and secrets
  val APP_KEY = current.configuration.getString("trello.api.key").get
  val APP_SECRET = current.configuration.getString("trello.api.secret").get
  val TOKEN = current.configuration.getString("trello.api.token").get
}
