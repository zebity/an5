package an5.solve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import an5.model.*; 

public class an5AndGoal extends an5GoalTree {
  an5SearchStats stats;
  List<an5Template> template;
  
  public an5AndGoal(List<an5Template> t, an5SearchStats s) {
	template = t;
	stats = s;
  }
  public int solve() {
    return SearchResult.UNDEFINED;
  }
  public int seed() {
	int res = 0;
    return res;
  }
  public int status() {
	int res = SearchResult.UNDEFINED;
    return res;
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
