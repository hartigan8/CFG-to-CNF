package grammar;

import java.util.List;

public class BufferExpression {

    private String target;
    private List<String> content;
    
    protected BufferExpression(String target, List<String> content) {
        this.target = target;
        this.content = content;
    }

    protected String getTarget() {
        return target;
    }

    protected List<String> getContent() {
        return content;
    }
    
    
}
