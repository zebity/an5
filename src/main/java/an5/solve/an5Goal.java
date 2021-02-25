package an5.solve; 

public class an5Goal extends an5GoalTree {
  an5Template template;
  an5SearchQueue<an5GoalTree> queue = new an5SearchQueue<>();
  an5SearchControl ctrlAndStats;
  int endScore,
      status = an5SearchControl.SearchResult.UNDEFINED;
  
  public an5Goal(an5Template targ, an5SearchControl st) {
    template = targ;
    ctrlAndStats = st;
  }
  public int solve() {
    int res = 0;
    an5GoalTree next = this;
    boolean stopSearch = false;
    
    endScore = seed();
    
    addToQueue(next);
    while (! stopSearch) {
      res = next.status();
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
      
      next = next.getNextGoal(ctrlAndStats);
      addToQueue(next);
      /* queueDispatch(); */
      
      if (ctrlAndStats.strategy == an5SearchControl.SearchOptions.OPTIMAL) {
    	stopSearch = queue.isEmpty();  
      } else {
    	stopSearch = res == an5SearchControl.SearchResult.FOUND ||
    			       queue.isEmpty();
      }
    }
    return res;
  }
  public int seed() {
	int res = 0;
    if ((template instanceof an5CreateNetwork) ||
        (template instanceof an5JoinNetwork) ||
        (template instanceof an5ConnectNetworks)) {
      res = template.seedGoal();    	
    }
    status = an5SearchControl.SearchResult.START;
    return res;
  }
  public int status() {
	int res = an5SearchControl.SearchResult.UNDEFINED;
	if ((template instanceof an5CreateNetwork) ||
		(template instanceof an5JoinNetwork) ||
		(template instanceof an5ConnectNetworks)) {
      res = template.status();    	
	}
	return res;
  }
  public an5GoalTree getNextGoal(an5SearchControl ctrl) {
	an5GoalTree res = null;
	queue.remove(0);
	if ((template instanceof an5CreateNetwork) ||
	    (template instanceof an5JoinNetwork) ||
	    (template instanceof an5ConnectNetworks)) {
	  res = template.getNextGoal(ctrl);
	}
    return res;
  }
  /* public void queueDispatch() {
	int bound = -1;
    an5GoalTree next = ctrlAndStats.queue.remove(0);
    
    if ((ctrlAndStats.strategy & an5SearchControl.SearchOptions.BOUND) != 0) {
      bound = ctrlAndStats.bound;
    }
    
  } */
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
	int[] g = template.gauge();
    return g;
  }
  public void release() {
  }
}
