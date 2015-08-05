package utils

import play.api.Play._

/**
 * Created by Lapanti on 04/08/15.
 */
object Global {
  val BOARDURL = current.configuration.getString("trello.url").get + "?key=" + current.configuration.getString("trello.key").get
}
