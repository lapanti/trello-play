package repositories

import play.api.libs.json.JsValue
import utils.Global._
import models.{Category, Card}
import play.api.libs.ws._
import scala.concurrent.Future
import play.api.Play.current
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by Lapanti on 04/08/15.
 */
object CardRepository extends TrelloRepository {

  def getCards(category: Category): Future[Option[JsValue]] = {
    WS.url(BOARDURL).get().map( response =>
      response.status match {
        case 200 => Some(response.json)
        case _ => None
      }
    )
  }
}
