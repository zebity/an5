package an5.solve;

import an5.model.*; 

abstract public class an5GoalTree {
  public static class SearchOptions { static final int DEPTH = 0x01, BREADTH = 0x02, BOUND = 0x04, SCORE = 0x08, COST = 0x10, OPTIMAL = 0x20;};
  public static class SearchResult {static final int UNDEFINED = -1, START = 0, SOLVING = 1, SUSPENDED = 2, FAILED = 3, VISITED = 4, FOUND = 5;};
  
  abstract public int solve();
  abstract public int seed();
  abstract public int status();
  abstract public an5GoalTree getNextGoal();
  abstract public void suspend();
  abstract public void resume();
  abstract public String[] why();
  abstract public String[] how();
}
