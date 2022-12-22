package grammar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Grammar {
    private HashMap<String, Expression> grammarMap;
    private HashSet<String> terminal;
    public static final String EMPTY = "€";
    private int charCounter;

    public Grammar() {
        this.grammarMap = new HashMap<>();
        this.terminal = new HashSet<>();
        charCounter = 65;
    }
    
    public void initTerminal(String[] terminalContent) {
        for (String string : terminalContent) {
            terminal.add(string);
        }
    }

    public void addExpression(String name, List<String> content) {
        Expression expression = new Expression(name);
        expression.addAllStrings(content);
        grammarMap.put(name, expression);
    }

    
    public void printGrammar() {
        Iterator<Expression> expressions = grammarMap.values().iterator();
        while(expressions.hasNext()){
            expressions.next().printExpression();
        }
    }

    public void ensureStartVariableNotAtRight() {
        Iterator<Expression> expressions = grammarMap.values().iterator();
        boolean b = false;
        while(expressions.hasNext() && !b){
            Expression expression = expressions.next();
            if(expression.consistStartVariable()){
                
                while(true){
                    
                    String expName = String.valueOf((char) charCounter);
                    if(!grammarMap.containsKey(expName)){
                        b = true;
                    }
                    else charCounter++;
                    if(b){
                        Expression newStart = new Expression(expName);
                        newStart.addString("S");
                        grammarMap.put(expName, newStart);
                        break;
                    }
                }
            }
        }
        if(b){
            printGrammar();
            System.out.println();
        }
    }

    public void eliminateEmpty() {

        Iterator<Expression> expressions = grammarMap.values().iterator();
        HashSet<String> nulls = new HashSet<>();
        boolean nullAppeared = false;

        while(expressions.hasNext()){
            Expression expression = expressions.next();
            if(expression.isContainsEmpty()){
                nulls.add(expression.getName());
            }
        }

        expressions = grammarMap.values().iterator();
        while(expressions.hasNext()){
            Expression expression = expressions.next();
            List<String> content = expression.getContent();

            for (int i = 0; i < content.size(); i++) {
                String string = content.get(i);
                for (int j = 0; j < string.length(); j++) {
                    if(nulls.contains(String.valueOf(string.charAt(j)))){
                        String newString = string.substring(0,j) + string.substring(j + 1);
                        if(!content.contains(newString)){
                            if(newString.length() == 0 && !nulls.contains(expression.getName())) {
                                nullAppeared = true;
                                content.add(EMPTY);
                            }
                            else {
                                content.add(newString);
                            }
                        }
                    }
                }
            }
        }
        nulls.stream().forEach(nullExpression -> grammarMap.get(nullExpression).getContent().remove(EMPTY));
        if(nullAppeared){
            eliminateEmpty();
        }
        else{
            printGrammar();
            System.out.println();
        }
    }

    public void eliminateUnit() {
        Iterator<Expression> expressions = grammarMap.values().iterator();
        while(expressions.hasNext()){
            Expression expression = expressions.next();
            while(true){
                List<String> buffer = expression.unitProductions(terminal);
                expression.removeAllStrings(buffer);
                if(buffer.size() == 0){
                    break;
                }
                else{
                    for (String string : buffer) {
                        List<String> contentsToAdd = grammarMap.get(string).getContent();
                        expression.addAllStrings(contentsToAdd);
                    }
                }
            }

        }
        printGrammar();
        System.out.println();
    }

    public void eliminateTerminals() {
        
        printGrammar();
        System.out.println();
    }

    public void breakStrings() {
        printGrammar();
        System.out.println();
    }
}