package astminer.parse.antlr.javascript

import me.vovak.antlr.parser.ECMAScriptLexer
import me.vovak.antlr.parser.ECMAScriptParser
import astminer.common.Parser
import astminer.parse.antlr.SimpleNode
import astminer.parse.antlr.convertAntlrTree
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import java.io.InputStream

class ECMAParser : Parser<SimpleNode> {
    override fun parse(content: InputStream): SimpleNode? {
        val lexer = ECMAScriptLexer(CharStreams.fromStream(content))
        lexer.removeErrorListeners()
        val tokens = CommonTokenStream(lexer)

        val parser = ECMAScriptParser(tokens)
        parser.removeErrorListeners()
        val context = parser.program()
        return convertAntlrTree(context, ECMAScriptParser.ruleNames)
    }

}