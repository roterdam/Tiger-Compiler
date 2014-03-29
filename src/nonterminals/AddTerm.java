package nonterminals;

import parser.ParserRule;
import symboltable.Type;

public class AddTerm extends ParserRule {
  private final MultTerm multTerm = new MultTerm();
  private final AddTerm2 addTerm2 = new AddTerm2();

  @Override
  public void parse() {
    storeLineNumber();
    matchNonTerminal(multTerm);
    matchNonTerminal(addTerm2);
  }

  @Override
  public String getLabel() {
    return "<add-term>";
  }

  @Override
  public Type getType() {
    return decideType(multTerm, addTerm2);
  }

  @Override
  protected String generateCode() {
    if (addTerm2.isExpanded()) {
      String op = addTerm2.getOp();
      String a = multTerm.generateCode();
      String b = addTerm2.generateCode();
      String temp = newTemp();
      emit(op + ", " + a + ", " + b + ", " + temp);
      return temp;
    }
    else
      return multTerm.generateCode();
  }
}
