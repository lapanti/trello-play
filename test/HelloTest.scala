import java.util.UUID
import org.specs2.mutable._
import models.Category
import repositories.CardRepository
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by Lapanti on 04/08/15.
 */
class HelloTest extends Specification {

  "The get" should {
    "not throw an error" in {
    CardRepository.getCards(Category(UUID.randomUUID(), "", List())).onComplete(println(_))
    }
  }
}
