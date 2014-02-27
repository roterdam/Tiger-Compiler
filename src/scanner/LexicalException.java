package scanner;

class LexicalException extends RuntimeException {
  public LexicalException(int lineNo, char c) {
    super("\nLexical error (line: " + (lineNo + 1) + "): \"" + c + "\" does not begin a valid token.");
  }
}
