package kn.uni.hahn.model

import kn.uni.hahn.coloring.Algorithms
import kn.uni.hahn.util.Util
import org.junit.Assert
import org.junit.Test

class GraphTest {


    @Test
    fun testNeighborSameReference() {
        val graph = Graph(Util().readFile(Util().resourceFile( "lu980.tsp")))
        val nodes = graph.getNodesOrdered(Graph.NodeOrdering.MOST_EDGES_FIRST)
        Assert.assertTrue(graph.nodes().size == nodes.size)
        for (node in nodes) {
            val neighbors = graph.getNeighbors(node)
            for (neighbor in neighbors) {
                Assert.assertTrue(graph.node(neighbor) === neighbor)
            }
        }
    }

    @Test
    fun testNeighbors() {
        val graph = Graph(Util().readFile(Util().resourceFile( "lu980.tsp")))
        Algorithms.GREEDY_DESCENDING.alg.color(graph)
        Assert.assertTrue(graph.isCompletelyColored())
        Assert.assertTrue(graph.isProperlyColored())
    }
}