package models

import play.api.libs.json.{Format, Writes, JsPath, Reads}
import play.api.libs.functional.syntax._

/**
 * Created by Lapanti on 04/08/15.
 */
case class Card(id: TrelloId, story: String, categoryId: TrelloId, url: String)

object Card {
  val reads: Reads[Card] = (
      (JsPath \ "id").read[TrelloId] and
      (JsPath \ "name").read[String] and
      (JsPath \ "idList").read[TrelloId] and
      (JsPath \ "url").read[String]
    )(Card.apply _)

  val writes: Writes[Card] = (
      (JsPath \ "id").write[TrelloId] and
      (JsPath \ "name").write[String] and
      (JsPath \ "idList").write[TrelloId] and
      (JsPath \ "url").write[String]
    )(unlift(Card.unapply))

  implicit val cardFormat: Format[Card] = Format(reads, writes)
}