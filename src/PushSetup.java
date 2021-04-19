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
    String[] argstmp;
    String drafttag;
    public PushSetup (Drafting drafting, String drafttag){
        
        this.drafting = drafting;
       // this.argstmp = args;
        this.drafting=drafting;
    }

    @Override
    public void execute() {
        try {
            drafting.pushsetup(drafttag);
        } catch (Exception ex) {
            System.out.println("I am in push setup class");
        } 

    }
    
    
}
