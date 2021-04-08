/**
 @what Provide wrapper between generic AND/OR solver an the network domain template classes
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

public class an5SimpleGoal extends an5GoalTree {
  an5Template template;
  an5SearchControl ctrl;
  an5SimpleGoal(an5Template t, an5SearchControl c) {
	 template = t;
	 ctrl = c;
  }
  public an5GoalTree executeNext() {
    int res = template.status();
	ctrl.stats.updateStats(res);
	if (res == an5SearchControl.SearchResult.UNDEFINED) {
	  template.seedGoal();
	  res = template.status();
	  ctrl.stats.updateStats(res);
	}
	return template.getNextGoal(ctrl);
  }
  public int status() {
    return template.status();
  }
  public int[] gauge(int type) {
	/* if doing score / cost . then we need to seed before gauge */
	int res = template.status();
	if (res == an5SearchControl.SearchResult.UNDEFINED) {
	  ctrl.stats.updateStats(res);
	  template.seedGoal();
	}
    return template.gauge(type);
  }
  public void suspend() {
  }
  public void resume() {
  }
  public void release() {
    template = null;
    ctrl = null;
  }
  public String[] why() {
    return new String[]{"Need to: " + template.getClass().getName(),
    		            ""};
  }
  public String[] how() {
	return new String[]{"By: " + template.getClass().getName(),
                        ""};
  }
  public int goalQueueSize() {
	return 1;
  }
  public int getDepth() {
    return template.getDepth();
  }
  public String templateType() {
	return new String(template.getClass().toString());
  }
}
