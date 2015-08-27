import org.specs2.mutable._
import models.{TrelloId, Category}
import play.api.test.WithApplication
import repositories.CardRepository
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by Lapanti on 04/08/15.
 */
class CardTest extends Specification {

  "CardRepository" should {
    "get test card" in new WithApplication {
      CardRepository.getCard(TrelloId("55c06282fd8a9b953139979a")) mustNotEqual None
    }

    "get cards of category" in new WithApplication {
      CardRepository.getCards(Category(TrelloId("55bf519c22205e61e2d97da9"), "", Nil)) mustNotEqual Nil
    }
  }
}
