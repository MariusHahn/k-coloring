package kn.uni.hahn.coloring

import kn.uni.hahn.model.Graph

class Greedy(private val nodeOrder: Graph.NodeOrdering) : GraphColoring {

    override fun color(graph: Graph) : Graph {
        for (node in graph.getNodesOrdered(nodeOrder)) {
            node.color = graph.smallestAvailableColor(node, Int.MAX_VALUE)
        }
        return graph
    }

}
