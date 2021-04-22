
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
public class PushSetup implements Command{
    
       
       Drafting drafting;
    //String[] argstmp;
    public PushSetup (Drafting draft){
        
        this.drafting = draft;
        
    }

    @Override
    public void execute() {
        
           try {
               drafting.push();
//               IOException
           } catch (IOException ex) {
               System.out.println("I am in push setup");
           }
         

    }
    
}
