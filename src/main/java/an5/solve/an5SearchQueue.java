package an5.solve;

import java.util.LinkedList;
import java.util.List;

public class an5SearchQueue<TQ extends an5SearchGauge> {
  public class queueNode {
    TQ goal;
    boolean seeded = false;
    int[] score = new int[]{0,0};
    int status = an5SearchControl.SearchResult.UNDEFINED;
    public queueNode(TQ t) {
      goal = t;
    }
  }
  List<queueNode> queue = new LinkedList<>();
  int[] min,
        max; 
  public an5SearchQueue() {
  }
  public boolean isEmpty() {
    return queue.isEmpty();
  }
  public boolean add(TQ t) {
	queueNode n = new queueNode(t);
    return queue.add(n);
  }
  public void add(int i, TQ t) {
	queueNode n = new queueNode(t);
    queue.add(i, n);
  }
  public TQ get(int i) {
    return queue.get(i).goal;
  }
  public TQ remove(int i) {
	TQ r = null;
    queueNode n = queue.remove(i);
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
  public void addToQueue(TQ t,an5SearchControl ctrl) {
    if ((ctrl.strategy & an5SearchControl.SearchOptions.BREADTH) != 0) {
      add(t);
    } else if ((ctrl.strategy & an5SearchControl.SearchOptions.DEPTH) != 0) {
      add(0, t);   	
    } else if ((ctrl.strategy & an5SearchControl.SearchOptions.SCORE) != 0 ||
               (ctrl.strategy & an5SearchControl.SearchOptions.COST) != 0) {
      int[] score = t.gauge();
      if (isEmpty()) {
        add(t);
        max = min = score;
      } else if ((score[0] *  score[1] * min[1]) - (min[0] *  score[1] * min[1]) <= 0) {
    	add(t);
    	min = score;
      } else if ((score[0] *  score[1] * max[1]) - (max[0] *  score[1] * max[1]) >= 0) {
      	add(0, t);
      	max = score;
      } else {
    	for (int i = 1; i < size(); i++) {
    	  int[] ndScore = get(i).gauge();
    	  if ((ndScore[0] *  score[1] * ndScore[1]) - (score[0] *  score[1] * ndScore[1]) >= 0) {
    	    add(i, t);
    	    i = size();
    	  }
    	}
      }
    }
  }
  public int[] gauge() {
    int[] mx = new int[]{0,1}; /* template.score(); */
    return mx;
  }
  public void release() {
  }
}
