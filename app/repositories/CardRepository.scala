package repositories

import play.api.mvc.Results
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

  private def getParams(params: (String, String)*): Seq[(String, String)] = {
    Seq("key" -> APP_KEY, "token" -> TOKEN) ++ params
  }

  private def getWSRequest(url: String, queryString: Seq[(String, String)]): WSRequest = {
    WS.url(url).withQueryString(queryString:_*)
  }

  private def get[B](url: String, queryString: Seq[(String, String)], f: WSResponse => B, errorCase: B): B = {
    val finalResp = getWSRequest(url, getParams(queryString:_*)).get().map{ response =>
      f(response)
    }.recover{
      case error: Throwable =>
        error.printStackTrace()
        errorCase
    }
    Await.result(finalResp, 5000 millis)
  }

  private def put[B](url: String, queryString: Seq[(String, String)], f: WSResponse => B, errorCase: B): B = {
    val finalResp = getWSRequest(url, getParams(queryString:_*)).put(Results.EmptyContent()).map{ response =>
      f(response)
    }.recover{
      case error: Throwable =>
        error.printStackTrace()
        errorCase
    }
    Await.result(finalResp, 5000 millis)
  }

  def getAll: List[Card] = {
    get(getCardsUrl, Seq("fields" -> "name,idList,url"), _.json.as[List[Card]], Nil)
  }

  def getCards(category: Category): List[Card] = {
    get(getCardsUrl, Seq("fields" -> "name,idList,url"), _.json.as[List[Card]].filter(_.categoryId == category.id), Nil)
  }

  def getCard(cardId: TrelloId): Option[Card] = {
    get(getCardUrl + cardId, Seq("fields" -> "name,idList,url"), _.json.asOpt[Card], None)
  }

  def changeCategory(cardId: TrelloId, categoryId: TrelloId): Boolean = {
    put(getCardUrl + cardId + "/idList", Seq("value" -> categoryId.toString), _.status == 200, false)
  }
}
