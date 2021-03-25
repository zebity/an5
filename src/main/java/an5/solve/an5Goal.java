package an5.solve;

import an5.an5Logging;

public class an5Goal extends an5GoalTree {
  an5SimpleGoal goal;
  an5SearchQueue<an5GoalTree> queue = new an5SearchQueue<>();
  an5SearchControl ctrlAndStats;
  int endScore,
      status = an5SearchControl.SearchResult.UNDEFINED;
  an5Logging log = new an5Logging(7, 7);
  
  public an5Goal(an5Template targ, an5SearchControl st) {
    goal = new an5SimpleGoal(targ, st);
    ctrlAndStats = st;
  }
  public int solve() {
    int res = 0;
    an5GoalTree next = this;
    boolean stopSearch = false;
    
    endScore = seed();
    
    addToQueue(next);
    while (! stopSearch) {
      next = queueDispatch();	
      res = next.status();
  	  log.DBG(6, "<log.INFO>:AN5:an5Goal.solve: next - '" + next.getClass().toString()
  			      + "' queue size: " + next.goalQueueSize() + " status: " + res
  			      + " template: " + next.templateType());

      switch (res) {
        case an5SearchControl.SearchResult.SOLVING:
        case an5SearchControl.SearchResult.START:
    	       ctrlAndStats.stats.noIntermediate++;
               break;
        case an5SearchControl.SearchResult.VISITED:
    	       ctrlAndStats.stats.noRevisits++;
               break;
        case an5SearchControl.SearchResult.FAILED:
    	       ctrlAndStats.stats.noFailed++;
               break;
        case an5SearchControl.SearchResult.BOUND:
               ctrlAndStats.stats.noBounded++;               
        case an5SearchControl.SearchResult.FOUND:
    	       ctrlAndStats.stats.noFound++;
               break;
        case an5SearchControl.SearchResult.UNDEFINED:
               ctrlAndStats.stats.noUnseeded++;
               next.seed();
               break;
        case an5SearchControl.SearchResult.SUSPENDED:
        default: break;
      }
      
      next = next.getNextGoal();
      addToQueue(next);

      
      if ((ctrlAndStats.strategy & an5SearchControl.SearchOptions.OPTIMAL) != 0) {
    	stopSearch = queue.isEmpty();
    	log.DBG(6, "<log.INFO>:AN5:an5Goal.solve: OPTIMAL - stop - '" + stopSearch + "'");
      } else {
    	stopSearch = res == an5SearchControl.SearchResult.FOUND ||
    			       queue.isEmpty();
    	log.DBG(6, "<log.INFO>:AN5:an5Goal.solve: FOUND - stop - '" + stopSearch + "' res = "
    			   + res);

      }
    }
    return res;
  }
  public int seed() {
	int res = goal.seed();
    status = an5SearchControl.SearchResult.START;
    return res;
  }
  public int status() {
	return goal.status();
  }
  public an5GoalTree getNextGoal() {
    return goal.getNextGoal();
  }
  public an5GoalTree queueDispatch() {
    return queue.remove(0);
  }
  public void addToQueue(an5GoalTree t) {
    queue.addToQueue(t, ctrlAndStats);
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
  public int[] gauge() {
	return goal.gauge();
  }
  public void release() {
  }
  public int goalQueueSize() {
	return queue.size();
  }
  public String templateType() {
	return new String("N/A");
  }
}
