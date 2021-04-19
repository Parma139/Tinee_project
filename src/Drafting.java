
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import sep.tinee.net.message.Push;
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
public class Drafting {
    
     CPClient draftvar = new CPClient();
     CLFormatter helper = null;
     public List<String> draftLines = new LinkedList<>();
    void linesetup(String[] argtmp) {
          
          String line = Arrays.stream(argtmp).
          collect(Collectors.joining(" "));
         
           draftLines.add(line);
           System.out.println("I am line setup and user name is: "+draftvar.user + " after adding " + draftLines);
         
//         return line;
    }
    
    void pushsetup(String draftTag) throws IOException{
        
        String userName = draftvar.user;
        helper.chan.send(new Push(userName, draftTag, draftLines));
        System.out.println("I am pushsetup in drafting ()"+userName);
    
    }
    
}
