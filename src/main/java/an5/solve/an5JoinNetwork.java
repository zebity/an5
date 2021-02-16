/**
 JoinNetwork == add new elements / links to an existing network.
   There are multiple scenarios that need to be supported
   a. Require that join is via particular "service" and topology as specified by prototype
   b. That join must be via specified object, as specified by prototype
   c. network only has single object, so can only be by connecting to that object
   d. multiple objects can join, but same prototype constraints apply
 */
package an5.solve;

import java.util.LinkedList;
import java.util.List;

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
  List<an5Object> toAdd;
  List<an5Object> available = new LinkedList<>();
  an5AvailableInterfaces availableInterface = new an5AvailableInterfaces();
  an5Service viaService;
  String srcClass,
         destClass;
  List<an5Object> use;
  
  public an5JoinNetwork(an5Object proto, an5Network net, an5Object from, List<an5Object> ele, List<an5Object> avail) {
    prototype = proto;
    joinNet = net;
    toAdd = ele;
    use = avail;
  }
  int seedGoal() {
    int res = 0;
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
    	}
      }
    }
    for (int i = 0; i < use.size(); i++) {
      an5Object o = use.get(i);
      if (mustUseClass.contains(o.getClass().getName())) {
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
      	availableInterface.available(o);
      }
    }
    return res;
  }
  an5Template[] getNextGoals() {
    an5Template[] res = null;
    return res;
  }
}
