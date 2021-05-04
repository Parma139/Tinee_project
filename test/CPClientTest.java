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
 * @author parma
 */
public class CPClientTest {
    
    public CPClientTest() {
    }

    /**
     * Test of main method, of class CPClient.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
       String[] args = {"username", "localhost", "8888"};
       String input = "read parma";
      ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes("UTF8"));
      System.setIn(in); // Sets System.in to the supplied stream
//      Client.main(args);
    }

    /**
     * Test of run method, of class CPClient.
//     */
//    @Test
//    public void testRun() throws Exception {
//        System.out.println("run");
//        CPClient instance = new CPClient();
//        instance.run();
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of loop method, of class CPClient.
//     */
//    @Test
//    public void testLoop() throws Exception {
//        System.out.println("loop");
//        CLFormatter helper = null;
//        BufferedReader reader = null;
//        CPClient instance = new CPClient();
//        instance.loop(helper, reader);
//        fail("The test case is a prototype.");
//    }
//    
}
