package an5.solve;

import java.util.List; 

public class an5AndGoal extends an5GoalTree {
  an5SearchControl ctrlAndStats;
  List<an5Template> template;
  
  public an5AndGoal(List<an5Template> t, an5SearchControl c) {
	template = t;
	ctrlAndStats = c;
  }
  public int seed() {
	int res = 0;
    return res;
  }
  public int status() {
	int res = an5SearchControl.SearchResult.UNDEFINED;
    return res;
  }
  public int[] gauge() {
	return new int[]{0,1};
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
  public void release() {
  }
}
