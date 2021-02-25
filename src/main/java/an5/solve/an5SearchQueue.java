package an5.solve;

import java.util.LinkedList;
import java.util.List;

public class an5SearchQueue<TQ> {
  public class queueNode<T> {
    T goal;
    boolean seeded = false;
    int[] score = new int[]{0,0};
    int status = an5SearchControl.SearchResult.UNDEFINED;
    public queueNode(T t) {
      goal = t;
    }
  }
  List<queueNode<TQ>> queue = new LinkedList<>();  
  public an5SearchQueue() {
  }
  public boolean isEmpty() {
    return queue.isEmpty();
  }
  public boolean add(TQ t) {
	queueNode<TQ> n = new queueNode<>(t);
    return queue.add(n);
  }
  public void add(int i, TQ t) {
	queueNode<TQ> n = new queueNode<>(t);
    queue.add(i, n);
  }
  public TQ get(int i) {
    return queue.get(i).goal;
  }
  public TQ remove(int i) {
	TQ r = null;
    queueNode<TQ> n = queue.remove(i);
    if (n != null) {
      r = n.goal;
    }
	return r;
  }
  public int size() {
	return queue.size();
  }
  public int status() {
	int res = an5SearchControl.SearchResult.UNDEFINED;
	return res;
  }
  public int cost() {
	return 0;
  }
  public TQ getNextGoal(an5SearchControl ctrl) {
	TQ res = null;
    return res;
  }
  /* public void queueDispatch() {
	int bound = -1;
    an5GoalTree next = ctrlAndStats.queue.remove(0);
    
    if ((ctrlAndStats.strategy & an5SearchControl.SearchOptions.BOUND) != 0) {
      bound = ctrlAndStats.bound;
    }
    
  } */
  public void addToQueue(TQ t, an5SearchControl ctrl) {
    if ((ctrl.strategy & an5SearchControl.SearchOptions.BREADTH) != 0) {
      ctrl.queue.add(t);
    } else if ((ctrl.strategy & an5SearchControl.SearchOptions.DEPTH) != 0) {
      ctrl.queue.add(0, t);   	
    } else if ((ctrl.strategy & an5SearchControl.SearchOptions.SCORE) != 0) {
      int score = t.score();
      if (ctrl.queue.isEmpty()) {
        ctrl.queue.add(t);
        ctrl.max = ctrl.min = score;
      } else if (score <= ctrl.min) {
    	ctrl.queue.add(t);
    	ctrl.min = score;
      } else if (score >= ctrl.max) {
      	ctrl.queue.add(0, t);
      	ctrl.max = score;    	  
      } else {
    	for (int i = 1; i < ctrl.queue.size(); i++) {
    	  if (ctrl.queue.get(i).score() >= score) {
    	    ctrl.queue.add(i, t);
    	    i = ctrl.queue.size();
    	  }
    	}
      }
    } else if ((ctrl.strategy & an5SearchControl.SearchOptions.COST) != 0) {
      int cost = t.cost();
      if (ctrl.queue.isEmpty()) {
        ctrl.queue.add(t);
        ctrl.max = ctrl.min = cost;
      } else if (cost <= ctrl.min) {
    	ctrl.queue.add(0, t);
    	ctrl.min = cost;
      } else if (cost >= ctrl.max) {
      	ctrl.queue.add(t);
      	ctrl.max = cost;    	  
      } else {
    	for (int i = 1; i < ctrl.queue.size(); i++) {
    	  if (ctrl.queue.get(i).cost() >= cost) {
    	    ctrl.queue<>.add(i, t);
    	    i = ctrl.queue.size();
    	  }
    	}
      }
    }
  }
  public int score() {
    int  max = 0; /* template.score(); */
    return max;
  }
  public void release() {
  }
}
