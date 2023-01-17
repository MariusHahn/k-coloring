package kn.uni.hahn.coloring

import kn.uni.hahn.model.Graph
import kn.uni.hahn.model.Node
import java.util.stream.IntStream
import java.util.stream.Stream

class Backtracking(private val numberOfColors: Int, private val parallel: Boolean) : GraphColoring {
    private val uncolor = -1

    override fun color(graph: Graph) : Graph {
        var stream: Stream<Triple<Graph, List<Node>, Int>> = IntStream.range(0, graph.nodes().size)
                .mapToObj { i ->
                    val newGraph = graph.deepCopy()
                    Triple(newGraph, newGraph.nodeSortedByEgdeCount().reversed(), i)
                }
        if (parallel) {
            stream = stream.parallel()
        }
        return stream
                .map { Pair(colorNode(it.first, it.second, it.third), it.first) }
                .filter { it.first }
                .filter{it.second.isProperlyColored()}
                .findAny()
                .map { it.second }
                .get()
    }

    private fun colorNode(graph: Graph, nodes: List<Node>, index: Int): Boolean {
        if (graph.isProperlyColored()) {
            return true
        }
        val node = nodes[index % nodes.size]
        for (newColor in 0 until numberOfColors) {
            node.color = newColor

            if (graph.isProperlyColored(node)) {
                if (colorNode(graph, nodes, index + 1)) {
                    return true
                }
                node.color = uncolor
            }
        }
        return false
    }
}