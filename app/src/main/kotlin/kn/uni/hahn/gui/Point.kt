package kn.uni.hahn.gui

import kn.uni.hahn.model.Node
import java.awt.Color

class Point {
    val x : Int
    val y : Int
    val color: Color

    constructor(x: Int, y: Int, color: Color) {
        this.x = x
        this.y = y
        this.color = color
    }

    constructor(node: Node, scaleFactor: Double, latMin: Double, longMin: Double) {
        this.y = ((node.latitude - latMin) * scaleFactor).toInt()
        this.x = ((node.longitude - longMin) * scaleFactor).toInt()
        color = getColor(node.color)
    }

    private fun getColor(color: Int): Color {
        return when (color) {
            0 -> Color.RED
            1 -> Color.GREEN
            2 -> Color.BLUE
            3 -> Color.YELLOW
            4 -> Color.ORANGE
            5 -> Color.PINK
            6 -> Color.CYAN
            7 -> Color.MAGENTA
            8 -> Color.LIGHT_GRAY
            9 -> Color.DARK_GRAY
            else -> Color.BLACK
        }
    }
}

fun createPoints(nodes: Set<Node>, graphDrawParameter: GraphDrawParameter): List<Point> {
    val (scaleFactor, longMin, latMin, yMax) = graphDrawParameter
    val upsideDown: List<Point> = nodes.map { node -> Point(node, scaleFactor, latMin, longMin) }
    return upsideDown
            .map { point: Point -> Point(point.x + 20, (point.y - yMax) * -1 + 20, point.color) }
}