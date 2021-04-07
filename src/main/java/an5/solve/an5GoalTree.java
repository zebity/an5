/**
 @what The generic AND/OR solver interface
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

abstract public class an5GoalTree extends an5SearchGauge {  
  abstract public int seed();
  abstract public int status();
  abstract public an5GoalTree getNextGoal();
  abstract public void suspend();
  abstract public void resume();
  abstract public void release();
  abstract public String[] why();
  abstract public String[] how();
  abstract public int goalQueueSize();
  abstract public int getDepth();
  abstract public String templateType();
  abstract public an5FoundGoal getFoundGoal();
}
