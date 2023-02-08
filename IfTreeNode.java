import java.util.ArrayList;

public class IfTreeNode extends TopTreeNode {
    Token operator;
    TopTreeNode leftNode;
    TopTreeNode rightNode;
    public ArrayList<TopTreeNode> operations = new ArrayList<>();

    public IfTreeNode(Token operator, TopTreeNode leftNode, TopTreeNode rightNode) {
        this.operator = operator;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }
    public void addOperations(TopTreeNode op){
        operations.add(op);
    }
}
