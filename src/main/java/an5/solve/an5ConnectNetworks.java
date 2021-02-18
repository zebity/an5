/**
 ConnectNetwork == bring two networks together to create new network.
   There are multiple scenarios that need to be supported
   a. There are common elements with two networks that can act as joinPoints
   b. There are no common elements and should join at provided connection points
   c. Joined networks will expose union of services of individual networks
 */
package an5.solve;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import an5.model.an5ClassInstance;
import an5.model.an5ClassTemplate;
import an5.model.an5Element;
import an5.model.an5Network;
import an5.model.an5Object;
import an5.model.an5Service;
import an5.model.an5VariableInstance;

public class an5ConnectNetworks extends an5Template {
  an5Object prototype;
  an5Network netA,
             netB;
  an5Object connectA,
            connectB;
  List<an5Object> use;
  an5Network resultNet;
  
  /* Working */
  Collection<an5Object> unique = new HashSet<>();
  
  /* Map<an5Object, an5Object> available = new HashMap<>();
  Map<an5Object, an5Object> mustUse =  new HashMap<>();
  an5AvailableInterfaces availableInterface = new an5AvailableInterfaces();
  an5Service viaService;
  String srcClass,
         destClass;
  List<an5Object> srcObjects; */
  int addCount = 0;
  
  public an5ConnectNetworks(an5Object proto, an5Network n1, an5Element a,
		   an5Network n2, an5Element b, List<an5Object> avail, an5Network res) {
    prototype = proto;
    netA = n1;
    netB = n2;
    connectA = a;
    connectB = b;
    use = avail;
    resultNet = res;
  }
  int seedGoal() {
    int i = 0;
    
    /*
    viaService = prototype.providesServices().getWhere(1, -1);
    for (an5VariableInstance c : prototype.AN5AT_vars.values()) {
      if (c instanceof an5ClassInstance) {
    	an5ClassInstance cl = (an5ClassInstance)c;
    	if (cl.mandatory) {
    	  if (i == 0) {
    	    srcClass = cl.objectDefinition.getClass().getName();
    	  } else if  (i == 2) {
    		destClass = cl.objectDefinition.getClass().getName();
    	  }
    	  i++;
    	}
      }
    }
    if (destClass != null & destClass.equals(connectTo.getClass().getName())) {
      mustUse.put(connectTo, connectTo);
      for (i = 0; i < srcObjects.size(); i++) {
        if (srcClass.equals(srcObjects.get(i))) {
          mustUse.put(srcObjects.get(i), srcObjects.get(i));
          toAdd.add(srcObjects.get(i));
        }
      }
      if (toAdd.size() > 0) {
        for (i = 0; i < use.size(); i++) {
          an5Object o = use.get(i);
          if (! mustUse.containsKey(o)) {
            available.put(o,o);
            availableInterface.available(o);
          }
        }
      }
    } */
    return i;
  }
  an5Template[] getNextGoals(an5SearchStats st) {
    an5Template[] res = null;
    return res;
  }
}
