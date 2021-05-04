/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 16084787
 */
public class CommandController {
    
     Command userinput;
    
    /**
     *This method control behaviour of the command requested by user
     * @param command this set the command that the user is going to control
     * could be called multiple times if the client of this wanted to change the behaviour of the command.
     */
    public void setCommand(Command command){
        userinput = command;
    }
    
    /**
     *This method takes the current command bound to the userinput() and calls its execute() method.  
     */
    public void userInput(){
        userinput.execute();
    }
    
    
}
