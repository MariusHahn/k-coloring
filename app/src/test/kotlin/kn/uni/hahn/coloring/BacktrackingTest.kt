package kn.uni.hahn.coloring

import kn.uni.hahn.model.Graph
import kn.uni.hahn.util.Util
import org.junit.Assert
import org.junit.Test
import java.io.File

class BacktrackingTest {
    @Test
    fun backtrackingUruguayTests(){
        backtrackingUruguay(Algorithms.BACKTRACKING_4)
        backtrackingUruguay(Algorithms.BACKTRACKING_4_PARALLEL)
    }

    fun backtrackingUruguay(algorithms: Algorithms) {
        var graph = Graph(Util().readFile(Util().resourceFile( "test40.tsp")))
        Assert.assertFalse(graph.isProperlyColored())
        graph = algorithms.alg.color((graph))
        Assert.assertTrue(graph.isCompletelyColored())
        Assert.assertTrue(graph.isProperlyColored())
    }
}