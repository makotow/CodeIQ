object SieveOfEratos {

  def from(begin: Int): Stream[Int] = Stream.cons(begin, from(begin+1))

  def sieve(xs: Stream[Int]): Stream[Int] = Stream.cons(xs.head, sieve(xs.tail.filter(_% xs.head!=0)))
 
  def main(args: Array[String]) = sieve(from(2)).take(100).foreach { println }

}
