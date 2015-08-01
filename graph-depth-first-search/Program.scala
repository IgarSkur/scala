import scala.io.Source
import scala.collection.mutable.MutableList
import scala.collection.mutable.HashMap
import scala.collection.mutable.Stack

trait SearchState
object Unexplored extends SearchState
object Explored extends SearchState
object Processed extends SearchState

case class SearchResults(
  hierarchy: Map[Int, Int],
  entryTimes: Map[Int, Int],
  exitTimes: Map[Int, Int]
)

object Program {
  type Graph = HashMap[Int, MutableList[Int]]
  def Graph() = 
    new HashMap[Int, MutableList[Int]] {
      override def apply(key: Int) = super.get(key) getOrElse { 
        val list = MutableList[Int]() 
        update(key, list)
        list
      }
    }

  def main(args: Array[String]): Unit = {
    val filename = "graph.txt"
    val lines = Source.fromFile(filename).getLines().filter(_ != "").toVector

    val graph = Graph()
    val edges = lines.map(parseEdge(_)).sortBy(_._1)

    edges.foreach(connectEdge(graph, _))

    val results = depthFirstSearch(graph, 1)
  }

  def parseEdge(text: String): (Int, Int) = {
    val ids = text.split(' ').map(_.toInt)
    (ids(0), ids(1))
  }

  def connectEdge(graph: Graph, edge: (Int, Int)): Unit = {
    val (v1, v2) = edge
    graph(v1) += v2
  }

  def depthFirstSearch(graph: Graph, v: Int): SearchResults = {
    val states = 
      new HashMap[Int, SearchState] {
        override def apply(key: Int) = super.get(key) getOrElse Unexplored
      }

    val destinations = Stack[Int]()

    val hierarchy = HashMap[Int, Int]()
    val entryTimes = HashMap[Int, Int]()
    val exitTimes = HashMap[Int, Int]()
    var time = 0

    states(v) = Explored
    destinations.push(v)

    while (!destinations.isEmpty) {
      val v = destinations.pop()

      states(v) match {
        case Processed => {
          states(v) = Processed
          exitTimes += (v -> time)
          time += 1
        }
        case Explored => {
          println(s"exploring $v")

          states(v) = Processed
          entryTimes += (v -> time)
          time += 1

          destinations.push(v)

          for (u <- graph(v) if states(u) == Unexplored) {
            states(u) = Explored
            destinations.push(u)
            hierarchy += (u -> v)
          }
        }
      }
    }

    SearchResults(hierarchy.toMap, entryTimes.toMap, exitTimes.toMap)
  }
}

