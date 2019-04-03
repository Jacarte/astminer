package astminer.examples

import astminer.parse.antlr.javascript.ECMAParser
import astminer.paths.PathMiner
import astminer.paths.PathRetrievalSettings
import astminer.paths.VocabularyPathStorage
import astminer.paths.toPathContext
import java.io.File


fun groupByRepo() {
    val folder = "/Users/javiercabrera/Documents/Develop/CodeAspirator/clones"

    File(folder).list().forEach { f ->


        val repoFolder = "$folder/$f"
        val exclude = arrayOf("bower_components", "node_modules")

        if(File(repoFolder).isDirectory) {

            print("Analysing...$repoFolder\n")

            val miner = PathMiner(PathRetrievalSettings(7, 4))
            val storage = VocabularyPathStorage()
            var counter = 0

            File(repoFolder).walkTopDown().filter { it.path.endsWith(".js") }.forEach { file ->

                if(!file.isDirectory && !exclude.any { s -> file.path.contains(s) }){
                    try {

                        print("Parsing $file\n")


                        val node = ECMAParser().parse(file.inputStream()) ?: return@forEach
                        val paths = miner.retrievePaths(node)

                        storage.store(paths.map { toPathContext(it) }, entityId = file.path)
                        counter++
                    } catch (e: Exception) {

                        print("Parsing error $file: ${e.message}\n")
                    }
                }
            }

            print("$counter files\n")
            storage.save("out_examples/repoResults/$f")
        }

    }
}