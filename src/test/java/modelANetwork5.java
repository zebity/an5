// package an5.test;

import java.util.Scanner;
import java.io.InputStream;


public class modelANetwork5 {
  public static void main(String[] args){
    Scanner s = new Scanner(System.in);
    System.out.println("Enter file to load: ");
    String str = s.next();
    s.close();

    File an5File = new File(str);
    InputStream stream = new FileInputStream(an5File);
    an5Model model = new an5Model::create(stream);

    model.info();        
  }
  
}
