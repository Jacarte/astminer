package astminer.examples

import astminer.paths.toPathContext
import astminer.parse.antlr.javascript.ECMAParser
import astminer.paths.PathMiner
import astminer.paths.PathRetrievalSettings
import astminer.paths.VocabularyPathStorage
import java.io.File


fun allJsFiles() {
    val folder = "./testData"

    val miner = PathMiner(PathRetrievalSettings(5, 5))
    val storage = VocabularyPathStorage()

    File(folder).walkTopDown().filter { it.path.endsWith(".js") }.forEach { file ->

        try{

            print("Parsing..." + file)
            print("\n")


            val node = ECMAParser().parse(file.inputStream()) ?: return@forEach
            val paths = miner.retrievePaths(node)

            storage.store(paths.map { toPathContext(it) }, entityId = file.path)
        }
        catch (e: Exception){
            print(e.message)
            print("\n")
        }
    }

    storage.save("out_examples/allJSFiles")
}