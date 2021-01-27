// package an5.test;

// import java.util.Scanner;
import java.io.IOException;

import an5.model.*;
import an5.solve.*;
import proto.AN5CL_switch;

public class BuildMiniNetwork {
  public static void main(String[] args){
	System.out.println(args[0]);  
    // Scanner s = new Scanner(System.in);
    // System.out.println("Enter file to load: ");
    // String str = s.next();
    // s.close();

    an5Network Result = new an5Network();

    AN5CL_switch sw1 = new AN5CL_switch("simple-switch", 8);
  }

}
