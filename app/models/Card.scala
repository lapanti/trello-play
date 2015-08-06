package models

import java.util.UUID
import play.api.libs.json.{Format, Writes, JsPath, Reads}
import play.api.libs.functional.syntax._

/**
 * Created by Lapanti on 04/08/15.
 */
case class Card (
                  id: UUID,
                  story: String,
                  categoryId: UUID,
                  url: String
                  )

object Card {
  val reads: Reads[Card] = (
      (JsPath \ "id").read[UUID] and
      (JsPath \ "name").read[String] and
      (JsPath \ "idList").read[UUID] and
      (JsPath \ "url").read[String]
    )(Card.apply _)

  val writes: Writes[Card] = (
      (JsPath \ "id").write[UUID] and
      (JsPath \ "name").write[String] and
      (JsPath \ "idList").write[UUID] and
      (JsPath \ "url").write[String]
    )(unlift(Card.unapply))

  implicit val cardFormat: Format[Card] = Format(reads, writes)
}