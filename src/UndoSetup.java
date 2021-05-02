/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author parma
 */
public class UndoSetup implements Command{
    
    
    Drafting drafting;
  
    /**
     *
     * @param draft
     */
    public UndoSetup (Drafting draft){
        
        this.drafting = draft;
     
    }

    /**
     *
     */
    @Override
    public void execute() {
            drafting.undo();
    }
    
}
