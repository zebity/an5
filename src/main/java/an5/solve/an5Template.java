/**
 @what The default an5 Solver template class
         All domain solvers should inherit this
         This is also used to provide "documentation" of the solver methods:
         ConnectElements - connect elements within network
         CreateNetwork - create a new network from bucket of bits
         JoinNetwork - add new elements into an existing network
         NetworkPath - find path within an network
         ConnectNetworks - join two network together
         These are the basic operations used by "problem reduction" solver to build networks

 @note currently implemented: Create / Join
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd.
*/
package an5.solve;

import java.util.List;

import an5.model.*;

abstract public class an5Template extends an5SearchGauge {
  public an5Template parent = null;
  public an5Template(an5Template p) {
    parent = p;
  }
  public int getDepth() {
    int i = 1;
    an5Template next = parent;
    while (next != null) {
      next = next.parent;
      i++;
    }
    return i;
  }
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
