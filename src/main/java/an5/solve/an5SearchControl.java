/**
 @what Solver search control options and statistics collection
         Search Option include: Depth First, Breadth First, Bound ...
         
 @reference See Patrick Winston - "Artifical Intelligence [3rd Ed]" for initial overview of AI search
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

import java.util.HashMap;
import java.util.Map;

import an5.model.*;

public class an5SearchControl {
  public static class SearchOptions { static final int DEPTH = 0x001, BREADTH = 0x002, BOUND = 0x004, SCORE = 0x008,
		                                               COST = 0x010, REMOVE_LOCAL_EQUIVALENTS = 0x020, BIND_ALL = 0x040, BIND_UNIQUE = 0x080,
		                                               THREAD = 0x100, TIMEBOX = 0x200, OPTIMAL = 0x400;}
  public static class SearchResult {static final int UNDEFINED = -1, START = 0, SOLVING = 1, SUSPENDED = 2, FAILED = 3,
		                                             BOUND = 4, VISITED = 5, FOUND = 6, FOUND_ALL = 7;}
  public static class BuildStrategy { static final int SINGLE_NET_ADD = 1, MULTI_NET_JOIN = 2; }
  public static String[] ResultString = { "UNDEFINED", "START", "SOLVING", "SUSPENDED", "FAILED", "BOUND",
		                                  "VISITED", "FOUND", "FOUND_ALL"};
  an5SearchStats stats = new an5SearchStats();
  Map<String, an5Object> visited = new HashMap<>();
  int strategy = (an5SearchControl.SearchOptions.COST | an5SearchControl.SearchOptions.BIND_UNIQUE),
	  networkBuildStrategy = an5SearchControl.BuildStrategy.SINGLE_NET_ADD;
  int bound;
  public String resultString(int res) {
	String resStr = null;
	int i = res + 1;
	if (i >= 0 && i < ResultString.length)
	  resStr = ResultString[i];
    return resStr;
  }
}
