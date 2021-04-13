/**
 @what Solver found solution flagging class
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

import java.io.PrintStream;

public class an5FoundGoal extends an5GoalTree {
  public an5Template goal;
  an5FoundGoal(an5Template g) {
    goal = g;
  }
  public int status() {
    return an5SearchControl.SearchResult.FOUND;
  }
  public int[] gauge(int type) {
    return new int[]{0,1};
  }
  public an5GoalTree executeNext() {
    return null;
  }
  public void suspend() {
  }
  public void resume() {
  }
  public void release() {
  }
  public String[] why() {
    return new String[]{"Need to: " + this.getClass().getName(),
    		            ""};
  }
  public String[] how() {
	return new String[]{"By: " + this.getClass().getName(),
                        ""};
  }
  public int goalQueueSize() {
	return 0;
  }
  public int getDepth() {
	return goal.getDepth();
  }
  public String templateType() {
	return new String("parent -> " + goal.getClass().toString());
  }
  public void dumpJSON(PrintStream ps, int strategy) {
	an5Template par = null;
	int[] score;
	ps.println("{");
	ps.println("  \"an5FoundGoal\": {");
	ps.println("    \"this\": " + this + ",");
	if (((an5SearchControl.SearchOptions.SCORE |
	      an5SearchControl.SearchOptions.COST) & strategy) > 0) {
	  score = goal.gauge(strategy);
	  ps.println("    \"gauge\": " + score[0] + "/" + score[1]);
	}
	ps.println("    \"depth\": " + goal.getDepth() + ",");
	ps.println("    \"parent\": {");
	par = goal.parent;
	while (par != null) {
	  ps.print("      \"" + par.getClass().getSimpleName() + "\": \"" + par);
	  if (((an5SearchControl.SearchOptions.SCORE |
		    an5SearchControl.SearchOptions.COST) & strategy) > 0) {
		score = par.gauge(strategy);
		ps.println();
	    ps.print("      \"gauge\": " + score[0] + "/" + score[1]);
	  }
	  par = par.parent;
	  if (par != null)
	    ps.println(",");
	}
	ps.println();
	ps.println("    }");	
	ps.println("  }");
	ps.println("}");    
  }
}
