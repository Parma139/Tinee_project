
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *The class represents the Closesetup  
 * @author 16084787
 */
public class CloseSetup implements Command{
    
    Drafting drafting;
   
    /**
    *Constructor pass the specific drafting that this command is going to control
    * @param draft draft is the drafting object that is going to be the receiver of the object when execute will called
    */
    public CloseSetup (Drafting draft){
        
        this.drafting = draft;
        
    }

    /**
     * this method will calls the discard() method on the receiving object, which is the drafting that are controlling
     */
    @Override
    public void execute() {

        try {
            drafting.close();
        } catch (IOException ex) {
               System.out.println("Invalid input try again");
            }
    }
}
