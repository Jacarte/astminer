package astminer.parse.antlr

import astminer.common.Node
import astminer.parser.babel_tree.BabelTreeNode
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.TerminalNode

fun convertAntlrTree(tree: ParserRuleContext, ruleNames: Array<String>): SimpleNode {
    return simplifyTree(convertRuleContext(tree, ruleNames, null))
}

fun convertBabelTree(tree: BabelTreeNode): SimpleNode{
    return convertBabelContext(tree, null);
}

private fun convertBabelContext(tree: BabelTreeNode, parent: Node?): SimpleNode{
    val typeLabel = tree.name

    val currentNode = SimpleNode(typeLabel, parent, null)

    val children: MutableList<Node> = ArrayList()

    tree.children.forEach {
        if(it.isTerminal){
            children.add(SimpleNode("Terminal", currentNode,  it.anyways))

            return@forEach
        }

        children.add(convertBabelContext(it, currentNode))
    }

    currentNode.setChildren(children)

    return currentNode
}

private fun convertRuleContext(ruleContext: ParserRuleContext, ruleNames: Array<String>, parent: Node?): SimpleNode {
    val typeLabel = ruleNames[ruleContext.ruleIndex]
    val currentNode = SimpleNode(typeLabel, parent, null)
    val children: MutableList<Node> = ArrayList()

    ruleContext.children.forEach {
        if (it is TerminalNode) {
            children.add(convertTerminal(it, currentNode))
            return@forEach
        }
        if (it is ErrorNode) {
            children.add(convertErrorNode(it, currentNode))
            return@forEach
        }
        children.add(convertRuleContext(it as ParserRuleContext, ruleNames, currentNode))
    }
    currentNode.setChildren(children)

    return currentNode
}

private fun convertTerminal(terminalNode: TerminalNode, parent: Node?): SimpleNode {
    return SimpleNode("Terminal", parent, terminalNode.symbol.text)
}

private fun convertErrorNode(errorNode: ErrorNode, parent: Node?): SimpleNode {
    return SimpleNode("Error", parent, errorNode.text)
}

private fun simplifyTree(tree: SimpleNode): SimpleNode {
    return if (tree.getChildren().size == 1) {
        simplifyTree(tree.getChildren().first() as SimpleNode)
    } else {
        tree.setChildren(tree.getChildren().map { simplifyTree(it as SimpleNode) })
        tree
    }
}

