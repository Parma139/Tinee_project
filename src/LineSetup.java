/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 16084787
 */
public class LineSetup implements Command{
    
    Drafting drafting;
    String[] argstmp;

    /**
     *
     *Constructor pass the specific drafting that this command is going to control
     * @param draft draft is the drafting object that is going to be the receiver of the object when execute will called
     * @param args it hold line which enter by the user or we can message to save in the server
     */
    public LineSetup (Drafting draft, String[] args){
        
        this.drafting = draft;
        this.argstmp = args;
    }

    /**
     * this method will calls the discard() method on the receiving object, which is the drafting that are controlling
     */
    @Override
    public void execute() {
        try {
            drafting.linesetup(argstmp); 
        } catch (Exception ex) {
            System.out.println("Invalid input try again" );
        } 

    }
}
