/**
 @what Generic solver OR node goal tree
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
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
    queue.setStategy(an5SearchControl.SearchOptions.BREADTH);
    for (an5GoalTree t: tl) {
      queue.addToQueue(t);	
    }
  }
  public an5OrGoal(an5GoalTree simpleT, an5SearchControl s) {
    ctrlAndStats = s;
    queue.setStategy(an5SearchControl.SearchOptions.BREADTH);
  }
  public an5GoalTree popQueue() {
    an5GoalTree res = queue.removeHead();
    return res;
  }
  public int status() {
	return status;
  }
  public int[] gauge(int type) {
	int[] max = new int[]{0,1};
    /* if (((ctrlAndStats.strategy & an5SearchControl.SearchOptions.SCORE) |
    	 (ctrlAndStats.strategy & an5SearchControl.SearchOptions.COST)) != 0) {
      max = new int[]{queue.max[0], queue.max[1]};
    } */
	return max;
  }
  public an5GoalTree executeNext() {
    an5GoalTree res = null;
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
	int dp = 1;
	return dp;
  }
  public String templateType() {
	return new String("N/A");
  }
}
