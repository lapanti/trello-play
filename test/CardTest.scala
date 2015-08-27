import org.specs2.mutable._
import models.{TrelloId, Category}
import play.api.test.WithApplication
import repositories.CardRepository
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by Lapanti on 04/08/15.
 */
class CardTest extends Specification {
  val testDoneCardId = TrelloId("55c06282fd8a9b953139979a")
  val testToDoListId = TrelloId("55bf519c22205e61e2d97da9")
  val testDoneListId = TrelloId("55bf51a316321e95156ccf23")

  "CardRepository" should {
    "get test card" in new WithApplication {
      CardRepository.getCard(testDoneCardId) mustNotEqual None
    }

    "get cards of category" in new WithApplication {
      CardRepository.getCards(Category(testToDoListId, "", Nil)) mustNotEqual Nil
    }

    "change category of card" in new WithApplication {
      CardRepository.changeCategory(testDoneCardId, testToDoListId) must beTrue
      val testCard = CardRepository.getCard(testDoneCardId)
      testCard mustNotEqual None
      testCard.get.categoryId mustEqual testToDoListId

      CardRepository.changeCategory(testDoneCardId, testDoneListId) must beTrue
      val testCard2 = CardRepository.getCard(testDoneCardId)
      testCard2 mustNotEqual None
      testCard2.get.categoryId mustEqual testDoneListId
    }
  }
}
