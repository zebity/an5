package an5.solve;

public class an5FoundGoal extends an5GoalTree {
  public an5Template goal;
  an5FoundGoal(an5Template g) {
    goal = g;
  }
  public int seed() {
    return 0;
  }
  public int status() {
    return an5SearchControl.SearchResult.FOUND;
  }
  public int[] gauge(int type) {
    return new int[]{0,1};
  }
  public an5GoalTree getNextGoal() {
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
}
