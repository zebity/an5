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
import java.util.Collections;
import java.util.Comparator;
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
  /* class pathsAndPool {
	public List<an5Object> paths;
    public Map<String, an5Object> pool;
    public pathsAndPool() {
      paths = new ArrayList<>();
      pool = new HashMap<>();
    }
  } */
  class sortPathCnts implements Comparable<sortPathCnts> {
    public int cnt = 0,
               idx = 0,
               chunkSize = 0,
               increment = 0;
    public sortPathCnts(int c, int i) {
      cnt = c;
      idx = i;
    }
    public int compareTo(sortPathCnts b) {
    // Biggest to smallest
      return b.cnt - cnt;
    }
  }
  class sortByIndex implements Comparator<sortPathCnts> {
    public int compare(sortPathCnts a, sortPathCnts b) {
    // Smallest to biggest
      return a.idx - b.idx;		
    }
  }
  an5Object prototype;
  an5Network joinNet;
  an5Object connectTo;
  List<an5Object> srcObjects;
  an5AvailableResource use;
  
  /* Working */
  List<an5Object> toAdd = new LinkedList<>();
  Map<String, an5Object> mustUse =  new HashMap<>();
  an5AvailableResource available = new an5AvailableResource(true);
  an5Service viaService; /* Should be also be using network services */
  String srcClass,
         destClass;
  int joinCount = 0,
	  status = an5SearchControl.SearchResult.UNDEFINED;
  
  public an5JoinNetwork(an5Template par, an5Object proto, an5Network net, an5Object to, List<an5Object> ele, an5AvailableResource avail) {
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
	use = new an5AvailableResource(false);
	for (an5Object o: avail.values())
	  use.add(o);
	connectTo = to;
  }
  public an5JoinNetwork(an5Template par, an5Object proto, an5Network net, an5Object to, an5Object ele, an5AvailableResource avail) {
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
    for (an5VariableInstance c : prototype.AN5AT_classes.values()) {
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
        if (srcClass.equals(srcObjects.get(i).getFirst().getClass().getName())) {
          mustUse.put(srcObjects.get(i).getGUID(), srcObjects.get(i));
          toAdd.add(srcObjects.get(i));
        }
      }
      if (toAdd.size() > 0) {
        for (i = 0; i < use.size(); i++) {
          an5Object o = use.get(i);
          if (! mustUse.containsKey(o.getGUID())) {
            available.put(o);
          }
        }
      }
    }
    status = an5SearchControl.SearchResult.START;
    return mustUse.size() - 1;
  }
  public an5GoalTree getNextGoal(an5SearchControl ctrl) {
    an5GoalTree res = null;
    List<an5GoalTree> orJoins = new LinkedList<>();
    an5Object fromO, toO;
    an5Network targetNet;
    an5Path[] foundPaths;
    an5Binding[] bindings;
    int i, j, k, l;
    int[][] pathCnt = new int[toAdd.size()][3];
    List<sortPathCnts> sortCnts = new ArrayList<>();
    an5InterfaceFinder finder = new an5InterfaceFinder();
    int fail = 0, maxBind = 0, alts = 0, maxBnd = 0,
    	fndMin = pathCnt.length,
    	fndMax = 0, minBnd = pathCnt.length,
    	pathExpand = 1;
    /* Before going to trouble of expanding paths make sure it is worth it.. */
    for (i = 0; i < pathCnt.length; i++) {
      j = toAdd.get(i).getLast().canBind(null, connectTo, joinNet.providesServices(), viaService);
      if (j > 0) {
         pathCnt[i][0] = j;
         pathCnt[i][1] = 0;
         pathCnt[i][2] = i;
         fndMin = Integer.min(i, fndMin);
         fndMax = Integer.max(i, fndMax);
      } else { 
      	 k = finder.canMatchInterface(toAdd.get(i).getLast(), joinNet.providesServices(), viaService, available);
         if (k > 0) {
           pathCnt[i][0] = 0;
           pathCnt[i][1] = k;
           pathCnt[i][2] = i;
           sortCnts.add(new sortPathCnts(k, i));
           alts++;
           pathExpand = pathExpand * k;
           maxBind = Integer.max(maxBind, k);
           minBnd = Integer.min(minBnd, i);
           maxBnd = Integer.max(maxBnd, i);
         }
         else {
           pathCnt[i][0] = -1;
           pathCnt[i][1] = -1;
           pathCnt[i][2] = i;
           fail = -1;
         }
      }
    }
    if (fail == -1) {
      orJoins.add(new an5FailedGoal());
      status = an5SearchControl.SearchResult.FAILED;
      res = new an5OrGoal(orJoins, ctrl);
    } else if (toAdd.size() == 0) {
      if (mustUse.size() == 0)	{
    	orJoins.add(new an5FoundGoal(this));
    	status = an5SearchControl.SearchResult.FOUND; 
    	res = new an5OrGoal(orJoins, ctrl);
      } else {
        orJoins.add(new an5FailedGoal());
        status = an5SearchControl.SearchResult.FAILED;
        res = new an5OrGoal(orJoins, ctrl);      
      }
    } else {
      targetNet = (an5Network)joinNet.clone(); /* only need "little" part of target network */
      toO = targetNet.getMember(connectTo.getGUID());
      for (i = fndMin; i <= fndMax; i++) {
        toO = (an5Object)toAdd.get(i).clone();
        if (pathCnt[i][0] > 0) {
          toO = targetNet.getMember(connectTo.getGUID());
    	  fromO = (an5Object)toAdd.get(i).clone();
    	  bindings = fromO.bind(toO, targetNet.providesServices(), viaService);
    	  /* connectTo object already in network, so just add toAdd[i] object */
    	  targetNet.putCandidate(fromO);
        }
      }
      an5Object[][] addPaths = new an5Object[pathExpand][alts];
      Collections.sort(sortCnts);
      sortCnts.get(0).chunkSize = sortCnts.get(0).cnt;
      sortCnts.get(0).increment = 1;  
      for (i = 1; i < sortCnts.size(); i++) {
        sortCnts.get(i).chunkSize = sortCnts.get(i).cnt *  sortCnts.get(i-1).chunkSize;
        sortCnts.get(i).increment = sortCnts.get(i).chunkSize / sortCnts.get(i).cnt; 
      }
      Collections.sort(sortCnts, new sortByIndex());
      an5AvailableResource pool = null;
      /* Have: fixed network with success cases as "candidate"
       *        Now have alternates of:
       *           connecting "element" list
       *           available resources pool
       */
      for (int m = 0; m < sortCnts.size(); m++) {
    	i = sortCnts.get(m).idx;
        j = 0;
        pool = (an5AvailableResource)available.clone();
        pool.load();
     	fromO = (an5Object)toAdd.get(i).clone();
     	foundPaths = finder.probePaths(fromO, joinNet.providesServices(), viaService, pool);
     	if (foundPaths.length <= sortCnts.get(m).cnt) {
     	  for (k = 0; k < pathExpand; k += sortCnts.get(m).increment) {
     		for (l = 0; l < sortCnts.get(m).increment; l++) {
     		  if (j < foundPaths.length) {
     	        addPaths[k+l][m] = foundPaths[j];
     		  } else {
     			addPaths[k+l][m] = null;
     		  }
     		}
     		j = (j + 1) % sortCnts.get(m).cnt;
          }
        } else {
          System.out.println("AN5:ERR:Inconsistent Probe Path Counts: " + foundPaths.length + " vs : " + sortCnts.get(m).cnt);
        }
      }
      an5Object removed = null;
      List<an5Object> toJoin = null;
      boolean skip;
 	  for (k = 0; k < pathExpand; k++) {
 		skip = false;
 		toJoin = new ArrayList<>();
 		Map<String, an5Object> avail = available.copyMap();
 		for (l = 0; l < alts; l++) {
 		  if (addPaths[k][l] != null) {
 		    toJoin.add(addPaths[k][l]);
 		    removed = avail.remove(addPaths[k][l].getLast().getGUID());
 		    if (removed == null) {
 		      skip = true; /* skip due to resource depletion */
 		    }
 		  }
 		}
 		if (! skip) {
          orJoins.add(new an5SimpleGoal(new an5JoinNetwork(this, prototype, targetNet, connectTo, toJoin, avail), ctrl));
 		}
      }
      res = new an5OrGoal(orJoins, ctrl);
      status = an5SearchControl.SearchResult.SOLVING;
    }
    return res;
  }
  public int status() {
	return status;
  }
}
