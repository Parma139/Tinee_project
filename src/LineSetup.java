/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author parma
 */
public class LineSetup implements Command{
    
    Drafting drafting;
    String[] argstmp;

    /**
     *
     * @param draft
     * @param args
     */
    public LineSetup (Drafting draft, String[] args){
        
        this.drafting = draft;
        this.argstmp = args;
    }

    /**
     *
     */
    @Override
    public void execute() {
        try {
            drafting.linesetup(argstmp);
        } catch (Exception ex) {
            System.out.println("I am in linesetup");
        } 

    }
}
