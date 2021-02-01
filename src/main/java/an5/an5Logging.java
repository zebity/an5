/**
 Mikey Mouse logginng and debugging stuff
 */
package an5;

public class an5Logging {
  int verbosity = 1,
	  diags = 5;

  void ERR(int level, String msg) {
    System.err.println(msg);
  }
  void DBG(String msg) {
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
  void DBG(int level, String msg) {
    switch (level) {
	  case 7:
      case 6: System.out.println(msg);
              break;
      case 5: 
	}
  }
}
