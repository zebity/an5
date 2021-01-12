// package an5.test;

import java.util.Scanner;
import java.io.IOException;
import an5.an5Model;

public class modelANetwork5 {
  public static void main(String[] args){
    Scanner s = new Scanner(System.in);
    System.out.println("Enter file to load: ");
    String str = s.next();
    s.close();

    an5Model model;
	try {
		model = an5Model.create(new String("test"), str);
	    model.info(); 
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}       
  }
  
}
