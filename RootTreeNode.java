import java.util.ArrayList;

public class RootTreeNode extends TopTreeNode { // корневой узел, будет хранить строки кода
    ArrayList<TopTreeNode> codeStrings = new ArrayList<>();
    public void addNode(TopTreeNode node){
        codeStrings.add(node);
    }
}
