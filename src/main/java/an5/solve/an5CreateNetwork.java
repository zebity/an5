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
import an5.model.an5Element;
import an5.model.an5Network;
import an5.model.an5Service;
import an5.model.an5VariableInstance;

public class an5CreateNetwork extends an5Template {
  class xref{ Map<an5Object, an5Object> from;
              an5Object key;
            }
  an5Network netType;
  Map<an5Object, an5Object> available =  new HashMap<>();
  an5Service mustProvide,
             canProvide;
  Map<an5Object, an5Object> mustUse =  new HashMap<>();
  List<an5Object> mustUseOrder = new LinkedList();
  Collection<String> mustUseClass = new HashSet<>();
  List<an5Network> networks = new ArrayList<>();
  List<an5Object> bestStarter = new ArrayList<>();
  List<an5Object> altStarter = new ArrayList<>();
  an5InterfaceSignatureKeys availableInterface = new an5InterfaceSignatureKeys();
  
  public an5CreateNetwork(an5Network n) {
    netType = n;
  }
  void seedGoal(List<an5Object> use) {
    mustProvide = netType.providesServices().getWhere(1, -1);
    canProvide = netType.providesServices().getWhere(0, -1);
    for (an5VariableInstance c : netType.AN5AT_vars.values()) {
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
    	mustUse.put(o,o);
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
    	available.put(o,o);
    	// availableInterface.add(o);
      }
    }
  }
}
