
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
 * @author parma
 */
public class CloseSetup implements Command{
    
    Drafting drafting;
   
    public CloseSetup (Drafting draft){
        
        this.drafting = draft;
        
    }

    @Override
    public void execute() {

        try {
            drafting.close();
        } catch (IOException ex) {
               System.out.println("I am in Close setup");
            }
    }
}
