/**
 @what Mikey Mouse logginng and debugging stuff
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
 */
package an5;

public class an5Logging {
  int verbosity = 1,
	  diags = 5;

  public an5Logging() {
  }
  public an5Logging(int v, int d) {
    verbosity = v;
    diags = d;
  }
  public void ERR(int level, String msg) {
    System.err.println(msg);
  }
  public void DBG(String msg) {
    switch (verbosity) {
	  case 7: System.out.println(msg);
      case 6:
	  case 5:
	  case 4:
	  case 3:
	  case 2:
	  case 1: 
	}
  }
  public void DBG(int level, String msg) {
	if (level <= diags) {
      switch (level) {
	    case 7:
        case 6: System.out.println(msg);
                break;
        case 5: 
	  }
	}
  }
}
