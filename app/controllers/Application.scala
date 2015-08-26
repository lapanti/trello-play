package controllers

import models.{Category, TrelloId}
import play.api.mvc._
import repositories.CardRepository
import utils.Global._

/**
 * Created by Lapanti on 04/08/15.
 */
object Application extends Controller {
  private val getCardsUrl = BASE_URL + "/boards/" + BOARD_ID + "/cards"

  def index = Action { implicit request =>
    if (TOKEN == "")
      TemporaryRedirect(utils.routes.TrelloOAuth.authorize().absoluteURL())
    else Ok(s"Your token is: $TOKEN")
  }

  def cards(categoryOpt: Option[String]) = Action { implicit request =>
    categoryOpt.map{ id =>
      Ok(s"Cards in category: ${CardRepository.getCards(new Category(TrelloId(id), "", Nil))}")
    }.getOrElse(Ok(s"Cards: ${CardRepository.getAll}"))
  }
}
