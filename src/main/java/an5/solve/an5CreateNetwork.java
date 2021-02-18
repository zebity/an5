package an5.solve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import an5.model.an5Object;
import an5.model.an5InterfaceSignatureKeys;
import an5.model.an5ClassInstance;
import an5.model.an5ClassTemplate;
import an5.model.an5Element;
import an5.model.an5Network;
import an5.model.an5Service;
import an5.model.an5VariableInstance;

public class an5CreateNetwork extends an5Template {
  public class BuildStrategy { static final int SINGLE_NET_ADD = 1, MULTI_NET_JOIN = 2; }
  an5Network resultNetwork;
  an5Network netPrototype;
  an5Object nodePrototype;
  List<an5Object> use;
  
  /* Working */
  int strategy = BuildStrategy.SINGLE_NET_ADD;
  List<an5Object> available =  new ArrayList<>();
  an5Service mustProvide,
             canProvide;
  List<an5Object> mustUseOrder = new LinkedList<>();
  Collection<String> mustUseClass = new HashSet<>();
  List<an5Network> networks = new ArrayList<>();
  List<an5Object> bestStarter = new ArrayList<>();
  List<an5Object> altStarter = new ArrayList<>();

  public an5CreateNetwork(an5Object[] proto, List<an5Object> from, an5Network net) {
    netPrototype = (an5Network)proto[0];
    nodePrototype = (an5Object)proto[1];
    use = from;
    resultNetwork = net;
  }
  int seedGoal() {
    mustProvide = netPrototype.providesServices().getWhere(1, -1);
    canProvide = netPrototype.providesServices().getWhere(0, -1);
    for (an5VariableInstance c : netPrototype.AN5AT_vars.values()) {
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
          boolean[] can = el.providesService(mustProvide, canProvide);
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
    return mustProvide.size() + canProvide.size() + mustUseOrder.size() + 1;
  }
  public an5GoalTree getNextGoal(an5SearchStats st) {
	an5GoalTree res = null;
	an5Network resNet;
	int i, j;
	
	if (bestStarter.size() > 0) {
      if (strategy == BuildStrategy.SINGLE_NET_ADD) {
        List<an5Template> altNets = new LinkedList<>();
        for (i = 0; i > bestStarter.size(); i++) {
          resNet = (an5Network)netPrototype.createInstance();
          resNet.members.put(bestStarter.get(i).getGUID(), (an5Object)(bestStarter.get(i).clone()));
          an5JoinNetwork join = new an5JoinNetwork(nodePrototype, resNet, bestStarter.get(i), mustUseOrder, available);
          altNets.add(join);
        }
        res = new an5OrGoal(altNets, st);
      } else if (strategy == BuildStrategy.MULTI_NET_JOIN) {
        List<an5Template> altNets = null;
        List<an5AndGoal> andTrees = null;
        for (i = 0; i > bestStarter.size(); i++) {
          altNets = new LinkedList<>();
          andTrees = new LinkedList<>();
          resNet = (an5Network)netPrototype.createInstance();
          resNet.members.put(bestStarter.get(i).getGUID(), (an5Object)bestStarter.get(i).clone());
          for (j = 0; j > mustUseOrder.size(); j++) {   
            an5JoinNetwork join = new an5JoinNetwork(nodePrototype, resNet, bestStarter.get(i), mustUseOrder.get(j), available);
            altNets.add(join);
          }
          andTrees.add(new an5AndGoal(altNets, st));
        }
        /* res = new an5OrGoal */
      }
	}
    return null;
  }
}
