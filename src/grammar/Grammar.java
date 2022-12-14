package grammar;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Grammar {
    private HashMap<String, Expression> grammarMap;
    private List<String> terminal;
    public static final String EMPTY = "€";

    public Grammar() {
        this.grammarMap = new HashMap<>();
        this.terminal = new ArrayList<>();
    }
    
    public void initTerminal(String[] terminalContent) {
        terminal = Arrays.asList(terminalContent);
    }

    public void addExpression(String name, List<String> content) {


        for (String string : content) {
            String[] variables = string.split("");
            for (String letter : variables) {
                if(!terminal.contains(letter) && !letter.equals(EMPTY)){
                    addToDirectedByOfAExpression(name, letter);
                }
            }
        }

        if(grammarMap.containsKey(name)){
            Expression expression = grammarMap.get(name);
            expression.setContents(content);

        }
        else{
            Expression expression = new Expression(name);
            expression.addAllStrings(content);

            grammarMap.put(name, expression);
        }
        
    }

    private void addToDirectedByOfAExpression(String directedBy, String directsTo) {
        if(!grammarMap.containsKey(directsTo)){
            grammarMap.put(directsTo, new Expression(directsTo));
        }
        grammarMap.get(directsTo).addDiretctedBy(directedBy);
    }
    
    public void printGrammar() {
        Iterator<Expression> expressions = grammarMap.values().iterator();
        while(expressions.hasNext()){
            expressions.next().printExpression();
        }
    }

    public void eliminateEmpty() {
        Iterator<Expression> expressions = grammarMap.values().iterator();
        boolean newPossibleEmpty = false;
        while(expressions.hasNext()){
            Expression expression = expressions.next();
            if(expression.isContainsEmpty()){
                newPossibleEmpty = elimateEmptyDirectedBy(expression);
            }
        }
        if(newPossibleEmpty){
            eliminateEmpty();
        }
        printGrammar();
    }

    private boolean elimateEmptyDirectedBy(Expression expression) {
        boolean gotEmpty = false;
        List<String> directedBy = expression.getDirectedBy();
        for (String string : directedBy) {
            Expression directedByExpression = grammarMap.get(string);
            boolean consistEmpty = directedByExpression.deleteEmptyVariable(expression.getName());
            if(consistEmpty) gotEmpty = true;
        }
        expression.deleteEmptyChar();

        return gotEmpty;
    }
}
