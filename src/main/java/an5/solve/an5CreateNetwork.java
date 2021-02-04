package an5.solve;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import an5.model.an5Object;
import an5.model.an5ClassInstance;
import an5.model.an5Element;
import an5.model.an5Network;
import an5.model.an5Service;
import an5.model.an5VariableInstance;

public class an5CreateNetwork extends an5Template {
  an5Network netType;
  List<an5Object> available =  new  ArrayList<>();
  an5Service mustProvide,
             canProvide;
  List<an5Object> mustUse = new ArrayList<>();
  Collection<String> mustUseClass = new HashSet<>();
  
  public an5CreateNetwork(an5Network n) {
    netType = n;
  }
  void seedGoal(List<an5Object> use) {
    mustProvide = netType.providesServices().getWhere(1, -1);
    canProvide = netType.providesServices().getWhere(0, -1);
    for (an5VariableInstance c : netType.clVars.values()) {
      if (c instanceof an5ClassInstance) {
    	an5ClassInstance cl = (an5ClassInstance)c;
    	if (cl.mandatory) {
    	  mustUseClass.add(cl.objectDefinition.getClass().getName());
    	}
      }
    }
    for (an5Object o: use) {
      if (mustUseClass.contains(o.getClass().getName())) {
    	mustUse.add(o);
      }
      else {
    	available.add(o);
      }
    }
  }
}
