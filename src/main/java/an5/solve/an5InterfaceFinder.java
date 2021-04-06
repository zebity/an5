/**
 @what Network interface finder help class. Pattern is to do all walking as:
         From to Target so:
           From - expands as set of Paths
           To - stays fixed
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import an5.model.an5Binding;
import an5.model.an5MapIf;
import an5.model.an5Object;
import an5.model.an5Path;
import an5.model.an5Service;

public class an5InterfaceFinder {
  /* from Available Interfaces */
  public int canMatchInterface(an5Object fromO, an5Service netSrv, an5Service protoSrvs, boolean bindUnique, an5AvailableInterfaces availIf) {
    int cnt = 0,
    	bindCnt;
    boolean found = false;
    List<an5MapIf> toIfs = null;
    Set<String> ifFound = new HashSet<>();
    
    do {
      found = false;
      for (an5MapIf fromI: fromO.AN5SG_sigKeyUnion.ifSet.values()) {
        toIfs = availIf.ifCollection.get(fromI.sigKey);
        if (toIfs != null) {
          /* no check against object, as cloning it to make unique has not effect */
          String fromOfromI = new String(fromI.forInterface.interfaceDefinition.getClass().toString());
    	  if (bindUnique && ! ifFound.contains(fromOfromI)) {
    	    for (an5MapIf toI: toIfs) {
              String fromItoO = new String(fromI.forInterface.interfaceDefinition.getClass().toString() + "__" +
            		                       toI.ref.getGUID()); 	    	
    	      if (! (ifFound.contains(fromItoO))) {
      	        /* check interface match */
    		    bindCnt = fromI.forInterface.canBind(fromO, toI.forInterface, netSrv, protoSrvs);
    		    if (bindCnt > 0) {
    	          cnt += bindCnt;
    	          if (! ifFound.contains(fromOfromI)) {
    	    	    ifFound.add(fromOfromI);
    	          }
    	    	  ifFound.add(fromItoO);
    	          found = true;
    		    }
    	      }
    	    }
    	  } else if (! bindUnique) {
            for (an5MapIf toI: toIfs) {
          	  bindCnt = fromI.forInterface.canBind(fromO, toI.forInterface, netSrv, protoSrvs);
          	  cnt += bindCnt;
    	    }
    	  }
        }
      }
    } while (found);
	return cnt;
  }
  public an5Path[] probePaths(an5Object startO, an5Service netSrv, an5Service protoSrvs, boolean bindUnique, an5AvailableInterfaces avail) {
    an5Path[] res = null;
    an5Object pathStartO, fromO; 
    
    List<an5MapIf> candidateIf = null;
    Collection<String> testIf = new HashSet<>();
    an5Binding binds = null;
    List<an5Binding> bindings = new LinkedList<>();
    List<an5Object> startWith = new LinkedList<>();
    boolean found;
    int bindingPerStart = 0;
    
    do {
      found = false;
      if (startO instanceof an5Path) {
        pathStartO = (an5Object)startO.clone();
        fromO = pathStartO.getLast();
      } else {
        pathStartO = fromO = (an5Object)startO.clone();
      }
    
      for (an5MapIf fromI: fromO.AN5SG_sigKeyUnion.ifSet.values()) {
        candidateIf = avail.ifCollection.get(fromI.sigKey);
        if (candidateIf != null) {
          String fromOfromI = new String(fromO.toString() + "__" + fromI.forInterface.interfaceDefinition.getClass().toString());
          if (bindUnique && ! testIf.contains(fromOfromI)) {
    	    for (an5MapIf toI: candidateIf) {
              String fromItoO = new String(fromI.forInterface.interfaceDefinition.getClass().toString() + "__" +
	                                       toI.ref.getGUID()); 	
    	      if (! (testIf.contains(fromOfromI) || testIf.contains(fromItoO))) {
    	        binds = fromI.forInterface.bind(fromO, toI.ref, toI.forInterface, netSrv, protoSrvs);
    	        if (binds != null) {
    		      bindings.add(binds);
    		      startWith.add(pathStartO);
    		      found = true;
    		      bindingPerStart = 1;
    		      if (! testIf.contains(fromOfromI)) {
    		        testIf.add(fromOfromI);
    		      }
    		      testIf.add(fromItoO);
    	        }
    	      }
    	    }
    	  } else if (! bindUnique) {
      	    for (an5MapIf toI: candidateIf) {
      	      binds = fromI.forInterface.bind(fromO, toI.ref, toI.forInterface, netSrv, protoSrvs);
      	      if (binds != null) {
      		    bindings.add(binds);
      		    if (startWith.size() == 0) {
      		      startWith.add(pathStartO);
      		    }
      	      }    	    	
    	    }
    	  }
        }
      }
    } while (found); 
    if (bindings.size() > 0) {
      res = new an5Path[bindings.size()];
      int i = 0,
    	  j = 0;
      for (an5Binding b: bindings) {
    	res[i] = new an5Path(startWith.get(j), b);
    	i++;
    	j += bindingPerStart;
      }
    }
	return res;
  }
  /* from Available Resources */
  public int canMatchInterface(an5Object fromO, an5Service netSrv, an5Service protoSrvs, boolean bindUnique, an5AvailableResource availRes) {
    int cnt = 0;
	availRes.load();
	cnt = canMatchInterface(fromO, netSrv, protoSrvs, bindUnique, availRes.availableInterface);
	return cnt;
  }
  public an5Path[] probePaths(an5Object fromO, an5Service netSrv, an5Service protoSrv, boolean bindUnique, an5AvailableResource availRes) {
    an5Path[] res = null;
	availRes.load();
	res = probePaths(fromO, netSrv, protoSrv, bindUnique, availRes.availableInterface);
	return res;
  }
}
