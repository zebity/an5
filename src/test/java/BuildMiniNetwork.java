// package an5.test;

// import java.util.Scanner;
import java.io.IOException;

import an5.model.*;
import an5.solve.*;
import an5.generic.dctypes.*;



public class BuildMiniNetwork {
  public static void main(String[] args){
	// System.out.println(args[0]);  
    // Scanner s = new Scanner(System.in);
    // System.out.println("Enter file to load: ");
    // String str = s.next();
    // s.close();

    an5Network Result = new an5Network();
    
    String[][] swDef = {{"name", "simple-switch"},
    		            {"port", "[24]", "p%I"}};

    AN5CL_switch sw1 = new AN5CL_switch(new an5ConstructArguments(swDef));
   System.out.println("switch reports as" + sw1.toString());
  }

}
