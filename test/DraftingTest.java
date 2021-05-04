/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import static jdk.nashorn.tools.ShellFunctions.input;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author parma
 */
public class DraftingTest {
   
    CPClient client = new CPClient();
    

   
    /**
     * Test of linesetup method, of class Drafting.
     */
    @Test
    public void testLinesetup() {
        
        String[] arg = {"hlo"};       
        CommandController controller = new CommandController(); 
        Drafting drafting = new Drafting(client);
        LineSetup linesetup = new LineSetup(drafting, arg);
        controller.setCommand(linesetup);
        controller.userInput();
        String expectedResult = "hlo";
        String result = client.draftLines.getLast();
        assertEquals(expectedResult, result);

    }

    /**
     * Test of push method, of class Drafting.
     */
    @Test
    public void testPush() throws Exception {
        CommandController controller = new CommandController(); 
        Drafting drafting = new Drafting(client);
        PushSetup pushSetup = new PushSetup(drafting);
        controller.setCommand(pushSetup);
        controller.userInput();
    }

    /**
     * Test of undo method, of class Drafting.
     */
    @Test
    public void testUndo() {
        
           CommandController controller = new CommandController(); 
           Drafting drafting = new Drafting(client);
           UndoSetup undosetup = new UndoSetup(drafting);
           controller.setCommand(undosetup);
           controller.userInput();
           String expectedResult = null;
           String result = client.draftLines.getLast();
           assertEquals(expectedResult, result);
            
            }
    

    /**
     * Test of close method, of class Drafting.
     */
    @Test
    public void testClose() throws Exception {
           CommandController controller = new CommandController(); 
           Drafting drafting = new Drafting(client);
           CloseSetup closeSetup = new CloseSetup(drafting);
           controller.setCommand(closeSetup);
           controller.userInput();
           String expectedResult = client.draftTag;
           String result = client.ticketStateTag.getLast();
           assertEquals(expectedResult, result);
    }

    /**
     * Test of discard method, of class Drafting.
     */
    @Test
    public void testDiscard() {
        CommandController controller = new CommandController(); 
        Drafting drafting = new Drafting(client);
        DiscardSetup discardSetup = new DiscardSetup(drafting);
           controller.setCommand(discardSetup);
           controller.userInput();
        String expectedResult = "Main";
        String result = client.state;
        assertEquals(expectedResult, result);
    }

    /**
     * Test of changestate method, of class Drafting.
     */
    @Test
    public void testChangestate() {
        
        Drafting instance = new Drafting(client);
        instance.changestate();
        String expectedResult1 = "Main";
        String result1 = client.state;
        String expectedResult = null;
        String result = client.draftTag;
        assertEquals(expectedResult1, result1);
        assertEquals(expectedResult, result);
       
    }
    
}
