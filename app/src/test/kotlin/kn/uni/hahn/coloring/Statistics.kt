package kn.uni.hahn.coloring

import kn.uni.hahn.gui.GraphCanavas
import kn.uni.hahn.model.Graph
import kn.uni.hahn.util.Util
import org.junit.Test
import java.awt.Dimension
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import javax.imageio.ImageIO
import javax.swing.JPanel
import kotlin.system.measureTimeMillis
import kotlin.collections.mutableListOf as mutableListOf

class Statistics {



    @Test
    fun backtrackingParallelStatistics() {
        val statisticFolder = File("./backTrackingParallelStatistics")
        if (statisticFolder.exists()) {
            statisticFolder.deleteRecursively()
        }
        statisticFolder.mkdir()
        val resourceFiles = "test5.tsp, wi29.tsp, dj38.txt, test40.tsp, uy100.tsp, qa194.tsp, lu980.tsp, uy734.tsp, rw1621.tsp, zi929.tsp"
                .split(",")
                .map( String::trim )
                .map { fileName -> Util().resourceFile(fileName) }
        val results = File(statisticFolder, "backtrack.csv")
        if (results.exists()) results.delete()
        results.createNewFile()
        results.appendText("filename, algorithm, node count, edge count, used colors, duration in ms, properly colored \n")
        for (it in resourceFiles) {
            val runAsync = CompletableFuture
                    .runAsync { doStatistic(it, Algorithms.BACKTRACKING_4_PARALLEL, results, statisticFolder) }
            try {
                runAsync.orTimeout( 2, TimeUnit.HOURS).get()
            } catch (e: Exception) {
                println("Did not finish ${it.nameWithoutExtension}")
            }
            println("Calculated graph successfully ${it.nameWithoutExtension}")
        }
    }

    @Test
    fun backtrackingParallel40Nodes() {
        val statisticFolder = File("./backTrackingParallelStatistics")
        val resourceFile = Util().resourceFile("test40.tsp")
        val results = File(statisticFolder, "backtrack.csv")
        for(i in 0..100) {
            doStatistic(resourceFile, Algorithms.BACKTRACKING_4_PARALLEL, results, statisticFolder)
        }
    }

    @Test
    fun backtrackingStatistics() {
        val statisticFolder = File("./backTrackingStatistics")
        if (statisticFolder.exists()) {
            statisticFolder.deleteRecursively()
        }
        statisticFolder.mkdir()
        val resourceFiles = "test5.tsp, wi29.tsp, dj38.txt, test40.tsp, uy100.tsp, qa194.tsp, lu980.tsp, uy734.tsp, rw1621.tsp, zi929.tsp"
                .split(",")
                .map( String::trim )
                .map { fileName -> Util().resourceFile(fileName) }
        val results = File(statisticFolder, "backtrack.csv")
        if (results.exists()) results.delete()
        results.createNewFile()
        results.appendText("filename, algorithm, node count, edge count, used colors, duration in ms, properly colored \n")
        resourceFiles
                .parallelStream()
                .forEach { doStatistic(it, Algorithms.BACKTRACKING_4, results, statisticFolder) }

    }

    @Test
    fun approximationStatistics() {
        val statisticFolder = File("./approximationStatistics")
        if (statisticFolder.exists()) {
            statisticFolder.deleteRecursively()
        }
        statisticFolder.mkdir()
        val toList = Algorithms.values().filter { algorithms -> algorithms.alg::class != Backtracking::class }.toList()
        runStatistics(toList, statisticFolder, "./bla.csv")
    }

    private fun runStatistics(algorithms: List<Algorithms>, statisticFolder: File, resultFileName: String) {
        val results = File(statisticFolder, resultFileName)
        if (results.exists()) results.delete()
        results.createNewFile()
        results.appendText("filename, algorithm, node count, edge count, used colors, duration in ms, properly colored \n")
        val functionCalls = mutableListOf<Pair<File, Algorithms>>()
        for (algorithm in algorithms) {
            for (resourceFile in Util().getResourceFiles()) {
                functionCalls.add(Pair(resourceFile, algorithm))
            }
        }
        functionCalls.parallelStream().forEach {
            try {
                doStatistic(it.first, it.second, results, statisticFolder)
            } catch (e: Exception) {
                println("Failed to run ${it.second.name} on ${it.first}")
                e.printStackTrace()
            }
        }
    }

    private fun doStatistic(resourceFile: File, algorithm: Algorithms, results: File, statisticFolder: File) {
        println("Start to with ${algorithm.name} on ${resourceFile.nameWithoutExtension}")
        val util = Util()
        var graph = getGraph(util, resourceFile)
        val newGraph: Graph
        val duration = measureTimeMillis { newGraph = algorithm.alg.color(graph) }
        (@Synchronized { results.appendText("${createInfromationLine(algorithm, newGraph, duration, resourceFile.name)} \n") })()
        val resultGraphFolder = File(statisticFolder, resourceFile.nameWithoutExtension)
        if (!resultGraphFolder.exists()) {
            resultGraphFolder.mkdir()
        }
        val image: BufferedImage = createImage(GraphCanavas(newGraph))
        val output = File(resultGraphFolder, "${algorithm.name}_${resourceFile.nameWithoutExtension}.jpg")
        if (output.exists()) {
            output.delete()
        }
        saveImage(image, output)
    }

    @Synchronized
    private fun getGraph(util: Util, resourceFile: File): Graph {
        return Graph(util.readFile(resourceFile))
    }

    private fun createInfromationLine(algorithm: Algorithms, graph: Graph, duration: Long, fileName: String): String {
        return "$fileName, ${algorithm.name}, ${graph.nodes().size}, ${graph.edges().size}, ${graph.numberOfUsedColors()}, $duration, ${graph.isProperlyColored()}"
    }

    @Synchronized
    private fun saveImage(image: BufferedImage, output: File) {
        try {
            ImageIO.write(image, "jpg", output)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun createImage(panel: JPanel): BufferedImage {
        panel.size = Dimension(900, 900)
        val w = panel.width
        val h = panel.height
        val bi = BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)
        val g = bi.createGraphics()
        panel.print(g)
        g.dispose()
        return bi
    }


}