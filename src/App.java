import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
    static List<String> alphabet = new ArrayList<>();
    static List<Expression> expressions = new LinkedList<>();
    
    
    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        printExpressions();
        eliminateEmpty();
    }

    private static void readFile() throws FileNotFoundException {
        File cfg = new File("CFG.txt");
        Scanner scn = new Scanner(cfg);

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
                    Expression expression = new Expression(parsedExpression[0], valueList);
                    expressions.add(expression);
                }
                
            }
        }

        scn.close();
    }

    private static void printExpressions() {
        for (Expression expression : expressions) {
            expression.printExpression();
        }
        System.out.println();
    }

    private static void eliminateEmpty() {
        System.out.println("Eliminate €");
        List<String> nullableVars = new ArrayList<>();
        for (Expression expression : expressions) {
            List<String> contentToIterate = new ArrayList<>(expression.getContent());
            List<String> actualContent = expression.getContent();
            for (String str : contentToIterate) {
                if(str.equals("€")){
                    nullableVars.add(expression.getName());
                    actualContent.remove("€");
                }
            }

        }
        for (Expression expression : expressions) {
            List<String> contentToIterate = new ArrayList<>(expression.getContent());
            List<String> actualContent = expression.getContent();
            for (String str : contentToIterate) {
                String newStr = new String(str);
                boolean replaced = false;
                for(String nullVar : nullableVars){
                    if(newStr.contains(nullVar)){
                        newStr = newStr.replace(nullVar, "");
                        replaced = true;
                    }
                }
                if(replaced && newStr.length() > 0)
                actualContent.add(newStr);
            }
            
        }
        printExpressions();
    }
    
}
