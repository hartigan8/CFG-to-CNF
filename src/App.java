import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import grammar.Grammar;

public class App {

    private static Grammar grammar;
    
    public static void main(String[] args) throws FileNotFoundException {
        grammar = new Grammar();
        readFileAndCreateGrammar();
        grammar.ensureStartVariableNotAtRight();
        grammar.eliminateEmpty();
        grammar.eliminateUnit();
        grammar.eliminateTerminals();
        grammar.breakStrings();
    }

    private static void readFileAndCreateGrammar() throws FileNotFoundException {
        File cfg = new File("CFG.txt");
        Scanner scn = new Scanner(cfg);

        while(scn.hasNextLine()){
            String line = scn.nextLine();
            if(!line.isBlank()){
                line = line.trim();
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
        System.out.println("CFG Form");
        grammar.printGrammar();
        System.out.println();
    }
}