/**
 @what Solver search control options and statistics collection
         Search Option include: Depth First, Breadth First, Bound ...
         
 @reference See Patrick Winston - "Artifical Intelligence [3rd Ed]" for initial overview of AI search
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import an5.model.*;

public class an5SearchControl {
  public static class SearchOptions { public static final int DEPTH = 0x001, BREADTH = 0x002, BOUND = 0x004, SCORE = 0x008,
		                                               COST = 0x010, REMOVE_LOCAL_EQUIVALENTS = 0x020, BIND_ALL = 0x040, BIND_UNIQUE = 0x080,
		                                               THREAD = 0x100, TIMEBOX = 0x200, OPTIMAL = 0x400, KEEP_REMOVED = 0x800; }
  public static class SearchResult { public static final int UNDEFINED = -1, START = 0, SOLVING = 1, SUSPENDED = 2, FAILED = 3,
		                                             BOUND = 4, VISITED = 5, FOUND = 6, FOUND_ALL = 7;}
  public static class BuildStrategy { public static final int SINGLE_NET_ADD = 1, MULTI_NET_JOIN = 2; }
  public static String[] ResultString = { "UNDEFINED", "START", "SOLVING", "SUSPENDED", "FAILED", "BOUND",
		                                  "VISITED", "FOUND", "FOUND_ALL"};
  public static String[] OptionsString = { "DEPTH", "BREADTH", "BOUND", "SCORE",
          "COST", "REMOVE_LOCAL_EQUIVALENTS", "BIND_ALL", "BIND_UNIQUE",
          "THREAD", "TIMEBOX", "OPTIMAL", "KEEP_REMOVED" };

  an5SearchStats stats = new an5SearchStats();
  Map<String, an5Object> visited = new HashMap<>();
  int strategy = (an5SearchControl.SearchOptions.COST | an5SearchControl.SearchOptions.REMOVE_LOCAL_EQUIVALENTS |
		          an5SearchControl.SearchOptions.BIND_UNIQUE),
	  networkBuildStrategy = an5SearchControl.BuildStrategy.SINGLE_NET_ADD;
  int bound;
  public void turnOn(int flags) {
    strategy = strategy | flags;
  }
  public void turnOff(int flags) {
    strategy = strategy & (~flags);
  }
  public String resultString(int res) {
	String resStr = null;
	int i = res + 1;
	if (i >= 0 && i < ResultString.length)
	  resStr = ResultString[i];
    return resStr;
  }
  public static String strategyString(int flags) {
	String res = new String();
    StringBuilder str = new StringBuilder();
    
    if ((flags & SearchOptions.DEPTH) > 0)
      str.append("DEPTH" + "|");
    if ((flags & SearchOptions.BREADTH) > 0)    
      str.append("BREADTH" + "|");
    if ((flags & SearchOptions.BOUND) > 0)     
      str.append("BOUND" + "|");
    if ((flags & SearchOptions.SCORE) > 0)
      str.append("SCORE" + "|");
    if ((flags & SearchOptions.COST) > 0)     
      str.append("COST" + "|");
    if ((flags & SearchOptions.REMOVE_LOCAL_EQUIVALENTS) > 0)
      str.append("REMOVE_LOCAL_EQUIVALENTS" + "|");
    if ((flags & SearchOptions.BIND_ALL) > 0)
      str.append("BIND_ALL" + "|");
    if ((flags & SearchOptions.BIND_UNIQUE) > 0)
      str.append("BIND_UNIQUE" + "|");
    if ((flags & SearchOptions.THREAD) > 0)
      str.append("THREAD" + "|");
    if ((flags & SearchOptions.TIMEBOX) > 0)
      str.append("TIMEBOX" + "|");
    if ((flags & SearchOptions.OPTIMAL) > 0)
      str.append("OPTIMAL" + "|");
    if ((flags & SearchOptions.KEEP_REMOVED) > 0)
      str.append("KEEP_REMOVED" + "|");
    
    int len = str.length();
    if (len > 0) {
      res = str.toString().substring(0, len - 1);
    }
    return res;
  }
  public void dumpStats(PrintStream ps) {
    stats.dumpJSON(ps);
  }
}
