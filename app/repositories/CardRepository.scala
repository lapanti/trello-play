package repositories

import play.api.libs.json.{JsObject, JsArray}
import utils.Global._
import models.{Category, Card}
import play.api.libs.ws._
import play.api.Logger
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current

/**
 * Created by Lapanti on 04/08/15.
 */
object CardRepository extends TrelloRepository {
  val logger = Logger(this.getClass)
  private val getCardsUrl = BASE_URL + "/cards?key=" + APP_KEY + "&token=" + TOKEN + "&fields=name,idList,url"

  private def get(url: String): Future[Option[WSResponse]] = {
    WS.url(url).get().map(response => {
      println(response)
      response.status match {
        case 200 => Some(response)
        case _ =>
          logger.debug(response.statusText)
          None
      }
    }).recover {
      case x => println(x)
        None
    }
  }

  def getAll: Future[List[Card]] = {
    get(getCardsUrl).map {
      case Some(response) => response.json.as[List[Card]]
      case _ => Nil
    }
  }

  def getCards(category: Category): Future[Option[Card]] = {
    get(getCardsUrl).map {
      case Some(response) => response.json.asOpt[JsArray] match {
        case jsarray => jsarray.flatMap(_.asOpt[Card]).filter(_.categoryId == category.id)
      }
      case _ => None
    }
  }
}
