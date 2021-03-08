package an5.solve;

import java.util.HashMap;
import java.util.Map;

import an5.model.*;

public class an5SearchControl {
  public static class SearchOptions { static final int DEPTH = 0x01, BREADTH = 0x02, BOUND = 0x04, SCORE = 0x08, COST = 0x10, REMOVE_LOCAL_EQUIVALENTS = 0x20, THREAD = 0x40, OPTIMAL = 0x80;};
  public static class SearchResult {static final int UNDEFINED = -1, START = 0, SOLVING = 1, SUSPENDED = 2, FAILED = 3, BOUND = 4, VISITED = 5, FOUND = 6, FOUND_ALL = 6;};
  public static class BuildStrategy { static final int SINGLE_NET_ADD = 1, MULTI_NET_JOIN = 2; }
  an5SearchStats stats = new an5SearchStats();
  Map<String, an5Object> visited = new HashMap<>();
  int strategy = an5SearchControl.SearchOptions.SCORE,
	  networkBuildStrategy = an5SearchControl.BuildStrategy.SINGLE_NET_ADD;
  int bound;
}
