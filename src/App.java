import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
        
        File cfg = new File("CFG.txt");
        Scanner scn = new Scanner(cfg);

        List<String> alphabet = new ArrayList<>();
        List<Variable> expressions = new LinkedList<>();
        
        while(scn.hasNextLine()){
            String line = scn.nextLine();
            if(!line.isBlank()){
                if(line.charAt(0) == 'E'){
                    String[] alphabetArray = line.substring(2).split(",");
                    alphabet.addAll(Arrays.asList(alphabetArray));
                }
                else{
                    String[] parsedExpression = line.split("-");
                    String[] values = parsedExpression[1].split("\\|");
                    List<String> valueList = Arrays.asList(values);
                    Variable expression = new Variable(parsedExpression[0], valueList);
                    expressions.add(expression);
                }
                
            }
        }


        
        scn.close();
    }
}
