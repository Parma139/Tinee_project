
import java.io.BufferedReader;
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
public class Read {
    
      void readsetup(String arg) throws IOException, ClassNotFoundException{
          

          CLFormatter helper = null;
          helper.chan.send(new ReadRequest(arg));
          ReadReply rep = (ReadReply) helper.chan.receive();
          System.out.print(
          helper.formatRead(arg, rep.users, rep.lines));
    }   
}
