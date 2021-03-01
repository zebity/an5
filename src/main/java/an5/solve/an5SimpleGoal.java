package an5.solve;

public class an5SimpleGoal extends an5GoalTree {
  an5Template template;
  an5SearchControl ctrl;
  an5SimpleGoal(an5Template t, an5SearchControl c) {
	 template = t;
	 ctrl = c;
  }
  public int seed() {
    return template.seedGoal();
  }
  public int status() {
    return template.status();
  }
  public int[] gauge() {
    return template.gauge();
  }
  public an5GoalTree getNextGoal() {
    return template.getNextGoal(ctrl);
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
}
