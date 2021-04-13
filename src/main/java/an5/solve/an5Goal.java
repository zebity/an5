/**
 @what The generic Solver starter class.
         Solver has two layers:
           Generic - the general purpose AND/OR Tree constraint solver, providing control
           Network - the network domain template classes, providing network domain logic

 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

import java.util.LinkedList;
import java.util.List;

import an5.an5Logging;

public class an5Goal extends an5GoalTree {
  an5SimpleGoal goal;
  an5SearchQueue<an5GoalTree> queue = new an5SearchQueue<>();
  public List<an5GoalTree> found = new LinkedList<>();
  an5SearchControl ctrlAndStats;
  int endScore,
      status = an5SearchControl.SearchResult.UNDEFINED;
  an5Logging log = new an5Logging(4, 4);
  boolean DEBUG = false;
  
  public an5Goal(an5Template targ, an5SearchControl st) {
    goal = new an5SimpleGoal(targ, st);
    ctrlAndStats = st;
    queue.setStategy(ctrlAndStats.strategy);
  }
  public int solve() {
    int res = 0,
    	loops = 1,
    	depth = 0;
    an5GoalTree next = this;
    boolean stopSearch = false;

    ctrlAndStats.stats.startTimer();
    
    queue.addToQueue(next, ctrlAndStats.stats);
    while (! stopSearch) {;
      res = next.status();
  	  log.DBG(6, "AN5:an5Goal.solve: next - '" + next.getClass().toString()
  			      + "' queue size: " + next.goalQueueSize() + " status: " + ctrlAndStats.resultString(res)
  			      + " template: " + next.templateType());
  	  
      if (next instanceof an5FoundGoal) {
    	found.add(next);
        an5FoundGoal fg = (an5FoundGoal)next;
        fg.dumpJSON(System.out, ctrlAndStats.strategy);
      }  else if (next instanceof an5OrGoal) {
      	an5OrGoal orT = (an5OrGoal)next;
      	an5GoalTree head = orT.popQueue();
        while (head != null) {
          queue.addToQueue(head, ctrlAndStats.stats);
          head = orT.popQueue();
        }
      } else if (next instanceof an5SimpleGoal ||
    		     next instanceof an5Goal) {
        next = next.executeNext();
        queue.addHead(next, ctrlAndStats.stats);
      }
      depth = next.getDepth();
      if (DEBUG)
        queue.dump(System.out, true);
  	  ctrlAndStats.stats.updateStats(res, loops, depth);

      if ((ctrlAndStats.strategy & an5SearchControl.SearchOptions.OPTIMAL) != 0) {
    	stopSearch = queue.isEmpty();
    	log.DBG(6, "AN5:an5Goal.solve: Option = OPTIMAL - stop - '" + stopSearch + "'");
      } else {
    	stopSearch = res == an5SearchControl.SearchResult.FOUND ||
    			       queue.isEmpty();
    	log.DBG(6, "AN5:an5Goal.solve: Option = FOUND - stop - '" + stopSearch + "' res = "
    			   + ctrlAndStats.resultString(res));

      }
      next = queueDispatch();
      loops++;
    }
    ctrlAndStats.stats.stopTimer();
    return res;
  }
  public int status() {
	return goal.status();
  }
  public an5GoalTree executeNext() {
    return goal.executeNext();
  }
  public an5GoalTree queueDispatch() {
	an5GoalTree hq = null;
	if (queue.size() > 0 ) {
      hq = queue.removeHead();
	}
	return hq;
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
  public int[] gauge(int type) {
	return goal.gauge(type);
  }
  public void release() {
  }
  public int goalQueueSize() {
	return queue.size();
  }
  public int getDepth() {
	return ctrlAndStats.stats.maxDepth;
  }
  public String templateType() {
	return new String("N/A");
  }
}
