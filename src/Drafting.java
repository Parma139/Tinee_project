
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
 *Drafting class is include all the command request which are available at drafting stage for the user 
 * @author 16084787
 */
public class Drafting {
    

    CPClient client; 
//    CLFormatter helper;
     
    /**
     *Constructor hold the instance of CPClient
     * @param client client is instance of the CPClient class
     */
    public Drafting(CPClient client){
        this.client = client;
    }
    /**
     * 
     * @param arg arg hold the message requested by the user to save in the sever
     */
   void linesetup(String[] arg){

         String line = Arrays.stream(arg).collect(Collectors.joining(" "));
         client.draftLines.add(line);  
    }
   
   /**
    * 
    * @throws IOException  IOException are thrown when there is any input/output file operational issue
    * while reading or writing the input or files
    */
   void push () throws IOException{
      
        CLFormatter.chan.send(new Push(client.user, client.draftTag, client.draftLines));
           changestate();
          
   }
    
   /**
    * undo method is remove the last enter tinee message by the user
    */
   void undo (){
       
       
        Iterator<String> addedLine = client.draftLines.iterator();
            if(addedLine.hasNext()){
            client.draftLines.removeLast();

              }  
            else {
                System.err.println("Nothing left for undo\n ");
            
            }
   
   }
   
   /**
    * 
    * @throws IOException  IOException are thrown when there is any input/output file operational issue
    * while reading or writing the input or files
    */
    void close () throws IOException{
       
        CLFormatter.chan.send(new Push(client.user, client.draftTag, client.draftLines));
           
        client.ticketStateTag.add(client.draftTag);
            changestate();
   
   }
    
   
/**
 * discard method is use to get back to the main stage without saving the enter tinee message by the user
 */
    void discard() {
        
          Iterator<String> addedLine = client.draftLines.iterator();
            if(addedLine.hasNext()){
            client.draftLines.clear();
            }
         changestate();
    }
 /**
  * change state is use to set the state and drafttag value to initial stage 
  */   
    
     void changestate(){
            client.state = "Main";
            client.draftTag = null;
    }
}
