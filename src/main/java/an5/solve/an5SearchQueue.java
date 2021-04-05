package an5.solve;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.fraction.Fraction;

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
  List<queueNode> removed = new LinkedList<>();
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
	TQ res = null;
	if (i >= 0 && i < queue.size()) {
      res = queue.get(i).goal;
    }
	return res;
  }
  public void addHead(TQ t) {
    add(0, t);
  }
  public TQ removeHead() {
	TQ r = null;
	if (queue != null && queue.size() > 0) {
      queueNode n = queue.remove(0);
      if (n != null) {
        r = n.goal;
        removed.add(n);
      }
	}
	return r;
  }
  public void purge() {
    if (! isEmpty()) {
      queue = new LinkedList<>();
      min = max = null;
    }
  }
  public int size() {
	return queue.size();
  }
  public int status() {
	int res = an5SearchControl.SearchResult.UNDEFINED;
	return res;
  }
  public void addToQueue(TQ t,an5SearchControl ctrl) {
	if (t != null) {
      if ((ctrl.strategy & an5SearchControl.SearchOptions.BREADTH) != 0) {
        add(t);
      } else if ((ctrl.strategy & an5SearchControl.SearchOptions.DEPTH) != 0) {
        add(0, t);   	
      } else if (((ctrl.strategy & an5SearchControl.SearchOptions.SCORE) |
                  (ctrl.strategy & an5SearchControl.SearchOptions.COST)) != 0) {
        int[] gs = t.gauge(ctrl.strategy);
        Fraction score = new Fraction(gs[0], gs[1]);
        if (isEmpty()) {
          add(t);
          max = min = gs;
        } else if (score.compareTo(new Fraction(min[0], min[1])) <= 0) {
          add(t);
          min = new int[]{score.getNumerator(), score.getDenominator()};
        } else if (score.compareTo(new Fraction(max[0], max[1])) >= 0) {
      	  add(0, t);
      	  max = new int[]{score.getNumerator(), score.getDenominator()};
        } else {
    	  /* note: should do insert by splitting in middle to allow for long queues */
    	  for (int i = 1; i < size(); i++) {
    	    int[] ngs = get(i).gauge(ctrl.strategy);
    	    Fraction ndScore = new Fraction(ngs[0], ngs[1]);
    	    if (ndScore.compareTo(score) >= 0) {
    	      add(i, t);
    	      i = size();
    	    }
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
