package models

import play.api.libs.json._

/**
 * Created by Lapanti on 25/08/15.
 */
class TrelloId private(id: String) {
  override def toString = id
}

object TrelloId {
  def apply(id: String): TrelloId = new TrelloId(id)

  implicit val trelloIdWrites = new Writes[TrelloId] {
    def writes(number: TrelloId): JsValue = {
      JsString(number.toString)
    }
  }

  implicit val trelloIdReads = new Reads[TrelloId] {
    def reads(json: JsValue): JsResult[TrelloId] = {
      JsSuccess(new TrelloId(json.as[String]))
    }
  }
}