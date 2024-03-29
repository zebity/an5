/**
 @what Build a network from a bucket of bits...
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.math3.fraction.Fraction;

import an5.model.an5Object;
import an5.model.an5ClassInstance;
import an5.model.an5Element;
import an5.model.an5Network;
import an5.model.an5Service;
import an5.model.an5VariableInstance;

public class an5CreateNetwork extends an5Template {
  an5Network resultNetwork;
  an5Network netPrototype;
  an5Object[] nodePrototype;
  List<an5Object> use;
  
  /* Working */
  an5AvailableResource available  = new an5AvailableResource(false);
  an5Service mustProvide,
             canProvide;
  List<an5Object> mustUseOrder = new LinkedList<>();
  Collection<String> mustUseClass = new HashSet<>();
  List<an5Network> networks = new ArrayList<>();
  List<an5Object> bestStarter = new ArrayList<>();
  List<an5Object> altStarter = new ArrayList<>();
  int status = an5SearchControl.SearchResult.UNDEFINED;
  int pathsExpanded = 0;

  public an5CreateNetwork(an5Template p, an5Object proto, List<an5Object> from, an5Network net) {
    super(p);
	netPrototype = (an5Network)proto;
    nodePrototype = proto.createConstraints();
    use = from;
    resultNetwork = net;
  }
  public int seedGoal() {
    mustProvide = netPrototype.expose().getWhere(1, -1);
    canProvide = netPrototype.expose().getWhere(0, -1);
    for (an5VariableInstance c : netPrototype.AN5AT_classes.values()) {
      if (c instanceof an5ClassInstance) {
    	an5ClassInstance cl = (an5ClassInstance)c;
    	if (cl.mandatory) {
    	  mustUseClass.add(cl.objectDefinition.getClass().getName());
    	}
      }
    }
    for (int i = 0; i < use.size(); i++) {
      an5Object o = use.get(i);
      if (mustUseClass.contains(o.getClass().getName())) {
    	// mustUse.put(o,o);
    	mustUseOrder.add(o);
    	if (o instanceof an5Element) {
          an5Element el = (an5Element)o;
          boolean[] can = el.checkServicesProvided(mustProvide, canProvide);
          if (can[0]) {
        	if (can[1]) {
        	  bestStarter.add(o);
        	}
        	else {
        	  altStarter.add(o);
        	}
          }
          else if (can[1]) {
        	altStarter.add(o);
          }
    	}
    	else if (o instanceof an5Network) {
          networks.add((an5Network)o);
    	}
      }
      else {
    	available.add(o);
      }
    }
    status = an5SearchControl.SearchResult.START;
    return mustProvide.size() + canProvide.size() + mustUseOrder.size() + 1;
  }
  public an5GoalTree getGoalFromStarter(List<an5Object> starter, an5SearchControl ctrl) {
    an5GoalTree res = null;
	an5Network resNet;
	int i, j;
	
    if (ctrl.networkBuildStrategy == an5SearchControl.BuildStrategy.SINGLE_NET_ADD) {
        List<an5GoalTree> altNets = new LinkedList<>();
        for (i = 0; i < starter.size(); i++) {
          resNet = (an5Network)netPrototype.createInstance();
          resNet.putMember((an5Object)(starter.get(i).clone()));
        an5JoinNetwork join = new an5JoinNetwork(this, nodePrototype, resNet, starter.get(i), mustUseOrder, available);
        altNets.add(new an5SimpleGoal(join,ctrl));
      }
      res = new an5OrGoal(altNets, ctrl);
      pathsExpanded = res.goalQueueSize();
    } else if (ctrl.networkBuildStrategy == an5SearchControl.BuildStrategy.MULTI_NET_JOIN) {
      /* This strategy can only work if you can share "parts buckets" across nets */
      List<an5GoalTree> altStarts = new LinkedList<>();
      List<an5GoalTree> andJoins = null;
      List<an5GoalTree> joinSet = null;
      List<an5Network> smallNets = null;
      an5GoalTree connectGoal = null;

      for (i = 0; i < starter.size(); i++) {
        andJoins = new LinkedList<>();
        joinSet = new LinkedList<>();
        smallNets = new LinkedList<>();
        resNet = (an5Network)netPrototype.createInstance();
        resNet.putMember((an5Object)starter.get(i).clone());
        for (j = 0; j > mustUseOrder.size(); j++) {   
          an5JoinNetwork join = new an5JoinNetwork(this, nodePrototype, resNet, starter.get(i), mustUseOrder.get(j), available);
          joinSet.add(new an5SimpleGoal(join, ctrl));
        }
        andJoins.add(new an5AndGoal(joinSet, ctrl));
        smallNets.add(resNet);
        connectGoal = new an5SimpleGoal(new an5ConnectNetworks(this, null, smallNets, resultNetwork), ctrl);
        andJoins.add(connectGoal);
        pathsExpanded += andJoins.size();
        altStarts.add(new an5OrGoal(andJoins, ctrl));
      }
      res = new an5OrGoal(altStarts, ctrl);
      pathsExpanded += res.goalQueueSize();
    }
    return res;
  }
  public an5GoalTree getNextGoal(an5SearchControl ctrl) {
	an5GoalTree res = null;
	
	if (bestStarter.size() > 0) {
      res = getGoalFromStarter(bestStarter, ctrl);
	} else if (altStarter.size() > 0) {
	   res = getGoalFromStarter(altStarter, ctrl);
	}
	else {
	  res = getGoalFromStarter(mustUseOrder, ctrl);
	}
	status = an5SearchControl.SearchResult.SOLVING;
    return res;
  }
  public int status() {
	return status;
  }
  public int[] gauge(int type) {
	Fraction res = new Fraction(bestStarter.size() + altStarter.size(), 1);
	if (parent != null) {
	  int []parGauge = parent.gauge(type);
	  res.add(new Fraction(parGauge[0], parGauge[1]));
	}
	return new int[]{res.getNumerator(), res.getDenominator()};
  }
}
