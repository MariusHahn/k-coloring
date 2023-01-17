package kn.uni.hahn.model

data class Edge(val node1: Node, val node2: Node) {

    fun other(node: Node): Node {
        if (!contains(node)) throw Exception()
        return if (node1 == node) node2 else node1
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Edge
        return node1 == other.node1 && node2 == other.node2
                || node1 == other.node2 && node2 == other.node1
    }

    override fun hashCode(): Int {
        return hashSetOf(node1, node2).hashCode()
    }

    fun contains(node : Node) : Boolean {
        return setOf(node1, node2).contains(node)
    }
}