package kn.uni.hahn.coloring

import kn.uni.hahn.model.Graph
/**
 * This interface should be implemented by all graph coloring algorithms.
 */
interface GraphColoring {
    /**
     * This function take a graph and returns is properly colored
     */
    fun color(graph: Graph) : Graph
}