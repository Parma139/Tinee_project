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
    
    Line line;
    String[] argstmp;
    public LineSetup (Line line, String[] args){
        
        this.line = line;
        this.argstmp = args;
    }

    @Override
    public void execute() {
        try {
            line.linesetup(argstmp);
        } catch (Exception ex) {
            System.out.println("I am in linesetup");
        } 

    }
}
