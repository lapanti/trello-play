package repositories

import play.api.libs.json.JsArray
import utils.Global._
import models.{Category, Card}
import play.api.libs.ws._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current

/**
 * Created by Lapanti on 04/08/15.
 */
object CardRepository extends TrelloRepository {
  private val getCardsUrl = BOARDURL + "/cards?key=" + KEY + "&fields=name,idList,url"

  private def get(url: String): Future[Option[WSResponse]] = {
    WS.url(url).get().map(response => response.status match {
      case 200 => Some(response)
      case _ => None
    })
  }

  def getAll: Future[Option[Card]] = {
    get(getCardsUrl).map( responseOpt =>
      responseOpt match {
        case Some(response) => response.json.asOpt[JsArray] match {
          case jsarray => jsarray.flatMap(_.asOpt[Card])
        }
        case _ => None
      }
    )
  }

  def getCards(category: Category): Future[Option[Card]] = {
    get(getCardsUrl).map( responseOpt =>
      responseOpt match {
        case Some(response) => response.json.asOpt[JsArray] match {
          case jsarray => jsarray.flatMap(_.asOpt[Card]).filter(_.categoryId == category.id)
        }
        case _ => None
      }
    )
  }
}
