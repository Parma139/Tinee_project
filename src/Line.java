
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author parma
 */
public class Line {

   String linesetup(String[] arg){
    
      
     
          String line = Arrays.stream(arg).collect(Collectors.joining(" "));
          return  line;

    }
    
}
