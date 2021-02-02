package an5.solve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import an5.model.*; 

public class an5Goal {
  static class SearchType { static int DEPTH = 0, BREADTH = 1, BOUND = 2, COST = 3;};
  static class SearchResult {static int START = 0, SOLVING = 1, FAILED = 2, FOUND = 3;};
  an5Network network;
  an5Template goal;
  List<an5Object> elementsAndLinks;
  an5Goal root,
          parent;
  List<an5Goal> children = new ArrayList<>();
  Map<an5Goal, an5Goal> visited = new HashMap<>();
  int strategy = SearchType.DEPTH;
  int result = SearchResult.START;
  int cost;
  public an5Goal(an5Template target, an5Network net, List<an5Object> from) {
    network = net;
    goal = target;
    elementsAndLinks = from;
  }
  public int solve() {
    int res = 0;
    return res;
  }
  void seedGoal() {
    if (goal instanceof an5CreateNetwork) {
    	
    }
  }
}
