package kn.uni.hahn.gui

import kn.uni.hahn.model.Edge
import kn.uni.hahn.model.Graph
import kn.uni.hahn.model.Node
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import javax.swing.JPanel
import kotlin.math.min

class GraphCanavas(val graph: Graph) : JPanel() {
    private val graphDrawParameter: GraphDrawParameter

    init {
        isOpaque = true
        background = Color.WHITE
        graphDrawParameter = getGraphDrawParameter(graph)
    }

    override fun paintComponent(g: Graphics?) {
        super.paintComponent(g)

        g!!.color = Color.BLACK
        graph.edges()
                .map { edge: Edge -> setOf(edge.node1, edge.node2) }
                .map { nodes: Set<Node> -> createPoints(nodes, graphDrawParameter) }
                .forEach { lineEndpoints: List<Point> ->
                    val (p1, p2) = lineEndpoints
                    g.drawLine(p1.x, p1.y, p2.x, p2.y)
                }
        val points = createPoints(graph.nodes(), graphDrawParameter)
        for (point in points) {
            g.color = point.color
            g.fillOval(point.x - 5, point.y - 5, 10, 10)
        }

    }

    override fun getPreferredSize(): Dimension {
        return Dimension(900, 900)
    }
}

data class GraphDrawParameter(val scaleFactor: Double, val longMin: Double, val latMin: Double, val yMax: Int)

fun getGraphDrawParameter(graph: Graph): GraphDrawParameter {
    val latMin: Double = graph.nodes().map(Node::latitude).minOrNull() ?: 0.0
    val longMin: Double = graph.nodes().map(Node::longitude).minOrNull() ?: 0.0
    val latMax: Double = graph.nodes().map(Node::latitude).maxOrNull() ?: 0.0
    val longMax: Double = graph.nodes().map(Node::longitude).maxOrNull() ?: 0.0

    val xRange = latMax - latMin
    val yRange = longMax - longMin
    val xScaleFactor = 900 / xRange
    val yScaleFactor = 900 / yRange
    val scaleFactor = min(xScaleFactor, yScaleFactor) * .9
    val yMax = (graph.nodes()
            .map { node -> Point(node, scaleFactor, latMin, longMin) }
            .map(Point::y).maxOrNull() ?: 0.0).toInt()
    return GraphDrawParameter(scaleFactor, longMin, latMin, yMax)
}