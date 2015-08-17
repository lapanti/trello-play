package controllers

import play.api.Play.current
import play.api.mvc._
import utils.OAuth2

/**
 * Created by Lapanti on 04/08/15.
 */
object Application extends Controller {

  def index = Action { implicit request =>
    val oauth2 = new OAuth2(current)
    val callbackUrl = utils.routes.OAuth2.callback(None, None).absoluteURL()
    val scope = "read, write"   // read and/or write
    Ok(oauth2.getAuthorizationUrl(callbackUrl, scope))
  }
}
