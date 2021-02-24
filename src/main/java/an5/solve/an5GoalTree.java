package an5.solve;

import an5.model.*; 

abstract public class an5GoalTree {  
  abstract public int solve();
  abstract public int seed();
  abstract public int status();
  abstract public int score();
  abstract public int cost();
  abstract public an5GoalTree getNextGoal(an5SearchControl ctrl);
  abstract public void suspend();
  abstract public void resume();
  abstract public void release();
  abstract public String[] why();
  abstract public String[] how();
}
