package astminer.parse.antlr.babel_tree

import astminer.common.Parser
import astminer.parse.antlr.SimpleNode
import astminer.parse.antlr.convertAntlrTree
import astminer.parse.antlr.convertBabelTree
import astminer.parser.babel_tree.BabelTreeNode
import me.vovak.antlr.parser.ECMAScriptLexer
import me.vovak.antlr.parser.ECMAScriptParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.InputStream
import com.google.gson.Gson
import java.io.InputStreamReader


class BabelTree : Parser<SimpleNode> {
    override fun parse(content: InputStream): SimpleNode? {
        val gson = Gson()

        val root = gson.fromJson<BabelTreeNode>(InputStreamReader(content), BabelTreeNode::class.java);

        return convertBabelTree(root)
    }

}