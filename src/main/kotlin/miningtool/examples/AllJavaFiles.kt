package miningtool.examples

import miningtool.common.toPathContext
import miningtool.parse.antlr.java.Java8Parser
import miningtool.paths.PathMiner
import miningtool.paths.PathRetrievalSettings
import miningtool.paths.storage.VocabularyPathStorage
import java.io.File

//Retrieve paths from Java files, using a generated parser.
fun allJavaFiles() {
    val folder = "./testData/examples/"

    val miner = PathMiner(PathRetrievalSettings(maxHeight = 5, maxWidth = 5))
    val storage = VocabularyPathStorage()

    File(folder).walkTopDown().filter { it.path.endsWith(".java") }.forEach { file ->
        val node = Java8Parser().parse(file.inputStream()) ?: return@forEach
        val paths = miner.retrievePaths(node)

        storage.store(paths.map { toPathContext(it) }, entityId = file.path)
    }

    storage.save("out_examples/allJavaFilesAntlr")
}