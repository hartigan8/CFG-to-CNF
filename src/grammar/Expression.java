package grammar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
public class Expression {

    private String name; 
    private List<String> content;
    private List<String> directedBy;


    protected Expression(String name) {
        this.name = name;
        this.content = new LinkedList<>();
        this.directedBy = new LinkedList<>();

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

    protected void addDiretctedBy(String s) {
        if(!directedBy.contains(s))
        directedBy.add(s);
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
    
    protected List<String> getDirectedBy() {
        return directedBy;
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
            if(content.get(i).contains(Grammar.EMPTY)) content.remove(content.get(i));
        }
    }
}