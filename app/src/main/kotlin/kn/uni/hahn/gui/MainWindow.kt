package kn.uni.hahn.gui

import kn.uni.hahn.coloring.Algorithms
import kn.uni.hahn.model.Graph
import kn.uni.hahn.util.Util
import java.awt.GridLayout
import java.awt.TextArea
import java.util.concurrent.CompletableFuture
import javax.swing.JComboBox
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants
import kotlin.system.measureTimeMillis

private const val WINDOW_HIGTH = 900
private const val WINDOW_WIDTH = 1600

class MainWindow : JFrame() {
    private val controlPanel = JPanel()
    private val controlLayout = GridLayout(0, 1)
    private val baseLayout = GridLayout(1, 2)
    private val graphInformation = TextArea()
    private val fileSelect =  JComboBox(Util().getResourceFiles())
    private val algSelect = JComboBox(Algorithms.values())
    private var graphCanavas = JPanel()

    init {
        setSize(WINDOW_WIDTH, WINDOW_HIGTH)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        layout = baseLayout
        add(controlPanel)
        fileSelect.selectedIndex = 0
        controlPanel.layout = controlLayout
        controlPanel.add(fileSelect)
        controlPanel.add(algSelect)
        controlPanel.add(graphInformation)

        add(graphCanavas)
        fileSelect.addActionListener { handleSelection() }
        algSelect.selectedIndex = 0
        fileSelect.selectedIndex = 0
        algSelect.addActionListener{ handleSelection() }
    }

    private fun handleSelection() {
        val file = fileSelect.getItemAt(fileSelect.selectedIndex)
        val graph = Graph(Util().readFile(file))
        CompletableFuture.runAsync{
            val coloredGraph : Graph
            val duration = measureTimeMillis { coloredGraph = algSelect.getItemAt(algSelect.selectedIndex).alg.color(graph)}
            remove(graphCanavas)
            graphCanavas = GraphCanavas(coloredGraph)
            add(graphCanavas)
            graphInformation.text = """
                | Nodes: ${graph.nodes().size}
                | Edges: ${graph.edges().size}
                | Number of Colors used: ${coloredGraph.numberOfUsedColors()} 
                | Is properly colored: ${coloredGraph.isProperlyColored()}
                | It took ${duration/1000.0} Seconds
            """.trimMargin()
            pack()
        }
        graphInformation.text = "Waiting........"
    }
}