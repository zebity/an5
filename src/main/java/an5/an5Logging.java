/**
 @what Mikey Mouse logginng and debugging stuff
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
 */
package an5;

import java.util.logging.Level;
import java.util.logging.Logger;

public class an5Logging {
  public static class SyslogLevel { public static final int emerg = 0, alert = 1, crit = 2, err = 3, warning = 4, notice = 5, info = 6, debug = 7; }
  public static class LogVerbose { public static final int OFF = 0, ON = 1, VERBOSE = 2; }
  private static final Logger LOGGER = Logger.getLogger(an5Logging.class.getName());
  int verbosity = 1,
	  diags = 5;

  public an5Logging() {
	LOGGER.setLevel(Level.OFF);
	LOGGER.setLevel(Level.SEVERE);
  }
  public an5Logging(int v, int d) {
	LOGGER.setLevel(Level.OFF);
	LOGGER.setLevel(Level.SEVERE);
	if (v > 0) {
	  LOGGER.setLevel(Level.SEVERE);
	  switch (d) {
	    case 7: if (v >= 2) {
		          LOGGER.setLevel(Level.FINEST);
		        } else {
		          LOGGER.setLevel(Level.FINE);
		        }
		        break;
	    case 6: LOGGER.setLevel(Level.INFO);
	    case 5:
	    case 4:
	    case 3: LOGGER.setLevel(Level.WARNING);
	            break;
	    case 2:
	    case 1:
	    case 0: LOGGER.setLevel(Level.SEVERE);
	            break;
	  }
	}
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
