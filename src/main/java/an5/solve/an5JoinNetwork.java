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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import an5.model.an5ClassInstance;
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
  int joinCount = 0,
	  status = an5SearchControl.SearchResult.UNDEFINED;
  
  public an5JoinNetwork(an5Template par, an5Object proto, an5Network net, an5Object to, List<an5Object> ele, List<an5Object> avail) {
    super(par);	  
    prototype = proto;
    joinNet = net;
    srcObjects = ele;
    use = avail;
    connectTo = to;
  }
  public an5JoinNetwork(an5Template par, an5Object proto, an5Network net, an5Object to, an5Object ele, List<an5Object> avail) {
	super(par);
	prototype = proto;
	joinNet = net;
	srcObjects = new ArrayList<>();
	srcObjects.add(ele);
	use = avail;
    connectTo = to;
  }
  public int[] gauge() {
	int sc = joinCount;
	int[] parGauge = new int[]{0,1};
	if (parent != null) {
      parGauge = parent.gauge();
	}
	return new int[]{(sc * parGauge[1]) + parGauge[0], parGauge[1]};
  }
  public int seedGoal() {
	int i = 0;
    viaService = prototype.providesServices().getWhere(1, -1);
    for (an5VariableInstance c : prototype.AN5AT_vars.values()) {
      if (c instanceof an5ClassInstance) {
    	an5ClassInstance cl = (an5ClassInstance)c;
    	if (cl.mandatory) {
    	  if (cl.order == 0) {
    	    srcClass = cl.objectDefinition.getClass().getName();
    	  } else if (cl.order == 1) {
    		destClass = cl.objectDefinition.getClass().getName();
    	  }
    	}
      }
    }
    if (destClass != null & destClass.equals(connectTo.getClass().getName())) {
      mustUse.put(connectTo, connectTo);
      for (i = 0; i < srcObjects.size(); i++) {
        if (srcClass.equals(srcObjects.get(i).getClass().getName())) {
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
    status = an5SearchControl.SearchResult.START;
    return mustUse.size() - 1;
  }
  public an5GoalTree getNextGoal(an5SearchControl crtl) {
    an5GoalTree res = null;
    for (int i = 0; i < use.size(); i++) {
    	
    }
    status = an5SearchControl.SearchResult.SOLVING;
    return res;
  }
  public int status() {
	return status;
  }
}
