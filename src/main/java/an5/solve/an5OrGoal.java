package an5.solve;

import java.util.List;

import an5.model.*;

public class an5OrGoal extends an5GoalTree {
  an5SearchControl ctrlAndStats;
  List<an5Template> template;
  public an5OrGoal(List<an5Template> t, an5SearchControl s) {
    template = t;
    ctrlAndStats = s;
  }
  public an5OrGoal(List<an5GoalTree> tree, an5SearchControl st, boolean andTree) {
    ctrlAndStats = st;
  }
  public int seed() {
	int res = 0;
    return res;
  }
  public int solve() {
  	return 0;
  }
  public int status() {
	return an5SearchControl.SearchResult.UNDEFINED;
  }
  public int score() {
	int max = 0;
    if ((ctrlAndStats.strategy & an5SearchControl.SearchOptions.SCORE) != 0) {
      max = template.get(0).score();
    } else {
      /* traverse through list to get maximum */	
    }
	return max;
  }
  public int cost() {
	return 0;
  }
  public an5GoalTree getNextGoal(an5SearchControl ctrl) {
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
  public void release() {
  }
}
