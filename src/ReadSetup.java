/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author parma
 */
public class ReadSetup implements Command{
    
    Read read;
    
    public ReadSetup (Read read){
        
        this.read = read;
    
    }

    @Override
    public void execute() {
       read.setup();

    }
    
}
