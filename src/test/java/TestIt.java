/**
 Test if stuff compiles
*/

import an5.generic.dctypes.*;
import an5.model.an5Interface;

public class TestIt {
  public static void main(String[] args) {
	Sleeper snoozy = new Sleeper();
	an5Interface obj = new AN5CL_ethernet_port_base_t();
	
    System.out.println("Test if compiles");
    System.out.println("Sleeper snoors ? :" + snoozy.snoors());
  }
}