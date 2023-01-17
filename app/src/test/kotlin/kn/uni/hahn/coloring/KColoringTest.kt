package kn.uni.hahn.coloring

import kn.uni.hahn.model.Graph
import kn.uni.hahn.model.Node
import kn.uni.hahn.util.Util
import org.junit.Assert
import org.junit.Test
import java.io.File

class KColoringTest {
    @Test
    fun k4ColorUruguayTest(){
        backtrackingUruguay(Algorithms.K4_COLORING)
    }

    fun backtrackingUruguay(algorithms: Algorithms) {
        var graph = Graph(Util().readFile(Util().resourceFile( "test40.tsp")))
        Assert.assertFalse(graph.isProperlyColored())
        graph = algorithms.alg.color((graph))
        Assert.assertTrue(graph.isCompletelyColored())
        Assert.assertTrue(graph.isProperlyColored())
        Assert.assertEquals(graph.nodes().map(Node::color).toSet().size, 4)
    }
}