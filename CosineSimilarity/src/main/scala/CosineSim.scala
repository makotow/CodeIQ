import java.io.File
import java.nio.charset.Charset
import java.nio.file.{Files,Path}
import scala.collection.JavaConversions
import scala.io.Source

/**
 * Project: CosineSimilarity
 * Package: 
 *
 * User: makoto
 * Date: 7/14/13
 * Time: 1:32 AM
 */
object CosineSim extends App {

  def exec(file: File)= {

    val s = Source.fromFile(file)
    try {
      val loadedData = new scala.collection.mutable.HashMap[String, List[Double]]
      s.getLines().drop(1) foreach { e =>
          // 愚直に書いてみる
          val line = e.split("\t")
          val userid = line(0); val itemid = line(1); val rate = line(2).toDouble

          val l = loadedData.getOrElse(itemid, List())
          val updated = l :+ rate
          loadedData += (itemid -> updated)
         }
      val result = calc(loadedData)
      println (result)
    } finally {
      s.close()
    }
  }

  def calc(data: scala.collection.mutable.HashMap[String, List[Double]]):List[(String, String, Double)] = {

    var result: List[(String, String, Double)] = List()
    val vectorOp = new Vec {}

    def cosinesim(a: List[Double], b: List[Double]): Double = vectorOp.dot(a,b) / (vectorOp.norm(a) * vectorOp.norm(b))

    for(k1 <- data.keys; k2 <- data.keys; if k1 != k2)  {
        result = result :+ (k1 ,k2, cosinesim(data.getOrElse(k1, List()), data.getOrElse(k2, List())))
    }
    result
  }

  // main
  val sim = CosineSim
  val in = readLine("exec?> please enter ")

//  sim.exec(new File("small.rating"))
  sim.exec(new File("large.rating"))
}


trait Vec {
  def dot(a: List[Double], b: List[Double]) = ((a zip b).map {case (x, y) => x * y } :\ 0.0 ) { _ + _ }
  def norm(l: List[Double]) = Math.sqrt((0.0 /: l.map(x => x * x)) {_ + _})
}
