package an5.solve;

import java.util.LinkedList;
import java.util.List;

public class an5OrGoal extends an5GoalTree {
  an5SearchControl ctrlAndStats;
  public an5SearchQueue<an5GoalTree> queue = new an5SearchQueue<>();
  public List<an5GoalTree> success = new LinkedList<>();
  int status = an5SearchControl.SearchResult.UNDEFINED;
  
  public an5OrGoal(List<an5GoalTree> tl, an5SearchControl s) {
    ctrlAndStats = s;
    for (an5GoalTree t: tl) {
      queue.addToQueue(t, ctrlAndStats);	
    }
  }
  public int seed() {
	int res = 0;
	res = queue.get(0).seed();
	status = an5SearchControl.SearchResult.START;
    return res;
  }
  public int status() {
	return status;
  }
  public int[] gauge() {
	int[] max = new int[]{0,1};
    if ((ctrlAndStats.strategy & an5SearchControl.SearchOptions.SCORE) != 0) {
      max = queue.get(0).gauge();
    } else {
      /* traverse through list to get maximum */	
    }
	return max;
  }
  public an5GoalTree getNextGoal() {
    an5GoalTree res = null;
    if (queue.size() > 0) {
      an5GoalTree hq = queue.remove(0);
      if (hq.status() == an5SearchControl.SearchResult.FOUND) {
        success.add(hq);
        if (queue.size() > 0) {
          hq = queue.remove(0);
          res = hq.getNextGoal();
        }
      } else {
        res = hq.getNextGoal();
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
