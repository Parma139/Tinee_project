/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 16084787
 */
public class CPClientTest {
    
    

    /**
     * Test of main method, of class CPClient.
     */
    @Test
    public void testMain() throws Exception {
      
      String[] args = {"username", "localhost", "8888"};
      String input = "read unknown\n manage message\n line hey runnting for testing \npush \n read message \n manage message\n line m1\n line m2\n undo\n push \nmanage message\n line m2\n discard\n manage message\n line m3\n close\n manage message\n exit";  
      ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes("UTF8"));
      System.setIn(in); // Sets System.in to the supplied stream
      CPClient.main(args);
    }

 /**
     * Test of run method, of class CPClient.
     */
    @Test
    public void testRun() throws Exception {
        
        CPClient instance = new CPClient();
        instance.run();
    
    }

//    /**
//     * Test of loop method, of class CPClient.
//    */
//    @Test
//    public void testLoop() throws Exception {
//        System.out.println("loop");
//        CLFormatter helper = null;
//        BufferedReader reader = null;
//        CPClient instance = new CPClient();
//        instance.loop(helper, reader);
//        fail("The test case is a prototype.");
//    }   
}
