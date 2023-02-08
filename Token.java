public class Token {
    TokenType type;
    String text;
    int position; // номер позиции в коде

    public Token(TokenType type, String text, int position) {
        this.type = type;
        this.text = text;
        this.position = position;
    }
}
