
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 16084787
 */
public class ReadSetup implements Command{
    
    Main main;
    String argstmp;

    /**
     *Constructor pass the specific main that this command is going to control
     * @param main main is the drafting object that is going to be the receiver of the object when execute will called
     * @param tagname it hold drafttag or tagname which use to check this tag include any message on the server
     */
    public ReadSetup (Main main, String tagname){
        
        this.main = main;
        this.argstmp = tagname;
    }

    /**
     * this method will calls the main() method on the receiving object, which is the Main that are controlling
     */
    @Override
    public void execute() {
        try {
            main.readsetup(argstmp);
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex + "Invalid input try again");
        } 

    }
    
}
