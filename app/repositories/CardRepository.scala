package repositories

import utils.Global._
import models.{TrelloId, Category, Card}
import play.api.libs.ws._
import play.api.Logger
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import play.api.Play.current

/**
 * Created by Lapanti on 04/08/15.
 */
object CardRepository extends TrelloRepository {
  val logger = Logger(this.getClass)
  private val getCardsUrl = BASE_URL + "/boards/" + BOARD_ID + "/cards"
  private val getCardUrl = BASE_URL + "/cards/"

  def getAll: List[Card] = {
    val queryString = Seq("key" -> APP_KEY, "token" -> TOKEN, "fields" -> "name,idList,url")
    val cards = WS.url(getCardsUrl).withQueryString(queryString:_*).get().map{ response =>
      response.json.as[List[Card]]
    }.recover{
      case error: Throwable =>
        error.printStackTrace()
        Nil
    }
    Await.result(cards, 5000 millis)
  }

  def getCards(category: Category): List[Card] = {
    val queryString = Seq("key" -> APP_KEY, "token" -> TOKEN, "fields" -> "name,idList,url")
    val cards = WS.url(getCardsUrl).withQueryString(queryString:_*).get().map{ response =>
      response.json.as[List[Card]].filter(_.categoryId == category.id)
    }.recover{
      case error: Throwable =>
        error.printStackTrace()
        Nil
    }
    Await.result(cards, 5000 millis)
  }

  def getCard(cardId: TrelloId): Option[Card] = {
    val queryString = Seq("key" -> APP_KEY, "token" -> TOKEN, "fields" -> "name,idList,url")
    val cardOpt = WS.url(getCardUrl + cardId).withQueryString(queryString:_*).get().map{ response =>
      response.json.asOpt[Card]
    }.recover{
      case error: Throwable =>
        error.printStackTrace()
        None
    }
    Await.result(cardOpt, 5000 millis)
  }
}
