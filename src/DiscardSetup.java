
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author parma
 */
public class DiscardSetup implements Command{
    
        
    Drafting drafting;
    
    /**
     *
     * @param draft
     */
    public DiscardSetup (Drafting draft){
        
        this.drafting = draft;
        
    }

    /**
     *
     */
    @Override
    public void execute() {

               drafting.discard();
    }
    
}
