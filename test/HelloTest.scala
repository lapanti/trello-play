import org.specs2._

/**
 * Created by Lapanti on 04/08/15.
 */
class HelloTest extends Specification {
  def is = s2"""

 This is my first specification
   it is working                 $ok
   really working!               $ok
                                 """
}
