import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class Expression {
    private String name; 
    private List<String> content;
    public Expression(String name, List<String> content) {
        this.name = name;
        this.content = new ArrayList<>(content);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getContent() {
        return content;
    }
    public void setContents(List<String> content) {
        this.content = content;
    }
    
    public void printExpression() {
        Iterator<String> contentIterator =  content.iterator();
        System.out.print(name + "-");
        while(contentIterator.hasNext()){
            System.out.print(contentIterator.next());
            if(contentIterator.hasNext()) System.out.print("|");
        }
        System.out.println();
    }

}