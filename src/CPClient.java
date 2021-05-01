
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
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
    CommandController controller = new CommandController();
    public LinkedList<String> draftLines = new LinkedList<>();  // Holds the current draft data when in the "Drafting" state
    public LinkedList<String> ticketStateTag = new LinkedList<>();
     public String state = "Main";  // Initial state
    public String draftTag = null;                              //String draftTag = null; declared in outside of the method
    


    public CPClient (){
      this.user = "parma";
      this.host = "localhost";
      this.port = 8888;

    }

    public static void main (String[] args) throws IOException{

      CPClient client = new CPClient();
      client.run();

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

         System.err.println("User/host has not been set. \n\nPlease enter User/host name: ");
         String userDetails = reader.readLine();
         if (user == null){
         this.user = userDetails;
         }
         else {
         this.host = userDetails;
         }

      run();

      }

      else{
        helper = new CLFormatter(this.host, this.port);
        System.out.print(helper.formatSplash(this.user));
        loop(helper, reader);
      }

    }

    catch (IOException | ClassNotFoundException ex) {
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



      if (cmd.isEmpty()){
      System.err.println("Type the input");
      continue;

      }
      // Process user input
      if ("exit".startsWith(cmd)) {
        // exit command applies in either state
        done = true;
   
      } // "Main" state commands
      else if (state.equals("Main")) {
          
          draftTag = rawArgs[0];
          boolean ticket = ticketStateTag.contains(draftTag);
          
        if ("manage".startsWith(cmd) && ticket == false ) {
          // Switch to "Drafting" state and start a new "draft
          
          state = "Drafting";
          draftTag = rawArgs[0];
        }
        else if (ticket) {
            System.out.println("\n >>>>>>>>>>> Ticket has been closed >>>>>>>>>>>");
        }

        else if ("read".startsWith(cmd)){
          // Read tines on server
            Main main = new Main();
            ReadSetup menusetup = new ReadSetup(main, rawArgs[0]);
            controller.setCommand(menusetup);
            controller.userInput();
        }

      } // "Drafting" state commands
      else if (state.equals("Drafting")) {
         
        Drafting drafting = new Drafting(this);
        if ("line".startsWith(cmd)) {
          // Add a tine message line
            LineSetup linesetup = new LineSetup(drafting, rawArgs);
            controller.setCommand(linesetup);
            controller.userInput();
        }     
        else if ("push".startsWith(cmd)) {
           
           PushSetup pushSetup = new PushSetup(drafting);
           controller.setCommand(pushSetup);
           controller.userInput();
        } 
        
        else if ("undo".startsWith(cmd)) {

           UndoSetup undosetup = new UndoSetup(drafting);
           controller.setCommand(undosetup);
           controller.userInput();
        } 
            
        else if ("close".startsWith(cmd)) {

           CloseSetup closeSetup = new CloseSetup(drafting);
           controller.setCommand(closeSetup);
           controller.userInput();
        
        }  
        
         else if ("discard".startsWith(cmd)) {

           DiscardSetup discardSetup = new DiscardSetup(drafting);
           controller.setCommand(discardSetup);
           controller.userInput();
        
        } 
              
        else {
          System.out.println("Could not parse command/args.");
        }
      } else {
        System.out.println("Could not parse command/args.");
      }
    }
    return;
  }
}
