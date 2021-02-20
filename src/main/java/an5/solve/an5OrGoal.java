package an5.solve;

import java.util.List;

import an5.model.*;

public class an5OrGoal extends an5GoalTree {
  an5SearchStats stats;
  List<an5Template> template;
  public an5OrGoal(List<an5Template> t, an5SearchStats s) {
    template = t;
    stats = s;
  }
  public an5OrGoal(List<an5GoalTree> tree, an5SearchStats st, boolean andTree) {
    stats = st;
  }
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
  public an5GoalTree getNextGoal(an5SearchStats st) {
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
