import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import grammar.Grammar;

public class App {

    static Grammar grammar;
    
    public static void main(String[] args) throws FileNotFoundException {
        grammar = new Grammar();
        readFile();
        System.out.println();
        grammar.eliminateEmpty();
        grammar.eliminateUnit();
    }

    private static void readFile() throws FileNotFoundException {
        File cfg = new File("CFG.txt");
        Scanner scn = new Scanner(cfg);

        while(scn.hasNextLine()){
            String line = scn.nextLine();
            if(!line.isBlank()){
                if(line.charAt(0) == 'E'){
                    String[] terminal = line.substring(2).split(",");
                    grammar.initTerminal(terminal);
                }
                else{
                    String[] parsedExpression = line.split("-");
                    String name = parsedExpression[0];
                    String[] values = parsedExpression[1].split("\\|");
                    List<String> valueList = Arrays.asList(values);
                    grammar.addExpression(name, valueList);
                }
                
            }
        }
        scn.close();
        grammar.printGrammar();
    }
}
