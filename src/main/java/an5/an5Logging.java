/**
 @what Mikey Mouse logginng and debugging stuff
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
 */
package an5;

import java.util.logging.Level;
import java.util.logging.Logger;

public class an5Logging {
  private static final Logger LOGGER = Logger.getLogger(an5Logging.class.getName());
  int verbosity = 1,
	  diags = 5;

  public an5Logging() {
  }
  public an5Logging(int v, int d) {
	LOGGER.setLevel(Level.OFF);
    verbosity = v;
    diags = d;
  }
  public void ERR(int level, String msg) {
	LOGGER.warning(msg);
    // System.err.println(msg);
  }
  public void CRITICAL(String msg) {
	LOGGER.severe(msg);
    // System.err.println(msg);
  }
  public void DBG(String msg) {
	LOGGER.info(msg);
    /* switch (verbosity) {
	  case 7: System.out.println(msg);
      case 6:
	  case 5:
	  case 4:
	  case 3:
	  case 2:
	  case 1: 
	} */
  }
  public void DBG(int level, String msg) {
	LOGGER.info(msg);
	/* if (level <= diags) {
      switch (level) {
	    case 7:
        case 6: System.out.println(msg);
                break;
        case 5: 
	  }
	} */
  }
}
