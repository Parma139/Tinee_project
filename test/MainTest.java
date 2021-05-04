/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author parma
 */
public class MainTest {
    
    public MainTest() {
    }

    /**
     * Test of readsetup method, of class Main.
     */
    @Test
    public void testReadsetup() throws Exception {
        
            String rawargs = "kk";
            CommandController controller = new CommandController();    
            Main main = new Main();
            ReadSetup menusetup = new ReadSetup(main, rawargs);
            controller.setCommand(menusetup);
            controller.userInput();
    }
    
}
