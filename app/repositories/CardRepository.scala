package repositories

import play.api.mvc.Results
import utils.Global._
import models.{TrelloId, Category, Card}
import play.api.libs.ws._
import play.api.Logger
import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import play.api.Play.current

/**
 * Created by Lapanti on 04/08/15.
 */
object CardRepository extends TrelloRepository {
  val logger = Logger(this.getClass)
  private val multipleCardsUrl = BASE_URL + "/boards/" + BOARD_ID + "/cards"
  private val singleCardUrl = BASE_URL + "/cards/"

  private def httpRequest[B](url: String, queryString: Seq[(String, String)], requestType: WSRequest => Future[WSResponse], handleResponse: WSResponse => B, errorCase: B): B = {
    val finalResp = requestType(WS.url(url).withQueryString(queryString ++ Seq("key" -> APP_KEY, "token" -> TOKEN):_*)).map{ response =>
      handleResponse(response)
    }.recover{
      case error: Throwable =>
        error.printStackTrace()
        errorCase
    }
    Await.result(finalResp, 5000 millis)
  }

  private def get[B](url: String, queryString: Seq[(String, String)], handleResponse: WSResponse => B, errorCase: B): B = {
    httpRequest(url, queryString, _.get(), handleResponse, errorCase)
  }

  private def put[B](url: String, queryString: Seq[(String, String)], handleResponse: WSResponse => B, errorCase: B): B = {
    httpRequest(url, queryString, _.put(Results.EmptyContent()), handleResponse, errorCase)
  }

  def getAll: List[Card] = {
    get(multipleCardsUrl, Seq("fields" -> "name,idList,url"), _.json.as[List[Card]], Nil)
  }

  def getCards(category: Category): List[Card] = {
    get(multipleCardsUrl, Seq("fields" -> "name,idList,url"), _.json.as[List[Card]].filter(_.categoryId == category.id), Nil)
  }

  def getCard(cardId: TrelloId): Option[Card] = {
    get(singleCardUrl + cardId, Seq("fields" -> "name,idList,url"), _.json.asOpt[Card], None)
  }

  def changeCategory(cardId: TrelloId, categoryId: TrelloId): Boolean = {
    put(singleCardUrl + cardId + "/idList", Seq("value" -> categoryId.toString), _.status == 200, false)
  }
}
