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

import org.apache.commons.math3.fraction.Fraction;

import an5.model.an5ClassInstance;
import an5.model.an5Network;
import an5.model.an5Object;
import an5.model.an5Path;
import an5.model.an5Service;
import an5.an5Logging;
import an5.model.an5Binding;
import an5.model.an5VariableInstance;

public class an5JoinNetwork extends an5Template {
  public class PathIdx { public static final int BIND = 0, PROBE = 1, INDEX = 2, USED = 3, SIZE = 4; }
  public class PathStats {
    int minDirect = 0,
        maxDirect = 0,
        expansionMultiplier = 1,
        alternates = 0;
  }
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
  an5Logging log = new an5Logging(7,7);
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
	  status = an5SearchControl.SearchResult.UNDEFINED,
	  pathExpansion = 0;
  
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
  public int[] gauge(int type) {
	int denom = 1;
	if (pathExpansion > 0 && (type & an5SearchControl.SearchOptions.COST) > 0)
	  denom = pathExpansion;
    Fraction res = new Fraction(joinCount, denom);
	if (parent != null) {
	  int []parGauge = parent.gauge(type);
	  res.add(new Fraction(parGauge[0], parGauge[1]));
	}
	return new int[]{res.getNumerator(), res.getDenominator()};
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
    an5Object startFromO, fromO, toO;
    an5Network targetNet;
    an5Path[] foundPaths;
    an5Binding[] bindings;
    PathStats pathStats = new PathStats();
    int i, j, k, l;
    int[][] pathCnt = new int[toAdd.size()][PathIdx.SIZE];
    List<sortPathCnts> probeCnts = new ArrayList<>();
    an5InterfaceFinder finder = new an5InterfaceFinder();
    int fail = 0;
    
    fail = getPathStats(pathStats, pathCnt, probeCnts, finder, (ctrl.strategy & an5SearchControl.SearchOptions.BIND_UNIQUE) > 0);
    if (fail == -1) {
      orJoins.add(new an5FailedGoal());
      status = an5SearchControl.SearchResult.FAILED;
      res = new an5OrGoal(orJoins, ctrl);
    } else if (toAdd.size() == 0) {
      if (mustUse.size() == 1 && mustUse.get(0).getGUID().equals(connectTo.getGUID())) {
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
      for (i = pathStats.minDirect; i <= pathStats.maxDirect; i++) {
        // fromO = (an5Object)toAdd.get(i).clone();
        if (pathCnt[i][PathIdx.BIND] > 0) {
          // toO = targetNet.getMember(connectTo.getGUID());
          startFromO = (an5Object)toAdd.get(i).clone();
    	  fromO = startFromO.getLast();
    	  bindings = fromO.bind(toO, targetNet.providesServices(), viaService, (ctrl.strategy & an5SearchControl.SearchOptions.BIND_UNIQUE) > 0);
    	  /* connectTo object already in network, so just add toAdd[i] object */
    	  if (bindings != null) {
            an5Path newP = new an5Path(startFromO, toO, bindings);
    	    targetNet.putCandidate(newP);
   			if (startFromO instanceof an5Path) {
  	          log.DBG(6, "<log.INFO>:AN5:an5JoinNetwork.getNextGoal: add candidate path[" +
  	                     newP.getPathLength() + "]: " + newP.getFirst().getGUID() + " >> " + newP.getLast().getGUID());
     		} else {
   	          log.ERR(6, "<log.INFO>:AN5:an5JoinNetwork.getNextGoal: add candidate: " +
   	            		 fromO.getGUID());
     		}
    	  } else {
   			if (startFromO instanceof an5Path) {
   			  an5Path fromPath = (an5Path)startFromO;
	          log.ERR(3, "<log.ERR>:AN5:an5JoinNetwork.getNextGoal: bind failed - from path[" +
	                     fromPath.getPathLength() + "]: " + startFromO.getFirst().getGUID() + " >> " + startFromO.getLast().getGUID() +
	                     " to: " + toO.getGUID());
   			} else {
 	          log.ERR(3, "<log.ERR>:AN5:an5JoinNetwork.getNextGoal: bind failed - from: " +
 	            		     fromO.getGUID() + " to: " + toO.getGUID());
   			}  
    	  }
        }
      }
      an5Object[][] addPaths = new an5Object[pathStats.expansionMultiplier][probeCnts.size()];
      if (probeCnts.size() > 0) {
        Collections.sort(probeCnts);
        probeCnts.get(0).chunkSize = probeCnts.get(0).cnt;
        probeCnts.get(0).increment = 1;  
        for (i = 1; i < probeCnts.size(); i++) {
          probeCnts.get(i).chunkSize = probeCnts.get(i).cnt *  probeCnts.get(i-1).chunkSize;
          probeCnts.get(i).increment = probeCnts.get(i).chunkSize / probeCnts.get(i).cnt; 
        }
        Collections.sort(probeCnts, new sortByIndex());
      }
      an5AvailableResource pool = null;
      /* Have: fixed network with success cases as "candidate"
       *        Now have alternates of:
       *           connecting "element" list
       *           available resources pool
       */
      for (int m = 0; m < probeCnts.size(); m++) {
    	i = probeCnts.get(m).idx;
        j = 0;
        pool = (an5AvailableResource)available.clone();
        pool.load();
     	fromO = (an5Object)toAdd.get(i);
     	foundPaths = finder.probePaths(fromO, joinNet.providesServices(), viaService, (ctrl.strategy & an5SearchControl.SearchOptions.BIND_UNIQUE) > 0, pool);
     	if (foundPaths.length <= probeCnts.get(m).cnt) {
     	  for (k = 0; k < pathStats.expansionMultiplier; k += probeCnts.get(m).increment) {
     		for (l = 0; l < probeCnts.get(m).increment; l++) {
     		  if (j < foundPaths.length) {
     	        addPaths[k+l][m] = foundPaths[j];
     		  } else {
     			addPaths[k+l][m] = null;
   			    int x = k+l;
     			if (fromO instanceof an5Path) {
     			  an5Path fromPath = (an5Path)fromO;
 	              log.ERR(3, "<log.INFO>:AN5:an5JoinNetwork.getNextGoal: Expected Paths[k+l=" + x + ", m=" + m +
 	            		     "]: " + probeCnts.get(m).cnt + " Got Paths: " + foundPaths.length + " from path[" + 
 	            		     fromPath.getPathLength() + "]: " + fromO.getFirst().getGUID() + " >> " + fromO.getLast().getGUID());
     			} else {
   	              log.ERR(3, "<log.INFO>:AN5:an5JoinNetwork.getNextGoal: Expected Paths[k+l=" + x + ", m=" + m +
   	            		     "]: " + probeCnts.get(m).cnt +
   	            		     " Got Paths: " + foundPaths.length + " from: " + fromO.getGUID());
     			}
     		  }
     		}
     		j = (j + 1) % probeCnts.get(m).cnt;
          }
        } else {
          log.ERR(3, "<log.ERR>:AN5:an5JoinNetwork.getNextGoal: Inconsistent Probe Path Counts: " + foundPaths.length + " vs : " + probeCnts.get(m).cnt);
        }
      }
      an5Object removed = null;
      List<an5Object> toJoin = null;
      boolean skip;
 	  for (k = 0; k < pathStats.expansionMultiplier; k++) {
 		skip = false;
 		toJoin = new ArrayList<>();
 		Map<String, an5Object> avail = available.copyMap();
 		for (l = 0; l < probeCnts.size(); l++) {
 		  if (addPaths[k][l] != null) {
 			an5Object adding = addPaths[k][l];
 		    removed = avail.remove(adding.getLast().getGUID());
 		    if (removed != null) {
 	 		  toJoin.add(adding);
 		    } else {
 		      skip = true; /* skip due to resource depletion */
 		      if (adding instanceof an5Path) {
 		    	an5Path addingPath = (an5Path)adding;
 	            log.ERR(3, "<log.INFO>:AN5:an5JoinNetwork.getNextGoal: Resource Depletion [k=" + k + ", l=" + l +
 		                "], attempted to consume path[" +
 		               addingPath.getPathLength() + "]: " + adding.getFirst().getGUID() + " >> " + adding.getLast().getGUID());
 		      } else {
 	 	        log.ERR(3, "<log.INFO>:AN5:an5JoinNetwork.getNextGoal: Resource Depletion k=" + ", l=" + l +
 	 	        		"], attempted to consume: " + adding.getGUID());
 		      }
 		    }
 		  }
 		}
 		if (! skip) {
		  int openBalance = toAdd.size() + joinNet.getMembersSize() + joinNet.getCandidatesSize();
		  int targetBalance = toJoin.size() + targetNet.getMembersSize() + targetNet.getCandidatesSize();
 		  if (openBalance == targetBalance) {
            orJoins.add(new an5SimpleGoal(new an5JoinNetwork(this, prototype, targetNet, connectTo, toJoin, avail), ctrl));
 	      }
 		  else {
 		    skip = true;
	 	    log.ERR(3, "<log.INFO>:AN5:an5JoinNetwork.getNextGoal: Wrong Balance[k=" + k + ",l=" + l +
	 	    		   "]: Open[" + openBalance + "] != Target[" + targetBalance +
	 	    		   "] - toAdd/toJoin[" + toAdd.size() + ", " + toJoin.size() +
 		               "] net members[" + joinNet.getMembersSize() + ", " + targetNet.getMembersSize() +
 		               "] net candidates[" + joinNet.getCandidatesSize() + ", " + targetNet.getCandidatesSize() + "]");
 		  }
 	    }
      }
      res = new an5OrGoal(orJoins, ctrl);
      status = an5SearchControl.SearchResult.SOLVING;
    }
    return res;
  }
  public int getPathStats(PathStats pStat, int[][] pathCnt, List<sortPathCnts> probeCnts,
		                  an5InterfaceFinder finder, boolean bindUnique) {
	int fail = 0;
	int i, j, k;
	pStat.minDirect = pathCnt.length;
    /* Before going to trouble of expanding paths make sure it is worth it.. */
	for (i = 0; i < pathCnt.length; i++) {
	  /* can we do direct bind to joint object */
	  j = toAdd.get(i).getLast().canBind(null, connectTo, joinNet.providesServices(), viaService, bindUnique);
	  if (j > 0) {
	    pathCnt[i][PathIdx.BIND] = j;
	    pathCnt[i][PathIdx.PROBE] = 0;
	    pathCnt[i][PathIdx.INDEX] = i;
	    pStat.minDirect = Integer.min(i, pStat.minDirect);
	    pStat.maxDirect = Integer.max(i, pStat.maxDirect);
	  } else { 
	    k = finder.canMatchInterface(toAdd.get(i).getLast(), joinNet.providesServices(), viaService, bindUnique, available);
	    if (k > 0) {
	      pathCnt[i][PathIdx.BIND] = 0;
	      pathCnt[i][PathIdx.PROBE] = k;
	      pathCnt[i][PathIdx.INDEX] = i;
	      probeCnts.add(new sortPathCnts(k, i));
	      pStat.alternates++;
	      pStat.expansionMultiplier = pStat.expansionMultiplier * k;
	    } else {
	      pathCnt[i][PathIdx.BIND] = -1;
	      pathCnt[i][PathIdx.PROBE] = -1;
	      pathCnt[i][PathIdx.INDEX] = i;
	      fail = -1;
	    }
	  }
	}
	return fail;
  }
  public int status() {
	return status;
  }
}
