public class TokenType {
    String name; // название типа токена
    String regex; // регулярное выражение

    public TokenType(String name, String regex) {
        this.name = name;
        this.regex = regex;
    }
    public static TokenType[] tokenTypeList={
            new TokenType("NUM", "^0|[1-9][0-9]*"),
            new TokenType("SPACE", "\\ "),
            new TokenType("ENDLINE", "\\n"),
            new TokenType("KARETKA", "\\r"),
            new TokenType("ASSIGN", "\\="),
            new TokenType("PLUS", "\\+"),
            new TokenType("DEGREE", "(\\*\\*)"),
            new TokenType("MINUS", "\\-"),
            new TokenType("MULT", "\\*"),
            new TokenType("DIV", "\\/"),
            new TokenType("LESS", "\\<"),
            new TokenType("MORE", "\\>"),
            new TokenType("EQUAL", "(==)"),
            new TokenType("PRINTED", "(printed)"),
            new TokenType("FOR", "(for)"),
            new TokenType("IF", "(if)"),
            new TokenType("WHILE","(while)"),
            new TokenType("END", "\\;"),
            new TokenType("L_BRA", "\\("),
            new TokenType("R_BRA", "\\)"),
            new TokenType("L_CURLY_BRA", "\\{"),
            new TokenType("R_CURLY_BRA", "\\}"),
            new TokenType("VAR", "[a-z][a-z]*"),
    };
}
