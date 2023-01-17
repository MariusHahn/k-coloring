package kn.uni.hahn.util

import kn.uni.hahn.model.Node
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

class Util{

    fun resourceFolder() = File(File("."), "src/main/resources")

    fun resourceFile(fileName: String) = File(resourceFolder(), fileName)

    fun getResourceFiles() : Array<File> {
        val resourceFolder = resourceFolder().toPath()
        val resourceFiles = Files.list(resourceFolder).map(Path::toFile).collect(Collectors.toList())
        return resourceFiles.toTypedArray()
    }

    fun readFile(file: File) : List<Node>{
        return Files.lines(file.toPath())
                .skip(7)
                .map { line: String -> line.split(" ") }
                .filter{ it.size == 3}
                .map { split -> Pair(split[1].trim().toDouble(), split[2].trim().toDouble()) }
                .distinct()
                .map { Node(it.first, it.second) }
                .collect(Collectors.toList())
    }
}


