import org.scalatest.FunSuite

/**
 * Project: CosineSimilarity
 * Package: 
 *
 * User: makoto
 * Date: 7/3/13
 * Time: 11:08 PM
 */
import org.scalatest._

class VecTest extends FunSuite {

  test("dot product") {
    val v1 = List(1.0, 3.0, -5.0)
    val v2 = List(4.0, -2.0, -1.0)
    val vec = new Vec{}
    assert(3 === vec.dot(v1 ,v2))
  }

  test("norm") {
    val v1 = List(1.0, 3.0, -5.0)
    val vec = new Vec{}

    assert(Math.sqrt(35) === vec.norm(v1))
  }
}
