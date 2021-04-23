
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import sep.tinee.net.message.Push;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author parma
 */
public class Drafting {
    
     public LinkedList<String> CPdraftLines = new LinkedList<>();
     CPClient draftvar = new CPClient();
     String list;
//   String linesetup(String[] arg){
   void linesetup(String[] arg){
    
//         List<String> CPdraftLines = new LinkedList<>();

         String line = Arrays.stream(arg).collect(Collectors.joining(" "));
//         CPdraftLines.add(line); //i edited
         draftvar.draftLines.add(line);
         System.out.println("I am afte add draftline  in line class: and priting using get last " + draftvar.draftLines.getLast());
         System.out.println("I am afte add draftline  in line class: without get lastt " + draftvar.draftLines);
//      System.out.println("I am afte add draftline  in line class: without get lastt " + CPdraftLines);
          list = draftvar.draftLines.getLast();  
//         return  line;

    }
   
   void push () throws IOException{
        
          String userID = draftvar.user;
          String draftTagID = draftvar.draftTag;
          CLFormatter helper = null;
//        
        
          CPdraftLines.add(list);
   
          helper.chan.send(new Push(userID, draftTagID, CPdraftLines));
          System.out.println("Draftlineline: in push options "+ draftvar.draftLines);
   }
    
   void undo (){
       
       
        Iterator<String> addedLine = draftvar.draftLines.iterator();
              if(addedLine.hasNext()){
              draftvar.draftLines.removeLast();
              }  
       
   }
}
