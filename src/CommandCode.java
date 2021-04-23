
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author parma
 */
public class CommandCode {
    
    
    String state = "Main";  
    String draftTag = null;
    List<String> draftLines = new LinkedList<>();
    BufferedReader reader = null;
    CLFormatter helper = null;
    String cmdInfo;
    String rawInfo;
     
    void printMainOptions(){
    
        System.out.print(helper.formatMainMenuPrompt());      
     }
    
    void printDraftingOptions(){
        System.out.print(helper.formatDraftingMenuPrompt(draftTag, draftLines));
      }
   
     // Read a line of user input
      

      void userInput() throws IOException{
          
      String raw = reader.readLine();
      if (raw == null) {
        throw new IOException("Input stream closed while reading.");
      }
      System.out.println("I am in userINout");
      // Trim leading/trailing white space, and split words according to spaces
      List<String> split = Arrays.stream(raw.trim().split("\\ "))
          .map(x -> x.trim()).collect(Collectors.toList());
      String cmd = split.remove(0);  // First word is the command keyword

      String[] rawArgs = split.toArray(new String[split.size()]);
      // Remainder, if any, are arguments
      cmdInfo=cmd;
      rawInfo = rawArgs[0];
      

     }
    
}
