package kn.uni.hahn.coloring

import kn.uni.hahn.model.Graph

class WelshPowell(private val nodeOrdering: Graph.NodeOrdering) : GraphColoring {

    override fun color(graph: Graph) : Graph {
        val nodes = graph.getNodesOrdered(nodeOrdering).toMutableList()
        do {
            val node = nodes.first()
            node.color = graph.smallestAvailableColor(node, Int.MAX_VALUE)
            val neighbors = graph.getNeighbors(node)
            val notNeighbors = nodes.filter { !neighbors.contains(it) }.filter { !it.isColored() }
            notNeighbors.forEach { it.color = node.color }
            nodes.removeIf{node -> node.isColored() && graph.isProperlyColored(node)}

        } while (nodes.isNotEmpty())

        return graph
    }


}