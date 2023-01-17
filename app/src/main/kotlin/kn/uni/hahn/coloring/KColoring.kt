package kn.uni.hahn.coloring

import kn.uni.hahn.model.Graph
import kn.uni.hahn.model.Node
import java.util.*

class KColoring(private val k: Int) : GraphColoring {

    override fun color(graph: Graph): Graph {
        val stack = Stack<Node>()
        val graphToManipulate = graph.deepCopy()

        while (graphToManipulate.nodes().isNotEmpty()) {
            val nodeToRemove = graphToManipulate.getNodeWithDegree(k)
            graphToManipulate.removeNode(nodeToRemove)
            stack.push(nodeToRemove)
        }
        while (stack.isNotEmpty()) {
            val nodeToColor = stack.pop()
            graph.node(nodeToColor).color = graph.smallestAvailableColor(nodeToColor, Int.MAX_VALUE)
        }
        return graph
    }

}