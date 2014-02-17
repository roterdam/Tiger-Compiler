package parser;

import scanner.TokenTuple;

import java.util.HashMap;
import java.util.Map;

public class Rule 
{
  private static Map<String, TokenTuple> tokenMap = new HashMap<String, TokenTuple>();
  static {
    tokenMap.put(",", new TokenTuple("COMMA", ","));
    tokenMap.put(":", new TokenTuple("COLON", ":"));
    tokenMap.put(";", new TokenTuple("SEMI", ";"));
    tokenMap.put("(", new TokenTuple("LPAREN", "("));
    tokenMap.put(")", new TokenTuple("RPAREN", ")"));
    tokenMap.put("[", new TokenTuple("LBRACK", "["));
    tokenMap.put("]", new TokenTuple("RBRACK", "]"));
    tokenMap.put("{", new TokenTuple("LBRACE", "{"));
    tokenMap.put("}", new TokenTuple("RBRACE", "}"));
    tokenMap.put(".", new TokenTuple("PERIOD", "."));
    tokenMap.put("+", new TokenTuple("PLUS", "+"));
    tokenMap.put("-", new TokenTuple("MINUS", "-"));
    tokenMap.put("*", new TokenTuple("MULT", "*"));
    tokenMap.put("/", new TokenTuple("DIV", "/"));
    tokenMap.put("=", new TokenTuple("EQ", "="));
    tokenMap.put("!=", new TokenTuple("NEQ", "!="));
    tokenMap.put("<", new TokenTuple("LESSER", "<"));
    tokenMap.put(">", new TokenTuple("GREATER", ">"));
    tokenMap.put("<=", new TokenTuple("LESSEREQ", "<="));
    tokenMap.put(">=", new TokenTuple("GREATEREQ", ">="));
    tokenMap.put("&", new TokenTuple("AND", "&"));
    tokenMap.put("|", new TokenTuple("OR", "|"));
    tokenMap.put(":=", new TokenTuple("ASSIGN", ":="));
  }
  private static String[] tokenStrings;
  private static TokenTuple[] tokenBuilder;

  TokenTuple[] tokens;

  public static Rule determineFrom(String rule) {
    tokenStrings = rule.split(" ");
    tokenBuilder = new TokenTuple[tokenStrings.length];
    interpretTokens();
    return new Rule(tokenBuilder);
  }

  public static void interpretTokens() {
    for(int i = 0; i < tokenBuilder.length; i++)
      interpretToken(i);
  }

  private static void interpretToken(int i) {
    if (tokenMap.containsKey(tokenStrings[i]))
      tokenBuilder[i] = tokenMap.get(tokenStrings[i]);
    else if(tokenStrings[i].contains("<"))
      tokenBuilder[i] = new TokenTuple("NONTERM", tokenStrings[i]); // non-terminal
    else if ("0123456789".contains(tokenStrings[i].substring(1)))
      tokenBuilder[i] = new TokenTuple("INTLIT", tokenStrings[i]);
    else
      tokenBuilder[i] = new TokenTuple(tokenStrings[i].toUpperCase(), tokenStrings[i]);
  }

  private Rule(TokenTuple[] tokens) {
    this.tokens = tokens;
  }
	
	public int getLength()
	{
		return tokenBuilder.length;
	}
}