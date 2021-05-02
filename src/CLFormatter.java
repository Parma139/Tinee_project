
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import sep.tinee.net.channel.ClientChannel;
import sep.tinee.net.message.Message;

/**
 * A helper class for the current prototype {@link Client client}.  <i>E.g.</i>,
 * for formatting Command Line messages.
 */
public class CLFormatter {
    
    private final String RESOURCE_PATH = "resources/MessageBundle";
    private static ResourceBundle strings;

  static ClientChannel chan;  // Client-side channel for talking to a Tinee server

  CLFormatter(String host, int port) {
      
    strings = ResourceBundle.getBundle(RESOURCE_PATH, new Locale("en", "EN"));
    this.chan = new ClientChannel(host, port);
  }

  /* Interact with Tinee server */

  private void send(Message msg) throws IOException {
    this.chan.send(msg);
  }

  private Message receive() throws IOException, ClassNotFoundException {
    return this.chan.receive();
  }

  /* Following are the auxiliary methods for formatting the UI text */

  static String formatSplash(String user) {
      
    return println(strings.getString("formatsplash_message"), user);
  }

  static String formatMainMenuPrompt() {
      
      
    return strings.getString("main_menu_prompt_message");
            
            //"\n[Main] Enter command: read [mytag], manage [mytag], exit\n> ";
  }

  static String formatDraftingMenuPrompt(String tag, List<String> lines) {
    
      return strings.getString("format_Drafting_MenuPrompt_before_tag")+ formatDrafting(tag, lines) + strings.getString("format_Drafting_MenuPrompt_after_tag");
      
  }

  static String formatDrafting(String tag, List<String> lines) {
    StringBuilder b = new StringBuilder("#");
    b.append(tag);
    int i = 1;
    for (String x : lines) {
      b.append("\n");
      b.append(String.format("%12d", i++));
      b.append("  ");
      b.append(x);
    };
    return b.toString();
  }

  static String formatRead(String tag, List<String> users,
      List<String> read) {
    StringBuilder b = new StringBuilder("Read: #");
    b.append(tag);
    Iterator<String> it = read.iterator();
    for (String user : users) {
      b.append("\n");
      b.append(String.format("%12s", user));
      b.append("  ");
      b.append(it.next());
    };
    b.append("\n");
    return b.toString();
  }
  
  //Message format class and class use message formatting which use message
    //Object....... this is the array of the object and in this numebr need to pass in order

    /**
     * This is a messageformatter which is use to assign a value for a plae holder in I18n
     * @param message It pass the message to the print
     * @param params object... is an array of the object which allow to add any number of params(parameter) in this case it have user as parameter
     * @return it return the message with assing string for placeholder
     */
    public static String println(String message, Object...params){
        
//        System.out.println(MessageFormat.format(message,params));
        
        return MessageFormat.format(message,params);
    
    }
}
