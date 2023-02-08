import java.util.ArrayList;

public class ForTreeNode extends TopTreeNode {
    Token operator;
    TopTreeNode leftNode;
    TopTreeNode rightNode;
    TopTreeNode action;
    public ArrayList<TopTreeNode> operations = new ArrayList<>();

    public ForTreeNode(Token operator, TopTreeNode leftNode, TopTreeNode rightNode, TopTreeNode action) {
        this.operator = operator;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.action = action;
    }
    public void addOperations(TopTreeNode op){
        operations.add(op);
    }
}
