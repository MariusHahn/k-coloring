package kn.uni.hahn.model

import org.junit.Assert
import org.junit.Test

class NodeTest {
    val a = Node(1.0, 5.0, 0)
    val b = Node(1.0, 5.0, 1)
    val c = Node(5.0, 1.0, 0)

    @Test
    fun testEquals() {
        Assert.assertEquals(a, a)
        Assert.assertEquals(a, b)
        Assert.assertNotEquals(a, c)
        Assert.assertFalse(a === b)
        Assert.assertTrue(a == b)
        Assert.assertTrue(a !== b)
    }

    @Test
    fun testHashCode() {
        Assert.assertEquals(a.hashCode(), a.hashCode())
        Assert.assertEquals(a.hashCode(), b.hashCode())
        Assert.assertNotEquals(a.hashCode(), c.hashCode())
    }
}