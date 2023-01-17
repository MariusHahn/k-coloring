package kn.uni.hahn.coloring

import kn.uni.hahn.model.Graph
import kn.uni.hahn.util.Util
import org.junit.Assert
import org.junit.Test

class GreedyTest {

    @Test
    fun greedyUruguayTests(){
        greedyUruguay(Algorithms.GREEDY_SHUFFLE)
        greedyUruguay(Algorithms.GREEDY_ASCENDING)
        greedyUruguay(Algorithms.GREEDY_DESCENDING)
    }
    fun greedyUruguay(algorithms: Algorithms) {
        val graph = Graph(Util().readFile(Util().resourceFile( "uy734.tsp")))
        Assert.assertFalse(graph.isProperlyColored())
        algorithms.alg.color((graph))
        Assert.assertTrue(graph.isCompletelyColored())
        Assert.assertTrue(graph.isProperlyColored())
    }

    @Test
    fun greedyColoringTest() {
        val testFiles = Util().getResourceFiles()
        for (testFile in testFiles) {
            val graph = Graph(Util().readFile(testFile))
            Greedy(Graph.NodeOrdering.MOST_EDGES_FIRST).color(graph)
            if (!graph.isProperlyColored()) {
                println(testFile)
            }
        }
    }

    @Test
    fun greedyColoringTestLuxembourg() {
            val graph = Graph(Util().readFile(Util().resourceFile( "lu980.tsp")))
            Greedy(Graph.NodeOrdering.MOST_EDGES_FIRST).color(graph)
            if (!graph.isProperlyColored()) {
                println(graph)
        }
    }
}
