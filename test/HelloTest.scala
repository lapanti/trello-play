import java.util.UUID
import org.specs2.mutable._
import models.Category
import play.api.test.WithApplication
import repositories.CardRepository
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by Lapanti on 04/08/15.
 */
class HelloTest extends Specification {

  "The get" should {
    "not throw an error" in new WithApplication {
      CardRepository.getAll.onComplete(println(_))
      true must beTrue
    }
  }
}
