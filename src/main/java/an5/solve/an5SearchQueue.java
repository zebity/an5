/**
 @what Search solution queue.
 
 @note Need to refactor so that insert does not do start to tail walk ...
         should either do binary partition or insert + resort
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

import java.io.PrintStream;
import java.time.Duration;
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
  int strategy = an5SearchControl.SearchOptions.SCORE;
  int[] min,
        max;
  boolean keepRemoved = false;
  public an5SearchQueue() {
  }
  public void setStategy(int strat) {
    strategy = strat;
    
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
        if (keepRemoved) {
          removed.add(n);
        }
        if (((strategy & an5SearchControl.SearchOptions.SCORE) |
             (strategy & an5SearchControl.SearchOptions.COST)) != 0) {
          if (queue.size() > 0) {
            max = queue.get(0).goal.gauge(strategy);
          }
        }
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
  public void addToQueue(TQ t) {
	if (t != null) {
      if ((strategy & an5SearchControl.SearchOptions.BREADTH) != 0) {
        add(t);
      } else if ((strategy & an5SearchControl.SearchOptions.DEPTH) != 0) {
        add(0, t);   	
      } else if (((strategy & an5SearchControl.SearchOptions.SCORE) |
                  (strategy & an5SearchControl.SearchOptions.COST)) != 0) {
        int[] gs = t.gauge(strategy);
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
    	    int[] ngs = get(i).gauge(strategy);
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
  void dump(PrintStream ps, boolean summary) {
	ps.println("{");
	ps.println("  \"an5SearchQueue\": {");
	ps.println("    \"this\": " + this + ",");
	ps.println("    \"strategy\": \"" + an5SearchControl.strategyString(strategy) + "\",");
	ps.println("    \"size\": " + queue.size());
	if (((an5SearchControl.SearchOptions.SCORE |
		  an5SearchControl.SearchOptions.COST) & strategy) > 0) {
	  ps.println("    \"max\": " + max[0] + "/" + max[1]);
	  ps.println("    \"min\": " + min[0] + "/" + min[1]);
	  ps.println("    \"queue[max..min]\": {");
	  if (! summary) {
	    for (int i = 0; i < queue.size(); i++) {
		  int[] gauge = queue.get(i).goal.gauge(strategy);
	      ps.print("      \"queue[" + gauge[0] + "/" + gauge[1] + "]\": \"" + queue.get(i).goal.getClass().getSimpleName() + "\"");
	      if (i < queue.size() - 1) {
		    ps.println();
		  }
	    }
	  }
	} else {
	  ps.println("    \"queue[head..tail]\": {");
	  if (! summary) {
	    for (int i = 0; i < queue.size(); i++) {
	      ps.print("      \"queue[" + i + "]\": \"" + queue.get(i).goal.getClass().getSimpleName() + "\"");
	      if (i < queue.size() - 1) {
	        ps.println();
	      }
	    }
	  }
	}
	if (! summary && queue.size() > 0)
	  ps.println();
	ps.println("    }");	
	ps.println("  }");
	ps.println("}");
  }
}
