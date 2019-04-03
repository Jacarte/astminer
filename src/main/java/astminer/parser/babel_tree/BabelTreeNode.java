package astminer.parser.babel_tree;

public class BabelTreeNode {

    public String name;

    public BabelTreeNode[] children;

    public boolean isTerminal;

    public String text;

    public String repr;

    public String getAnyways(){
        if(text == null || text
                .isEmpty())
            return this.repr;
        return this.text;
    }
}
