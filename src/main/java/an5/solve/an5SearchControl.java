package an5.solve;

import java.util.HashMap;
import java.util.Map;

import an5.model.*;

public class an5SearchControl {
  public static class SearchOptions { static final int DEPTH = 0x01, BREADTH = 0x02, BOUND = 0x04, SCORE = 0x08, COST = 0x10, REMOVE_LOCAL_EQUIVALENTS = 0x20, THREAD = 0x40, OPTIMAL = 0x80;}
  public static class SearchResult {static final int UNDEFINED = -1, START = 0, SOLVING = 1, SUSPENDED = 2, FAILED = 3, BOUND = 4, VISITED = 5, FOUND = 6, FOUND_ALL = 7;}
  public static class BuildStrategy { static final int SINGLE_NET_ADD = 1, MULTI_NET_JOIN = 2; }
  public static String[] ResultString = { "UNDEFINED", "START", "SOLVING", "SUSPENDED", "FAILED", "BOUND",
		                                  "VISITED", "FOUND", "FOUND_ALL"};
  an5SearchStats stats = new an5SearchStats();
  Map<String, an5Object> visited = new HashMap<>();
  int strategy = an5SearchControl.SearchOptions.COST,
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
