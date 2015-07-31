import scala.io.Source
import scala.util.Random
import scala.collection.immutable.Queue

trait SearchState
object Unexplored extends SearchState
object Explored extends SearchState
object Processed extends SearchState

object Program {
  type Graph = Map[Int, Vector[Int]]
  def Graph(elems: (Int, Vector[Int])*) = Map(elems: _*)

  def main(args: Array[String]): Unit = {
    val filename = "graph.txt"
    val lines = Source.fromFile(filename).getLines().filter(_ != "").toVector

    val edges = lines.map(parseEdge(_))
    val graph = edges.foldLeft(Graph())(connectEdge(_, _))

    depthFirstSearch(graph, 1)
  }

  def parseEdge(text: String): (Int, Int) = {
    val ids = text.split(' ').map(_.toInt)
    (ids(0), ids(1))
  }

  def connectEdge(graph: Graph, edge: (Int, Int)): Graph = {
    val (v1, v2) = edge
    
    val v1edges = graph.get(v1) match {
      case Some(list) => v2 +: list
      case None => Vector(v2)
    }

    val v2edges = graph.get(v2) match {
      case Some(list) => list
      case None => Vector[Int]()
    }

    graph + (v1 -> v1edges, v2 -> v2edges)
  }

  def depthFirstSearch(graph: Graph, v: Int): Unit = {
    depthFirstSearch(graph, v, Map.empty)
  }

  def depthFirstSearch(graph: Graph, v: Int, states: Map[Int, SearchState]): Map[Int, SearchState] = {
    println(s"exploring $v")

    var newStates = states + (v -> Explored)

    for (u <- graph(v) if newStates.get(u) == None) {
      newStates = depthFirstSearch(graph, u, newStates)
    }

    newStates + (v -> Processed)
  }
}

