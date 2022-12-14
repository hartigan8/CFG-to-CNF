package grammar;

import java.util.List;

public class BufferExpression {

    private String target;
    private List<String> content;
    
    public BufferExpression(String target, List<String> content) {
        this.target = target;
        this.content = content;
    }

    public String getTarget() {
        return target;
    }

    public List<String> getContent() {
        return content;
    }
    
    
}
