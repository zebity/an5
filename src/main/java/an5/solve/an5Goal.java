package an5.solve; 

public class an5Goal extends an5GoalTree {
  an5Template template;
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
        case an5SearchControl.SearchResult.SUSPENDED:
        default: break;
      }
      
      next = next.getNextGoal(ctrlAndStats);
      addToQueue(next);
      /* queueDispatch(); */
      
      if (ctrlAndStats.strategy == an5SearchControl.SearchOptions.OPTIMAL) {
    	stopSearch = ctrlAndStats.queue.isEmpty();  
      } else {
    	stopSearch = res == an5SearchControl.SearchResult.FOUND ||
    			       ctrlAndStats.queue.isEmpty();
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
  public int cost() {
	return 0;
  }
  public an5GoalTree getNextGoal(an5SearchControl ctrl) {
	an5GoalTree res = null;
	ctrl.queue.remove(0);
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
    if ((ctrlAndStats.strategy & an5SearchControl.SearchOptions.BREADTH) != 0) {
      ctrlAndStats.queue.add(t);
    } else if ((ctrlAndStats.strategy & an5SearchControl.SearchOptions.DEPTH) != 0) {
      ctrlAndStats.queue.add(0, t);   	
    } else if ((ctrlAndStats.strategy & an5SearchControl.SearchOptions.SCORE) != 0) {
      int score = t.score();
      if (ctrlAndStats.queue.isEmpty()) {
        ctrlAndStats.queue.add(t);
        ctrlAndStats.max = ctrlAndStats.min = score;
      } else if (score <= ctrlAndStats.min) {
    	ctrlAndStats.queue.add(t);
    	ctrlAndStats.min = score;
      } else if (score >= ctrlAndStats.max) {
      	ctrlAndStats.queue.add(0, t);
      	ctrlAndStats.max = score;    	  
      } else {
    	for (int i = 1; i < ctrlAndStats.queue.size(); i++) {
    	  if (ctrlAndStats.queue.get(i).score() >= score) {
    	    ctrlAndStats.queue.add(i, t);
    	    i = ctrlAndStats.queue.size();
    	  }
    	}
      }
    } else if ((ctrlAndStats.strategy & an5SearchControl.SearchOptions.COST) != 0) {
      int cost = t.cost();
      if (ctrlAndStats.queue.isEmpty()) {
        ctrlAndStats.queue.add(t);
        ctrlAndStats.max = ctrlAndStats.min = cost;
      } else if (cost <= ctrlAndStats.min) {
    	ctrlAndStats.queue.add(0, t);
    	ctrlAndStats.min = cost;
      } else if (cost >= ctrlAndStats.max) {
      	ctrlAndStats.queue.add(t);
      	ctrlAndStats.max = cost;    	  
      } else {
    	for (int i = 1; i < ctrlAndStats.queue.size(); i++) {
    	  if (ctrlAndStats.queue.get(i).cost() >= cost) {
    	    ctrlAndStats.queue.add(i, t);
    	    i = ctrlAndStats.queue.size();
    	  }
    	}
      }
    }
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
  public int score() {
    int  max = template.score();
    return max;
  }
  public void release() {
  }
}
