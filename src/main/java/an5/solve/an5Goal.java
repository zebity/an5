package an5.solve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import an5.model.*; 

public class an5Goal extends an5GoalTree {
  int strategy = SearchOptions.SCORE;
  int result = SearchResult.START;
  int cost,
      endScore;
  an5Template template;
  an5SearchStats stats;
  
  public an5Goal(an5Template targ, an5SearchStats st) {
    template = targ;
    stats = st;
  }
  public int solve() {
    int res = 0;
    an5GoalTree next;
    
    endScore = seed();
    
    res = status();
    switch (res) {
      case SearchResult.SOLVING:
      case SearchResult.START: stats.maxDepth++;
                               next = getNextGoal(stats);
                               res = next.solve();
                               break;
      case SearchResult.VISITED: stats.noRevisits++;
                                 break;
      case SearchResult.FAILED: stats.noFailed++;
                                break;
      case SearchResult.FOUND: stats.noFound++;
                               break;
      case SearchResult.SUSPENDED:
      default: break;
    }
    return res;
  }
  public int seed() {
	int res = 0;
	
    if (template instanceof an5CreateNetwork) {
      an5CreateNetwork net = (an5CreateNetwork)template;
      res = net.seedGoal();    	
    } else if (template instanceof an5JoinNetwork) {
      an5JoinNetwork join = (an5JoinNetwork)template;
      res = join.seedGoal(); 
    } else if (template instanceof an5ConnectNetworks) {
      an5ConnectNetworks connect = (an5ConnectNetworks)template;
      res = connect.seedGoal();
    }
    return res;
  }
  public int status() {
    return SearchResult.UNDEFINED;
  }
  public an5GoalTree getNextGoal(an5SearchStats st) {
    return null;
  }
  public void suspend() {
  }
  public void resume() {
  }
  public String[] why() {
    return null;
  }
  public String[] how() {
    return null;
  }
}
