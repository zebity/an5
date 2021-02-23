package an5.solve;

import java.util.List;

import an5.model.*;

abstract public class an5Template {
  abstract public int seedGoal();
  abstract public an5GoalTree getNextGoal(an5SearchControl ctrl);
  abstract public int status();
  public an5Template connectElements(an5Network n, an5Element a, an5Element b) {
	an5Template res = null;
	return res;
  }
  public an5Template createNetwork(an5Network proto, List<an5Object> from, an5Network net) {
    return null;
  }
  public an5Template joinNetwork(an5Element proto, an5Network n, List<an5Object> ele, List<an5Object> use) {
	an5Template res = null;
	return res;
  }
  public an5Template networkPath(an5Network n, an5Element a, an5Element b) {
	an5Template res = null;
	return res;
  }
  public an5Template connectNetworks(an5Network n1, an5Element a, an5Network n2, an5Element b) {
	an5Template res = null;
	return res;
  }
}
