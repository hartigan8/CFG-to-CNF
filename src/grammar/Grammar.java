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
        
        // is start variable at the right hand side flag
        boolean b = false;
        while(expressions.hasNext() && !b){
            // traverse expressions and find if one of them contains 'S'
            Expression expression = expressions.next();
            if(expression.consistStartVariable()){
                
                // if an expression contains 'S' then find a name to give
                // to find a name use ascii and count then add to grammarMap
                //String expName = newName();
                String expName = "S0";
                b = true;
                Expression newStart = new Expression(expName);
                newStart.addString("S");
                grammarMap.put(expName, newStart);
            }
        }
        if(b){
            System.out.println("Ensure start variable not at the right hand side");
            printGrammar();
            System.out.println();
        }
    }

    public void eliminateEmpty() {

        Iterator<Expression> expressions = grammarMap.values().iterator();
        HashSet<String> nulls = new HashSet<>();

        // after replacing a string if there is no char left in string
        // then this function must call itself
        boolean nullAppeared = false;

        // detemine expressions which contains empty
        while(expressions.hasNext()){
            Expression expression = expressions.next();
            if(expression.isContainsEmpty()){
                nulls.add(expression.getName());
            }
        }

        // traverse expressions and replace the variables which contains empty with ""
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
                            if(newString.length() == 0 && !nulls.contains(expression.getName())  && !expression.getName().equals("S")) {
                                nullAppeared = true;
                                expression.addString(EMPTY);
                            }
                            else {
                                expression.addString(newString);
                            }
                        }
                    }
                }
            }
        }

        // deleting null chars from expressions
        nulls.stream().forEach(nullExpression -> grammarMap.get(nullExpression).getContent().remove(EMPTY));

        if(nullAppeared){
            eliminateEmpty();
        }
        else{
            System.out.println("Eliminate €  ");
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
        System.out.println("Eliminate unit production");
        printGrammar();
        System.out.println();
    }

    public void eliminateTerminals() {

        // find variable names for terminals and create a HashMap to link names with terminals
        // from terminal get variable name this make easier replacing terminals with names
        HashMap<String, String> terminalMap = new HashMap<>();
        for (String t : terminal) {
            
            String expressionName = newName();
            terminalMap.put(t, expressionName);
            Expression exp = new Expression(expressionName);
            exp.addString(t);
            grammarMap.put(expressionName, exp);
        }
        // replace terminals with variable
        Iterator<Expression> expressions = grammarMap.values().iterator();
        while(expressions.hasNext()){
            Expression exp = expressions.next();
            if(!terminalMap.containsValue(exp.getName())){
                exp.replaceChars(terminalMap);
            }
            
        }
        System.out.println("Eliminate terminals ");
        printGrammar();
        System.out.println();
    }

    public void breakStrings() {

        
        HashMap<String, String> varMap; //value, name
        Iterator<Expression> expIt = grammarMap.values().iterator();
        List<String> moreThanTwoList;
        while(true){

            varMap = new HashMap<>();
            moreThanTwoList = new LinkedList<>();
            List<String> invalidList = new LinkedList<>();

            // getting substrings of strings that longer than 2
            while (expIt.hasNext()) {
                Expression exp = expIt.next();
                moreThanTwoList.addAll(exp.moreThanTwoList());
                
            }

            // if there is no string such that then exit
            if(moreThanTwoList.size() == 0){
                break;
            }

            // put them into a map to use function in expression class
            for (String string : moreThanTwoList) {
                if(!varMap.containsKey(string)){
                    String newName = newName();
                    Expression exp = new Expression(newName);
                    exp.addString(string);
                    grammarMap.put(newName, exp);
                    varMap.put(string, newName);
                    // mark all of them unused and if one of them used then remove from the list
                    invalidList.add(newName);
                }
            }

            // replace substrings with variables
            
            Iterator<Expression> expressions = grammarMap.values().iterator();

            while(expressions.hasNext()){
                Expression exp = expressions.next();
                if(!varMap.containsValue(exp.getName())){
                    
                    Iterator<String> varMapIterator = varMap.keySet().iterator();
                    while (varMapIterator.hasNext()) {

                        // create single unit map to see that if that var ever used
                        String singleKey = varMapIterator.next();
                        String singleValue = varMap.get(singleKey);
                        HashMap<String, String> singleVar = new HashMap<>();

                        singleVar.put(singleKey, singleValue);
                        boolean invalid = exp.replaceChars(singleVar);
                        // if used even once them remove invalid list
                        if(!invalid && invalidList.contains(singleValue)){
                            invalidList.remove(singleValue);
                        }
                    }
                    
                }
                    
            }
            
            // delete unused substrings
            for (String string : invalidList) {
                grammarMap.remove(string);
            }

            
        }
        

        System.out.println("Break variable strings longer than 2 ");
        printGrammar();
        System.out.println();
    }

    // to get new name for a variable
    private String newName() {
        while(true){
            String expressionName = String.valueOf((char) charCounter);
            if(grammarMap.containsKey(expressionName)){
                charCounter++;
            }
            else{
                return expressionName;
            }
        }
    }
}