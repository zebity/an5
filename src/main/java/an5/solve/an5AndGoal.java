package an5.solve;

import java.util.List; 

public class an5AndGoal extends an5GoalTree {
  an5SearchControl ctrlAndStats;
  List<an5Template> template;
  
  public an5AndGoal(List<an5Template> t, an5SearchControl c) {
	template = t;
	ctrlAndStats = c;
  }
  /* public int solve() {
    return an5SearchControl.SearchResult.UNDEFINED;
  } */
  public int seed() {
	int res = 0;
    return res;
  }
  public int status() {
	int res = an5SearchControl.SearchResult.UNDEFINED;
    return res;
  }
  public int score() {
	return 0;
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
