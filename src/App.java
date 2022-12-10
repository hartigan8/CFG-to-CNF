import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class App {
    static List<String> alphabet = new ArrayList<>();
    static List<Expression> expressions = new LinkedList<>();
    
    private static void printExpressions() {
        for (Expression expression : expressions) {
            expression.printExpression();
        }
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

    private static void eliminateEmpty() {
        for (Expression expression : expressions) {
            List<String> content = expression.getContent();
            for (String str : content) {
                if(str.contains("€")){

                }
            }
        }
        printExpressions();
    }

    public static void main(String[] args) throws FileNotFoundException {
        readFile();
        printExpressions();
    }
}
