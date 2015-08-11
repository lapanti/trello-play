package utils

import play.api.Play._

/**
 * Created by Lapanti on 04/08/15.
 */
object Global {
  val BASE_URL = current.configuration.getString("trello.api.baseurl").get
  val BOARD_ID = current.configuration.getString("trello.api.boardid").get
  val KEY = current.configuration.getString("trello.api.key").get
}
