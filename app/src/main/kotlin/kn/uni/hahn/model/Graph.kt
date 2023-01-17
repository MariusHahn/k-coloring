package kn.uni.hahn.model

import com.github.ricardomatias.Delaunator

class Graph(nodes: List<Node>) {
    private val graph: MutableMap<Node, MutableSet<Edge>>

    init {
        val points = nodes.flatMap { listOf(it.latitude, it.longitude) }.toDoubleArray()
        val delaunator = Delaunator(points)
        val edges = createInnerEdges(delaunator, nodes)
        createHullEdges(delaunator, nodes, edges)
        graph = buildGraph(nodes, edges)
    }

    fun node(node: Node): Node {
        return nodes().filter { it == node }.toList().first()
    }

    fun nodes(): Set<Node> {
        return graph.keys
    }

    fun edges(): Set<Edge> {
        return graph.values.flatten().toSet()
    }

    fun edges(node: Node): Set<Edge> {
        return graph[node] ?: emptySet()
    }

    fun nodeSortedByEgdeCount(): List<Node> {
        return graph.map { Pair(it.key, it.value.size) }.sortedBy { it.second }.map { it.first }.toList()
    }

    fun getNeighborColors(node: Node): Array<Int> {
        return getNeighbors(node).map(Node::color).sorted().toTypedArray()
    }

    fun isProperlyColored(node: Node): Boolean {
        return getNeighbors(node).none { other: Node -> node.color == other.color }
    }

    fun getNeighbors(node: Node): List<Node> {
        return edges(node).map { edge: Edge -> edge.other(node) }
    }

    fun isProperlyColored(): Boolean {
        return isCompletelyColored() && nodes().map { node -> isProperlyColored(node) }.none { a -> !a }
    }

    fun resetNodeColors() {
        nodes().forEach { node -> node.color = -1 }
    }

    fun isCompletelyColored(): Boolean {
        return nodes().map(Node::isColored).none { colored -> !colored }
    }

    fun smallestAvailableColor(node: Node, maxColors: Int): Int {
        val neighborColors = getNeighborColors(node)
        for (color in 0..maxColors) {
            if (color !in neighborColors) {
                return color
            }
        }
        return -1
    }

    fun removeNode(node: Node) {
        graph.remove(node)
        for (edges: MutableSet<Edge> in graph.values) {
            edges.removeIf { edge: Edge -> edge.contains(node) }
        }
    }

    fun getNodesOrdered(nodeOrder: NodeOrdering): Array<Node> {
        return when (nodeOrder) {
            NodeOrdering.LEAST_EDGES_FIRST -> nodeSortedByEgdeCount().toTypedArray()
            NodeOrdering.SHUFFLE -> nodes().toList().shuffled().toTypedArray()
            NodeOrdering.MOST_EDGES_FIRST -> nodeSortedByEgdeCount().reversed().toTypedArray()
        }
    }

    fun numberOfUsedColors() = nodes().map(Node::color).distinct().size

    tailrec fun getNodeWithDegree(degree: Int): Node {
        for (index in 1..degree) {
            for ((node: Node, edges: Set<Edge>) in graph) {
                if (edges.size == degree - index) {
                    return node
                }
            }
        }
        return getNodeWithDegree(degree+1)
    }

    enum class NodeOrdering {
        SHUFFLE,
        MOST_EDGES_FIRST,
        LEAST_EDGES_FIRST,
    }

    fun deepCopy(): Graph {
        val newNodes = nodes().map { it.copy() }
        return Graph(newNodes)
    }

    private fun buildGraph(nodes: List<Node>, edges: MutableSet<Edge>): MutableMap<Node, MutableSet<Edge>> {
        val mutGraph = mutableMapOf<Node, MutableSet<Edge>>()
        nodes.forEach { node: Node -> mutGraph[node] = mutableSetOf() }
        edges.forEach { edge: Edge ->
            mutGraph[edge.node1]?.add(edge)
            mutGraph[edge.node2]?.add(edge)
        }
        return mutGraph
    }

    private fun createInnerEdges(delaunator: Delaunator, nodes: List<Node>): MutableSet<Edge> {
        val triangles = delaunator.triangles
        val edges = delaunator.halfedges
                .mapIndexed { from, to -> Pair(from, to) }
                .filter { pair: Pair<Int, Int> -> pair.second != -1 }
                .map { pair -> Pair(triangles[pair.first], triangles[pair.second]) }
                .map { pair -> Edge(nodes[pair.first], nodes[pair.second]) }
                .toMutableSet()
        return edges
    }

    private fun createHullEdges(delaunator: Delaunator, nodes: List<Node>, edges: MutableSet<Edge>) {
        val hull = delaunator.hull
        val hullIter = hull.iterator()
        var previous = nodes[hullIter.next()]
        while (hullIter.hasNext()) {
            val node = nodes[hullIter.next()]
            edges.add(Edge(previous, node))
            previous = node
        }
        edges.add(Edge(nodes[hull.first()], nodes[hull.last()]))
    }

    override fun toString(): String {
        var s = ""
        graph.forEach {
            s += it.toString() + "\n"
        }
        return s
    }
}