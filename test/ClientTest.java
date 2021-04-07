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
public class ClientTest {
    
    public ClientTest() {
    }

    /**
     * Test of main method, of class Client.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = {"username", "localhost", "8888"};
        String input = "exit\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes("UTF8"));
        System.setIn(in); // Sets System.in to the supplied stream
        Client.main(args);
        
    }

//    /**
//     * Test of set method, of class Client.
//     */
//    @Test
//    public void testSet() {
//        System.out.println("set");
//        String user = "";
//        String host = "";
//        int port = 0;
//        Client instance = new Client();
//        instance.set(user, host, port);
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of run method, of class Client.
//     */
//    @Test
//    public void testRun() throws Exception {
//        System.out.println("run");
//        Client instance = new Client();
//        instance.run();
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of loop method, of class Client.
//     */
//    @Test
//    public void testLoop() throws Exception {
//        System.out.println("loop");
//        CLFormatter helper = null;
//        BufferedReader reader = null;
//        Client instance = new Client();
//        instance.loop(helper, reader);
//        fail("The test case is a prototype.");
//    }
//    
}
