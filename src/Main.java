
import java.io.IOException;
import sep.tinee.net.message.ReadReply;
import sep.tinee.net.message.ReadRequest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author parma
 */
public class Main {
    
//    CPClient client;
//    
//    public Main(CPClient client){
//        this.client = client;
//    }
    
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

