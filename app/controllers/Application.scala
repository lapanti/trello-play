package controllers

import play.api.Play.current
import play.api.mvc._
import utils.TrelloOAuth
import utils.Global._

/**
 * Created by Lapanti on 04/08/15.
 */
object Application extends Controller {

  def index = Action { implicit request =>
    if (TOKEN == "")
      TemporaryRedirect(utils.routes.TrelloOAuth.authorize().absoluteURL())
    else Ok(s"Your token is: $TOKEN")
  }
}
