package controllers

import play.api.Play.current
import play.api.mvc._
import utils.OAuth2

/**
 * Created by Lapanti on 04/08/15.
 */
object Application extends Controller {

  def index = Action { request =>
    val oauth2 = new OAuth2(current)
    val callbackUrl = util.routes.OAuth2.callback(None, None).absoluteURL()
    val scope = "repo"   // github scope - request repo access
    val state = UUID.randomUUID().toString  // random confirmation string
    val redirectUrl = oauth2.getAuthorizationUrl(callbackUrl, scope, state)
    Ok(views.html.index("Your new application is ready.", redirectUrl)).
      withSession("oauth-state" -> state)
  }

}
