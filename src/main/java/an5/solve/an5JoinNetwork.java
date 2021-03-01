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
import an5.model.an5Path;
import an5.model.an5Service;
import an5.model.an5Binding;
import an5.model.an5VariableInstance;

public class an5JoinNetwork extends an5Template {
  an5Object prototype;
  an5Network joinNet;
  an5Object connectTo;
  List<an5Object> srcObjects;
  List<an5Object> use;
  
  /* Working */
  List<an5Object> toAdd = new LinkedList<>();
  Map<String, an5Object> available = new HashMap<>();
  Map<String, an5Object> mustUse =  new HashMap<>();
  an5AvailableInterfaces availableInterface = new an5AvailableInterfaces();
  an5Service viaService; /* Should be also be using network services */
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
  public an5JoinNetwork(an5Template par, an5Object proto, an5Network net, an5Object to, List<an5Object> ele, Map<String, an5Object> avail) {
	super(par);	  
	prototype = proto;
	joinNet = net;
	srcObjects = ele;
	for (an5Object o: avail.values())
	  use.add(o);
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
      mustUse.put(connectTo.getGUID(), connectTo);
      for (i = 0; i < srcObjects.size(); i++) {
        if (srcClass.equals(srcObjects.get(i).getClass().getName())) {
          mustUse.put(srcObjects.get(i).getGUID(), srcObjects.get(i));
          toAdd.add(srcObjects.get(i));
        }
      }
      if (toAdd.size() > 0) {
        for (i = 0; i < use.size(); i++) {
          an5Object o = use.get(i);
          if (! mustUse.containsKey(o.getGUID())) {
            available.put(o.getGUID(),o);
            availableInterface.available(o);
          }
        }
      }
    }
    status = an5SearchControl.SearchResult.START;
    return mustUse.size() - 1;
  }
  public Map<String, an5Object> notAvailable(an5Object o) {
    Map<String, an5Object> to = new HashMap<>();
    for (an5Object from : available.values())
      to.put(from.getGUID(), from);
    if (o instanceof an5Path) {
      an5Path p = (an5Path)o;
      to.remove(p.path.get(p.path.size() - 1).getGUID());
    } else {
      to.remove(o.getGUID());
    }
    return to;
  }
  public an5GoalTree getNextGoal(an5SearchControl ctrl) {
    an5GoalTree res = null;
    List<an5GoalTree> andGoals = new LinkedList<>();
    an5Object o, c;
    an5Network n;
    an5Binding[] b;
    an5Path[] p;
    for (int i = 0; i < toAdd.size(); i++) {
      int j = toAdd.get(i).canBind(connectTo, joinNet.providesServices(), viaService);
      if (j > 0) {
    	o = (an5Object)toAdd.remove(i).clone();
    	n = (an5Network)joinNet.clone(); /* only need "little" part of target network */
    	c = n.members.get(connectTo.getGUID());
    	b = o.bind(c, n.providesServices(), viaService);
    	n.members.put(o.getGUID(), o);
    	availableInterface.notAvailable(b);
    	andGoals.add(new an5SimpleGoal(new an5JoinNetwork(this, prototype, n, c, toAdd, available), ctrl));
      } else {
    	 int k = availableInterface.canMatchInterface(toAdd.get(i), joinNet.providesServices(), viaService);
    	 if (k > 0) {
    	   List<an5GoalTree> alts = new LinkedList<>();
    	   o = (an5Object)toAdd.remove(i).clone();
    	   p = availableInterface.probePaths(o, joinNet.providesServices(), viaService);
    	   Map<String, an5Object> a = null;   	   
    	   /* have alternate paths with uncommitted bindings */
      	   for (int l = 0; i < k; l++) {
        	 a = notAvailable(p[l]);
      	     alts.add(new an5SimpleGoal(new an5JoinNetwork(this, prototype, joinNet, p[l], toAdd, a), ctrl));
      	   }
      	   andGoals.add(new an5OrGoal(alts, ctrl));
    	 }
      }
    }
    res = new an5AndGoal(andGoals, ctrl);
    status = an5SearchControl.SearchResult.SOLVING;
    return res;
  }
  public int status() {
	return status;
  }
}
