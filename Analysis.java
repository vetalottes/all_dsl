public class Analysis {
    static String testcode = "a=10;\n" +
            "printed a;\n" +
            "b = (115 / 5*(a-2));\n" + //184
            "printed b;\n" +
            "printed a+b;\n" + //194
            "s = 5**3;\n" +
            "printed s;\n" + //125
            "i=16;\n" +
            "rez = 16;\n" +
            "for i<26;i=i+6 {\n" +
            "    printed i/2;\n" + //8,11
            "};" +
            "j = 2*8-30;\n" + //-14
            "if j<rez {\n" +
            "   printed rez;\n" +
            "};\n" +
            "while j<rez {\n" +
            "    printed j;\n" + //-14,-3, 8
            "    j=j+11;\n" +
            "};\n";

    public static void main(String[] args) {
        Lexer lexer = new Lexer(testcode);
        Parser parser = new Parser(lexer.lexer_analysis());
        // System.out.println("______________________");
        RootTreeNode root = parser.parser_analysis();
        Interpreter interpreter = new Interpreter();
        System.out.println("______________________________");
        for (int i = 0; i < root.codeStrings.size(); i++) {
            interpreter.run(root.codeStrings.get(i));
        }
    }
}
