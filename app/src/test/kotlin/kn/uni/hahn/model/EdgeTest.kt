package kn.uni.hahn.model

import org.junit.Test
import org.junit.Assert

class EdgeTest {
    private val node1 = Node(10.0, 20.0, 0)
    private val node2 = Node(20.0, 300.0, 0)

    @Test
    fun equalsTest() {
        Assert.assertEquals(Edge(node1, node2), Edge(node2, node1))
    }
    @Test
    fun hashCodeTest() {
        Assert.assertEquals(Edge(node1, node2).hashCode(), Edge(node2, node1).hashCode())
    }

    @Test
    fun otherTest() {
        val edge = Edge(node1, node2)
        Assert.assertSame(edge.other(node1), node2)
        Assert.assertSame(edge.other(node2), node1)
    }
}
