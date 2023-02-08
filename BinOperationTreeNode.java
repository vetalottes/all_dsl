public class BinOperationTreeNode extends TopTreeNode { // [операнд][знак операции][операнд]
    Token operator;
    TopTreeNode leftNode;
    TopTreeNode rightNode;

    public BinOperationTreeNode(Token operator, TopTreeNode leftNode, TopTreeNode rightNode) {
        super();
        this.operator = operator;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }
}
