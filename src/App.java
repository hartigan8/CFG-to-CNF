import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
        
        File cfg = new File("CFG.txt");
        List<String> alphabet = new ArrayList<>();
        List<String> expressions = new LinkedList<>();
        
        Scanner scn = new Scanner(cfg);
        while(scn.hasNextLine()){
            String line = scn.nextLine();
            if(!line.isBlank()){
                if(line.charAt(0) == 'E'){
                    String[] alphabetArray = line.substring(2).split(",");
                    alphabet.addAll(Arrays.asList(alphabetArray));
                }
                else{
                    
                }
                
            }
            
        }
        expressions.toString();

    }
}
