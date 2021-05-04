
import java.io.IOException;
import sep.tinee.net.message.ReadReply;
import sep.tinee.net.message.ReadRequest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Constructor include all the command which is avaliable in the main stage for the user
 * @author 16084787
 */
public class Main {
    
//    CPClient client;
//    
//    public Main(CPClient client){
//        this.client = client;
//    }
   
    /**
     * 
     * @param arg arg hold drafttag or tag name to look in the sever that this tag have any saved message or not
     * @throws IOException IOException are thrown when there is any input/output file operational issue
     * while reading or writing the input or files
     * @throws ClassNotFoundException ClassNotFoundException are thrown when application not able to find the class path
     */
    void readsetup(String arg) throws IOException, ClassNotFoundException{

          CLFormatter helper = null;
          helper.chan.send(new ReadRequest(arg));
          ReadReply rep = (ReadReply) helper.chan.receive();
          System.out.print(
          helper.formatRead(arg, rep.users, rep.lines));
    }  
       
       
//    void manageSetup(){
//       
//    
//         if (client.ticket == false ) {
//          // Switch to "Drafting" state and start a new "draft
//          
//          client.state = "Drafting";
//          client.draftTag = rawArgs[0];
//        }
//        else{
//             
//            System.out.println("\n >>>>>>>>>>> Ticket has been closed >>>>>>>>>>>");
//        }
//       
//      
//       
//       }
       
    }

