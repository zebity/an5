/**
 @what Generic solver OR node goal tree
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

import java.util.LinkedList;
import java.util.List;

public class an5OrGoal extends an5GoalTree {
  an5SearchControl ctrlAndStats;
  an5GoalTree active = null;
  public an5SearchQueue<an5GoalTree> queue = new an5SearchQueue<>();
  public List<an5GoalTree> success = new LinkedList<>();
  int status = an5SearchControl.SearchResult.UNDEFINED;
  
  public an5OrGoal(List<an5GoalTree> tl, an5SearchControl s) {
    ctrlAndStats = s;
    for (an5GoalTree t: tl) {
      queue.addToQueue(t, ctrlAndStats);	
    }
  }
  public an5OrGoal(an5GoalTree simpleT, an5SearchControl s) {
    ctrlAndStats = s;
    active = simpleT;
    status = active.status();
  }
  public an5GoalTree popQueue() {
    an5GoalTree res = queue.removeHead();
    return res;
  }
  public int seed() {
	int res = 0;
	if (active != null) {
	  res = active.seed();
	  status = active.status();
	}
    return res;
  }
  public int status() {
	return status;
  }
  public int[] gauge(int type) {
	int[] max = new int[]{0,1};
    if (((ctrlAndStats.strategy & an5SearchControl.SearchOptions.SCORE) |
    	 (ctrlAndStats.strategy & an5SearchControl.SearchOptions.COST)) != 0) {
      if (active != null) {
        max = active.gauge(type);
      }
    } else {
      /* traverse through list to get maximum */	
    }
	return max;
  }
  public an5GoalTree getNextGoal() {
    an5GoalTree res = null;
    if (active != null) {
      if (active.status() == an5SearchControl.SearchResult.FOUND) {
        success.add(active);
        if (queue.size() > 0) {
          active = queue.removeHead();
          // res = hq.getNextGoal();
          res = active;
        }
      } else {
        // res = hq.getNextGoal();
    	res = active.getNextGoal();
      }
    }
    else {
      active = queue.removeHead();
      res = active;
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
  public int goalQueueSize() {
	return queue.size();
  }
  public int getDepth() {
	int dp = 0;
	if (active != null) {
	  dp = active.getDepth();
	}
	return dp;
  }
  public String templateType() {
	return new String("N/A");
  }
  public an5FoundGoal getFoundGoal() {
    an5FoundGoal res = null;
    
    if (status == an5SearchControl.SearchResult.FOUND) {
      if (active instanceof an5FoundGoal) {
    	res = (an5FoundGoal)active;
      }
    }
    return res;
  }
}
