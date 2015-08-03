import scala.io.Source
import scala.util.Random
import scala.collection.immutable.Queue

object Program {
  def main(args: Array[String]): Unit = {
    val filename = "graph.txt"
    val lines = Source.fromFile(filename).getLines().filter(_ != "")
    val vertices = lines.map(parseVertex(_)).toMap

    val states = vertices.keys.map(id => (id, Unexplored)).toMap
    breadthFirstSearch(vertices, Queue[Int](1), states)
  }

  def parseVertex(text: String): (Int, List[Int]) = {
    text.split('\t').map(_.toInt).toList match {
      case id :: edges => (id, edges)
      case _ => throw new Exception("Something is wrong with this one")
    }
  }

  def breadthFirstSearch(graph: Map[Int, List[Int]], destinations: Queue[Int], states: Map[Int, SearchState]): Unit = destinations match {
    case v +: oldDestinations => {
      println(s"exploring $v")

      var newDestinations = oldDestinations
      var newStates = states + (v -> Processed)

      for (u <- graph(v) if states(u) == Unexplored) {
        newDestinations = newDestinations.enqueue(u)
        newStates += (u -> Explored)
      }

      breadthFirstSearch(graph, newDestinations, newStates)
    }
    case _ => println("done")
  }
}

trait SearchState
object Unexplored extends SearchState
object Explored extends SearchState
object Processed extends SearchState

