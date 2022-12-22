package grammar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
public class Expression {

    private String name; 
    private List<String> content;

    protected Expression(String name) {
        this.name = name;
        this.content = new LinkedList<>();

    }
    protected String getName() {
        return name;
    }
    protected List<String> getContent() {
        return content;
    }
    protected void setContents(List<String> content) {
        this.content.addAll(content);
    }

    protected void addString(String s){
        content.add(s);
    }

    protected void addAllStrings(List<String> strings) {
        content.addAll(strings);
    }

    
    protected void printExpression() {
        Iterator<String> contentIterator =  content.iterator();
        System.out.print(name + "-");
        while(contentIterator.hasNext()){
            System.out.print(contentIterator.next());
            if(contentIterator.hasNext()) System.out.print("|");
        }
        System.out.println();
    }
    protected boolean isContainsEmpty() {
        for (String string : content) {
            if(string.equals(Grammar.EMPTY)) return true;
        }
        return false;
    }
    

    protected boolean deleteEmptyVariable(String expressionName) {
        int size = content.size();
        for (int i = 0; i < size; i++) {
            String string = content.get(i);
            if(string.contains(expressionName)){
                String newString = string.replace(expressionName, "");
                if(newString.length() == 0){
                    content.add(Grammar.EMPTY);
                }
                else{
                    content.add(newString);
                }
            }
        }
        return isContainsEmpty();
    }

    protected void deleteEmptyChar() {
        for (int i = 0; i < content.size(); i++) {
            String string = content.get(i);
            if(string.length() == 1 && string.equals(Grammar.EMPTY)) {
                content.remove(string);
            }
        }
    }
    
    protected boolean consistStartVariable() {
        for (String string : content) {
            for (int i = 0; i < string.length(); i++) {
                if(string.charAt(i) == 'S'){
                    return true;
                }
            }
        }
        return false;
    }
    protected List<String> unitProductions(HashSet<String> terminals){
        List<String> buffer = new LinkedList<>();
        for (String string : content) {
            if(string.length() == 1 && !terminals.contains(string)){
                buffer.add(string);
            }
        }
        return buffer;
    }
    public void removeAllStrings(List<String> buffer) {
        for (String string : buffer) {
            content.remove(string);
        }
    }
}