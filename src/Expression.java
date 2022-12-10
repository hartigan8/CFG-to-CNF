import java.util.List;
public class Expression {
    private String name; 
    private List<String> content;
    public Expression(String name, List<String> content) {
        this.name = name;
        this.content = content;
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
    

}