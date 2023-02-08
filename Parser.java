import java.util.ArrayList;

public class Parser {
    ArrayList<Token> tokens; // цепочка токенов из лексера
    int pos = 0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public Token retCurrent(String[] need) { // функция по текущей позиции возвращает токен с определенным типом expected из списка
        if (pos < tokens.size()) {
            Token curToken = tokens.get(pos); // извлекаем токен по индексу = текущей позиции
            // ищем необходимый тип токена
            for (String tokenTypeName : need)
                if (tokenTypeName.equals(curToken.type.name)) { // если токен содержит тот тип, который передали
                    pos++;
                    return curToken;
                }
        }
        return null;
    }

    public void require(String[] expected) {
        Token token = retCurrent(expected);
        if (token == null) {
            throw new Error("На позииции " + pos + " ожидается токен " + expected[0]);
        }
    }

    public RootTreeNode parser_analysis() { // функция, с которой начинается парсинг кода
        RootTreeNode rootTree = new RootTreeNode();
        while (pos < tokens.size()) {
            TopTreeNode codeString = parserString();
            //System.out.println(codeString);
            require(new String[]{"END"});
            rootTree.addNode(codeString);
        }
        return rootTree;
    }

    public TopTreeNode parserString(){
        if (tokens.get(pos).type.name.equals("VAR")) {
            TopTreeNode varNode = parserVarNumber();
            Token assign = retCurrent(new String[]{"ASSIGN"});
            if (assign != null) {
                TopTreeNode rightVal = parserFormula();
                //System.out.println("    " + varNode + " Token:ASSIGH" + " " + rightVal);
                return new BinOperationTreeNode(assign, varNode, rightVal);
            }
            throw new Error("После переменной ожидается оператор присвоения на позиции " + this.pos);
        }
        else if (tokens.get(pos).type.name.equals("PRINTED")) {
            pos++;
            return new UnarOperationTreeNode(tokens.get(pos-1), this.parserFormula());
        }
        else if (tokens.get(pos).type.name.equals("WHILE")) {
            pos++;
            return  parserWhile();
        }
        else if (tokens.get(pos).type.name.equals("FOR")) {
            pos++;
            return parserFor();
        }
        else if (tokens.get(pos).type.name.equals("IF")) {
            pos++;
            return parserIf();
        }
        throw new Error("Ожидалось действие или переменная на позиции: " + pos);
    }

    public TopTreeNode parserVarNumber() { // простейший случай формулы
        if (tokens.get(pos).type.name.equals("NUM")){
            pos++;
            return new NumberTreeNode(tokens.get(pos-1)); // тк при парсинге токен преобразуем в ноду
        }
        if (tokens.get(pos).type.name.equals("VAR")){
            pos++;
            return new VarTreeNode(tokens.get(pos-1));
        }

        throw new Error("Ожидалась переменная или число на позиции " + pos);
    }

    public TopTreeNode parserParentheses() { // для парсинга скобок
        if (tokens.get(pos).type.name.equals("L_BRA")) { // если левая открывающаяся скобка
            pos++;
            TopTreeNode node = parserFormula(); // рекурсивно распарсится формула
            require(new String[]{"R_BRA"});
            return node;
        }
        else // если левой скобки нет, то значит, там какая-то переменная\число
            return parserVarNumber();
    }

    public TopTreeNode parserFormula(){
        TopTreeNode leftVal = parserMultDiv();
        Token operator = retCurrent(new String[]{"PLUS","MINUS","DEGREE"});
        while (operator != null){
            TopTreeNode rightVal = parserMultDiv();
            leftVal = new BinOperationTreeNode(operator, leftVal, rightVal); //рекурсивно один узел под другой становится
            operator = retCurrent(new String[]{"PLUS","MINUS", "DEGREE"});
        }
        return leftVal;
    }

    public TopTreeNode getOperations() {
        TopTreeNode codeStringNode = parserString();
        require(new String[]{"END"});
        return codeStringNode;
    }

    public TopTreeNode parserMultDiv() {
        TopTreeNode leftNode = parserParentheses();
        Token operator = retCurrent(new String[]{"MULT","DIV"});
        while (operator != null) {
            TopTreeNode rightVal = parserParentheses();
            leftNode = new BinOperationTreeNode(operator, leftNode, rightVal);
            operator = retCurrent(new String[]{"MULT","DIV"});
        }
        return leftNode;
    }

    public TopTreeNode parserFor() {
        TopTreeNode leftVal = parserFormula();
        Token operator = retCurrent(new String[]{"LESS", "MORE", "EQUAL"});
        TopTreeNode rightVal = parserFormula();
        require(new String[]{"END"});
        TopTreeNode varNode = parserVarNumber();
        Token assign = retCurrent(new String[]{"ASSIGN"});
        TopTreeNode rightActVal = parserFormula();
        BinOperationTreeNode action = new BinOperationTreeNode(assign, varNode, rightActVal);
        if (assign == null)
            throw new Error("После переменной ожидается '=' на позиции:" + pos);
        ForTreeNode forNode = new ForTreeNode(operator, leftVal, rightVal, action);
        require(new String[]{"L_CURLY_BRA"});
        while(!tokens.get(pos).type.name.equals("R_CURLY_BRA")) {
            forNode.addOperations(getOperations());
            if (pos == tokens.size())
                throw new Error("Ошибка, ожидалось '}'");
        }
        pos++;
        return forNode;
    }

    public TopTreeNode parserWhile() {
        TopTreeNode leftVal = parserFormula();
        Token operator = retCurrent(new String[]{"LESS", "MORE", "EQUAL"});
        TopTreeNode rightVal = parserFormula();
        WhileTreeNode whileNode = new WhileTreeNode(operator, leftVal, rightVal);
        require(new String[]{"L_CURLY_BRA"});
        while (!tokens.get(pos).type.name.equals("R_CURLY_BRA")) {
            whileNode.addOperations(getOperations());
            if (pos == tokens.size())
                throw new Error("Ожидалось '}' на позиции" + pos);
        }
        pos++;
        return whileNode;
    }

    TopTreeNode parserIf() {
        TopTreeNode leftVal = parserFormula();
        Token operator = retCurrent(new String[]{"LESS", "MORE", "EQUAL"});
        TopTreeNode rightVal = parserFormula();
        IfTreeNode ifNode = new IfTreeNode(operator, leftVal, rightVal);
        require(new String[]{"L_CURLY_BRA"});
        while (!tokens.get(pos).type.name.equals("R_CURLY_BRA")) {
            ifNode.addOperations(getOperations());
            if (pos == tokens.size())
                throw new Error("Ожидалось '}' на позиции" + pos);
        }
        pos++;
        return ifNode;
    }
}
