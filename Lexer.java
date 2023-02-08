import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    String code;
    int position_lex = 0; // позиция, в которой находится лексер и бродит по коду
    ArrayList<Token> tokenList = new ArrayList<>(); // сами токены кода

    public Lexer(String code) {
        this.code = code;
    }
    public ArrayList<Token> lexer_analysis() {
        while (nextToken()) {}
        for (Token token : tokenList)
            if (!(token.type.name.equals("SPACE") || token.type.name.equals("ENDLINE"))) // если токен не пробел и не перенос строки
                System.out.println("[Токен: " + token.type.name + ", значение: " + token.text + ", позиция в коде: " + token.position + "]");
        return this.tokenList;
    }

    public boolean nextToken() {
        TokenType[] allTokenTypes = TokenType.tokenTypeList; // все значения из списка типа токенов
        if (this.position_lex >= code.length()) {
            return false;
        }
        for (int i = 0; i < allTokenTypes.length; i++) {
            TokenType tokenType = allTokenTypes[i];
            String regex = tokenType.regex;
            Matcher matcher = Pattern.compile(regex).matcher(code);
            if (matcher.find(this.position_lex) && matcher.start() == this.position_lex) {
                String result = this.code.substring(this.position_lex, this.position_lex + matcher.group().length());
                Token token = new Token(tokenType, result, this.position_lex);
                this.position_lex += result.length();
                if (token.type != TokenType.tokenTypeList[3] && token.type != TokenType.tokenTypeList[2] && token.type != TokenType.tokenTypeList[1])
                    tokenList.add(token);
                return true;
            }
        }
        throw new Error("Ошибка на позиции: " + this.position_lex);
    }
}
