package an5.solve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import an5.model.*;
import an5.solve.an5GoalTree.SearchResult; 

public class an5OrGoal extends an5GoalTree {
  public int seed() {
	int res = 0;
    return res;
  }
  public int solve() {
  	return 0;
  }
  public int status() {
	return SearchResult.UNDEFINED;
  }
  public an5GoalTree getNextGoal() {
    return null;
  }
  public void suspend() {
  }
  public void resume() {
  }
  public String[] why() {
	return null;
  }
  public String[] how() {
	return null;
  }
}
