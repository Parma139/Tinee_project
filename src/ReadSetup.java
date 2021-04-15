
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
public class ReadSetup implements Command{
    
    Read read;
    String argstmp;
    public ReadSetup (Read read, String args){
        
        this.read = read;
        this.argstmp = args;
    }

    @Override
    public void execute() {
        try {
            read.readsetup(argstmp);
        } catch (Exception ex) {
            System.out.println("I am in readsetup class");
        } 

    }
    
}
