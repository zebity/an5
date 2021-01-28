/**
 an5 Compiler
 Generates java code
 */

package an5;


// import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class an5Compiler {
  public static void main(String[] args){
	an5Global glob = new an5Global();
	List<String> srcs = new ArrayList<>();
	Map<String, String> flags = new HashMap<>();
	
	System.out.println(args[0]);
	
	/*
    Scanner s = new Scanner(System.in);
    System.out.println("Enter file to load: ");
    String str = s.next();
    s.close();
    */

	glob.parseArgs(args, flags, srcs);
    an5Model model;
    
    for (String src: srcs) {
    	
      String dir = flags.get("gendir");
      if (dir == null) {
        dir = src.substring(0, src.lastIndexOf(glob.pathSeperator)+1);
      }
	  try {
		model = an5Model.create(glob, src, dir);
		model.compile();
	    model.info();
	  } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
    }
  }

}
