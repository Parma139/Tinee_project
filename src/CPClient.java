
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import sep.tinee.net.message.Bye;
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
public class CPClient {
    
    
    String user;
    String host;
    int port;
    
    public CPClient (){
      this.user = "parma";
      this.host = "localhost";
      this.port = 8888;
    
    }
     
    public static void main (String[] args) throws IOException{
  
      CPClient client = new CPClient();
      client.run();    
             
//     // access the read functionality
//       CommandController controller = new CommandController();
//       Read read = new Read();
//       ReadSetup menusetup = new ReadSetup(read);
//       controller.setCommand(menusetup);
//       controller.userInput();
//    
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
       
      if (user == null || host == null) {
         
         System.err.println("User/host has not been set. \n"); 
        
         Scanner userDetails = new Scanner (System.in);
         System.err.println("Please enter User/host name:"); 
         String userInput = userDetails.next();
           
         if (user == null){
         this.user = userInput; 
         }
         else {
         this.host = userInput;
         }
         
      run();
      
      }
      
      else{
        helper = new CLFormatter(this.host, this.port);                     
        System.out.print(helper.formatSplash(this.user));
        loop(helper, reader);
      }
     
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
  
  
  
  // Main loop: print user options, read user input and process
  void loop(CLFormatter helper, BufferedReader reader) throws IOException,
      ClassNotFoundException {

    // The app is in one of two states: "Main" or "Drafting"
    String state = "Main";  // Initial state

    // Holds the current draft data when in the "Drafting" state
    String draftTag = null;
    List<String> draftLines = new LinkedList<>();

    // The loop
   for (boolean done = false; !done;)
    {

      // Print user options
      if (state.equals("Main")) {
        System.out.print(helper.formatMainMenuPrompt());
      } else {  // state = "Drafting"
        System.out.print(helper.
            formatDraftingMenuPrompt(draftTag, draftLines));
      }

      // Read a line of user input
      String raw = reader.readLine();
      if (raw == null) {
        throw new IOException("Input stream closed while reading.");
      }
      // Trim leading/trailing white space, and split words according to spaces
      List<String> split = Arrays.stream(raw.trim().split("\\ "))
          .map(x -> x.trim()).collect(Collectors.toList());
      String cmd = split.remove(0);  // First word is the command keyword
      String[] rawArgs = split.toArray(new String[split.size()]);
      // Remainder, if any, are arguments

      // Process user input
      if ("exit".startsWith(cmd)) {
        // exit command applies in either state
        done = true;
      } // "Main" state commands
      else if (state.equals("Main")) {
        if ("manage".startsWith(cmd)) {
          // Switch to "Drafting" state and start a new "draft"
          state = "Drafting";
          draftTag = rawArgs[0];
        } else if ("read".startsWith(cmd)){
          // Read tines on server
          helper.chan.send(new ReadRequest(rawArgs[0]));
          ReadReply rep = (ReadReply) helper.chan.receive();
          System.out.print(
              helper.formatRead(rawArgs[0], rep.users, rep.lines));
        } else {
          System.out.println("Could not parse command/args.");
        }
      } // "Drafting" state commands
      else if (state.equals("Drafting")) {
        if ("line".startsWith(cmd)) {
          // Add a tine message line
          String line = Arrays.stream(rawArgs).
              collect(Collectors.joining());
          draftLines.add(line);
        } else if ("push".startsWith(cmd)) {
          // Send drafted tines to the server, and go back to "Main" state
          helper.chan.send(new Push(user, draftTag, draftLines));
          state = "Main";
          draftTag = null;
        } else {
          System.out.println("Could not parse command/args.");
        }
      } else {
        System.out.println("Could not parse command/args.");
      }
    }
    return;
  } 
}
