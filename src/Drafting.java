
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
    

    CPClient client; 
    CLFormatter helper = null;
     
    public Drafting(CPClient client){
        this.client = client;
    }
    
   void linesetup(String[] arg){

         String line = Arrays.stream(arg).collect(Collectors.joining(" "));
         client.draftLines.add(line);  
    }
   
   void push () throws IOException{
      
        helper.chan.send(new Push(client.user, client.draftTag, client.draftLines));
           changestate();
          
   }
    
   void undo (){
       
       
        Iterator<String> addedLine = client.draftLines.iterator();
            if(addedLine.hasNext()){
            client.draftLines.removeLast();

              }  
   
   }
   
   
    void close () throws IOException{
       
        helper.chan.send(new Push(client.user, client.draftTag, client.draftLines));
           
            client.ticketStateTag.add(client.draftTag);
            changestate();
   
   }
    
    void changestate(){
            client.state = "Main";
            client.draftTag = null;
    }
}
