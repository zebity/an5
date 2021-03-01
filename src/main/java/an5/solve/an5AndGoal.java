package an5.solve;

import java.util.LinkedList;
import java.util.List; 

public class an5AndGoal extends an5GoalTree {
  an5SearchControl ctrlAndStats;
  public an5SearchQueue<an5GoalTree> queue = new an5SearchQueue<>();
  public List<an5GoalTree> success = new LinkedList<>();
  int status = an5SearchControl.SearchResult.UNDEFINED;
  
  public an5AndGoal(List<an5GoalTree> tl, an5SearchControl c) {
	ctrlAndStats = c;
	for (an5GoalTree t: tl) {
	  queue.addToQueue(t, ctrlAndStats);	
	}
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
