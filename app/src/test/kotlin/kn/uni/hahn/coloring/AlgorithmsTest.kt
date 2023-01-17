package kn.uni.hahn.coloring

import kn.uni.hahn.model.Graph
import kn.uni.hahn.util.Util
import org.junit.Assert
import org.junit.Test
import java.io.File

class AlgorithmsTest {
    
    @Test
    fun testAlgorithmsWithUruguay() {
        for (algorithms in Algorithms.values().filter { algorithms -> algorithms.alg::class != Backtracking::class }) {
            testAlg(algorithms.alg, Util().resourceFile( "uy734.tsp" ))
        }
    }    @Test
    fun testAlgorithmsWithTest5() {
        for (algorithms in Algorithms.values()) {
            testAlg(algorithms.alg, Util().resourceFile("test5.tsp" ))
        }
    }
    
    private fun testAlg(algorithms: GraphColoring, file: File) {
        var graph = Graph(Util().readFile(file))
        Assert.assertFalse(graph.isProperlyColored())
        Assert.assertFalse(graph.isCompletelyColored())
        graph = algorithms.color(graph)
        Assert.assertTrue(graph.isProperlyColored())
        Assert.assertTrue(graph.isCompletelyColored())
    }
}