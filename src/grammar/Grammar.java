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

    public Grammar() {
        this.grammarMap = new HashMap<>();
        this.terminal = new HashSet<>();
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
        }
    }

    public void eliminateUnit() {
        Iterator<Expression> expressions = grammarMap.values().iterator();
        List<BufferExpression> buffers = new LinkedList<>();
        while(expressions.hasNext()){
            Expression expression = expressions.next();
            List<String> contents = expression.getContent();
            int size = contents.size();
            for (int i = 0; i < size; i++) {
                String string = contents.get(i);
                if(string.length() == 1 && !terminal.contains(string)){
                    buffers.add(new BufferExpression(expression.getName(), grammarMap.get(string).getContent()));
                }
            }
        }
        for (BufferExpression buffer : buffers) {
            grammarMap.get(buffer.getTarget()).addAllStrings(buffer.getContent());
        }
        printGrammar();
        System.out.println();
    }
}