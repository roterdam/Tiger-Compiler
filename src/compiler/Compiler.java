package compiler;

import parser.ParserException;
import scanner.LexicalException;
import utilities.NormalFileScraper;
import parser.Parser;
import scanner.Scanner;

public class Compiler {

  public static Compiler normal(Scanner scanner, Parser parser) {
    return new Compiler(scanner, parser);
  }

  /**
   * The debug compiler generates a list of tokens found in the compiled
   * file in the Compiler message.
   */
  public static Compiler debug(Scanner scanner, Parser parser) {
    return new DebugCompiler(scanner, parser);
  }

  private final Scanner scanner;
  private final Parser parser;
  private boolean noErrorEncountered;
  private boolean parserErrorEncountered;
  String message;

  Compiler(Scanner scanner, Parser parser) {
    this.scanner = scanner;
    this.parser = parser;
  }

  /**
   * Attempts to compile the file found at filename. The compiler will reject
   * any files that have lexical or parsing errors. Once compilation is
   * complete, an output message is available through a call to getMessage(),
   * and the generated parsing tree is available through a call to
   * getParseTreePrintout().
   *
   * @param filename The file to be compiled.
   */
  public void compile(String filename)  {
    noErrorEncountered = true;
    scanner.reset();
    parser.reset();
    init(filename);
    while (scanner.hasMoreTokens() && !parserErrorEncountered)
      compileNextToken();
    addSuccessMessage();
    parser.buildSymbolTable();
  }

  /**
   * @return A message giving information about the last compilation. If any
   * errors were found, they will be found here. In addition, the message
   * will indicate if the compilation was successful or not.
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return A String representation of the parsing tree generated by the
   * compiler.
   */
  public String getParseTreePrintout() {
    return parser.printTree();
  }
  
  public void getSymbolTablePrintout()
  {
	  parser.printSymbolTable();
  }

  private void init(String filename) {
    message = "";
    scanner.scan(getLinesFromFile(filename));
  }

  private String[] getLinesFromFile(String filename) {
    NormalFileScraper scraper = new NormalFileScraper();
    return scraper.read(filename);
  }

  private void compileNextToken() {
    TokenTuple token = scan();
    if (token != null && noErrorEncountered)
      parseToken(token);
  }

  TokenTuple scan() {
    TokenTuple token = null;
    try {
      token = scanner.getNextToken();
    } catch (LexicalException e) {
      handleScannerError(e);
    }
    return token;
  }

  void handleScannerError(LexicalException e) {
    noErrorEncountered = false;
    message += e.getMessage() + "\n";
  }

  private void parseToken(TokenTuple token) {
    try {
      parser.parse(token);
    } catch (ParserException e) {
      handleParserError(e);
    }
  }

  void handleParserError(ParserException e) {
    message += "Parsing error " + scanner.getLineInfo() + " <-- " + e.getMessage();
    noErrorEncountered = false;
    parserErrorEncountered = true;
  }

  void addSuccessMessage() {
    if (noErrorEncountered)
      message += "Tiger-Compiler: Compilation successful!\n";
    else
      message += "Tiger-Compiler: Compilation unsuccessful.\n";
  }
}
