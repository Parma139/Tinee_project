
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
     *The constructor pass the specific draft
     * @param draft the draft is instance variable 
     * that allow to access the object or function according to user request  
     */
    public CloseSetup (Drafting draft){
        
        this.drafting = draft;
        
    }

    /**
     * this execute the method which user request
     */
    @Override
    public void execute() {

        try {
            drafting.close();
        } catch (IOException ex) {
               System.out.println("I am in Close setup");
            }
    }
}
