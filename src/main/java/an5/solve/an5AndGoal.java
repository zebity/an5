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
	if (queue.size() > 0) {
	  res = queue.get(0).seed();
	}
	status = an5SearchControl.SearchResult.START;
    return res;
  }
  public int status() {
	int i,
	    res = status;
	int worst = an5SearchControl.SearchResult.FOUND;
	for (i = 0; i < queue.size(); i++) {
      worst = Integer.min(worst, queue.get(i).status());
	}
	if (i > 0) {
	  res = worst;
	  status = res;
	}
    return res;
  }
  public int[] gauge() {
	return new int[]{0,1};
  }
  public an5GoalTree getNextGoal() {
	an5GoalTree res = null;
	if (status == an5SearchControl.SearchResult.FAILED) {
	  if (queue.size() > 0) {
	    queue.purge();
	  }
	} else {
	  if (queue.size() > 0) {
		res = queue.get(0).getNextGoal();
	  }
	}
    return res;
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
