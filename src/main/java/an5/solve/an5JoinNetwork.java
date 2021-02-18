/**
 JoinNetwork == add new elements / links to an existing network.
   There are multiple scenarios that need to be supported
   a. Require that join is via particular "service" and topology as specified by prototype
   b. That join must be via specified object, as specified by prototype
   c. network only has single object, so can only be by connecting to that object
   d. multiple objects can join, but same prototype constraints apply
 */
package an5.solve;

import java.util.ArrayList;
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

public class an5JoinNetwork extends an5Template {
  an5Object prototype;
  an5Network joinNet;
  an5Object connectTo;
  List<an5Object> srcObjects;
  List<an5Object> use;
  
  /* Working */
  List<an5Object> toAdd = new LinkedList<>();
  Map<an5Object, an5Object> available = new HashMap<>();
  Map<an5Object, an5Object> mustUse =  new HashMap<>();
  an5AvailableInterfaces availableInterface = new an5AvailableInterfaces();
  an5Service viaService;
  String srcClass,
         destClass;

  int addCount = 0;
  
  public an5JoinNetwork(an5Object proto, an5Network net, an5Object to, List<an5Object> ele, List<an5Object> avail) {
    prototype = proto;
    joinNet = net;
    srcObjects = ele;
    use = avail;
    connectTo = to;
  }
  public an5JoinNetwork(an5Object proto, an5Network net, an5Object to, an5Object ele, List<an5Object> avail) {
	prototype = proto;
	joinNet = net;
	srcObjects = new ArrayList<>();
	srcObjects.add(ele);
	use = avail;
    connectTo = to;
  }
  int seedGoal() {
    int i = 0;
    
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
    }
    return mustUse.size() - 1;
  }
  an5Template[] getNextGoals() {
    an5Template[] res = null;
    return res;
  }
}
