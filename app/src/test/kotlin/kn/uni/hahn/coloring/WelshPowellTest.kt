package kn.uni.hahn.coloring

import kn.uni.hahn.model.Graph
import kn.uni.hahn.util.Util
import org.junit.Assert
import org.junit.Test

class WelshPowellTest {


    @Test
    fun welshPowellUruguay(){
        welshPowellUruguay(Algorithms.WELSH_POWELL_MOST_EDGES_FIRST)
        welshPowellUruguay(Algorithms.WELSH_POWELL_LEAST_EDGES_FIRST)
        welshPowellUruguay(Algorithms.WELSH_POWELL_SHUFFLE_NODES)
    }

    fun welshPowellUruguay(algorithms: Algorithms) {
        val graph = Graph(Util().readFile(Util().resourceFile( "uy734.tsp")))
        Assert.assertFalse(graph.isProperlyColored())
        algorithms.alg.color((graph))
        Assert.assertTrue(graph.isCompletelyColored())
        Assert.assertTrue(graph.isProperlyColored())
    }
}