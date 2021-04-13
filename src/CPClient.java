
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import sep.tinee.net.message.Bye;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author parma
 */
public class CPClient {
    
    
    static String user;
    static  String host;
    static int port;

    boolean printSplash = true;
    
    
    public static void main (String[] args) throws IOException{
        
      
      Scanner userDetails;
      userDetails = new Scanner(System.in);
      
      System.out.println("\nPlease enter UserId");
      user = userDetails.next();
      
      System.out.println("\nPlease enter HostId");
      host = userDetails.next();
      
      System.out.println("\nPlease enter PortNo");
      port = userDetails.nextInt();
           

      CPClient client = new CPClient();
      client.run();    
        
        
       
        // access the read functionality
        
    
    CommandController controller = new CommandController();
    
    Read read = new Read();
    
    ReadSetup menusetup = new ReadSetup(read);
    
    controller.setCommand(menusetup);
    controller.userInput();
    
    }
    
    
    
    
    // Run the client
  @SuppressFBWarnings(
      value = "DM_DEFAULT_ENCODING",
      justification = "When reading console, ignore 'default encoding' warning")
  void run() throws IOException {

    BufferedReader reader = null;
    CLFormatter helper = null;
    try {
      reader = new BufferedReader(new InputStreamReader(System.in));

      if (this.user.isEmpty() || this.host.isEmpty()) {
         System.err.println("User/host has not been set.");
         run();                                  
      }
      
         helper = new CLFormatter(this.host, this.port);

      if (printSplash){                             
         System.out.print(helper.formatSplash(this.user));
     }
     //   loop(helper, reader);
    } 
    
    catch (Exception ex) {
      throw new RuntimeException(ex);
    } 
    
    finally {
      reader.close();
      if (helper.chan.isOpen()) {
        // If the channel is open, send Bye and close
        helper.chan.send(new Bye());
        helper.chan.close();
      }
    }
  }
}
