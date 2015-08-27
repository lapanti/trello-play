package utils

import utils.Global._
import play.api.libs.oauth.{RequestToken, ServiceInfo, OAuth, ConsumerKey}
import play.api.mvc.{RequestHeader, Action, Controller}

object TrelloOAuth extends Controller {

  private val KEY = ConsumerKey(APP_KEY, APP_SECRET)
  private val use10a = true

  private val TRELLO = OAuth(ServiceInfo(
    "https://trello.com/1/OAuthGetRequestToken",
    "https://trello.com/1/OAuthGetAccessToken",
    "https://trello.com/1/OAuthAuthorizeToken?name=AR-Storyboard&scope=read,write&expiration=never", KEY),
    use10a)

  def authorize() = Action {
    implicit request =>
      request.queryString.get("oauth_verifier").flatMap(_.headOption).map { verifier =>
        val tokenPair = sessionTokenPair(request).get
        // We got the verifier; now get the access token, store it and back to index
        TRELLO.retrieveAccessToken(tokenPair, verifier) match {
          case Right(t) => {
            // We received the authorized tokens in the OAuth object - store it before we proceed
            Ok(s"Your new token is: ${t.token} and your secret is ${t.secret}")
          }
          case Left(e) => throw e
        }
      }.getOrElse(
          TRELLO.retrieveRequestToken("http://localhost:9000/authorize") match {
            case Right(t) => {
              // We received the unauthorized tokens in the OAuth object - store it before we proceed
              Redirect(TRELLO.redirectUrl(t.token)).withSession("token" -> t.token, "secret" -> t.secret)
            }
            case Left(e) => throw e
          })
  }

  def sessionTokenPair(implicit request: RequestHeader): Option[RequestToken] = {
    for {
      token <- request.session.get("token")
      secret <- request.session.get("secret")
    } yield {
      RequestToken(token, secret)
    }
  }
}