/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author parma
 */
public class CommandController {
    
     Command userinput;
    
    /**
     *
     * @param command
     */
    public void setCommand(Command command){
        userinput = command;
    }
    
    /**
     *
     */
    public void userInput(){
        userinput.execute();
    }
    
    
}
