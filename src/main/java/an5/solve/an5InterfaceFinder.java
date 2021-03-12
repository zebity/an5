package an5.solve;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import an5.model.an5Binding;
import an5.model.an5InterfaceMatch;
import an5.model.an5MapIf;
import an5.model.an5Object;
import an5.model.an5Path;
import an5.model.an5Service;

public class an5InterfaceFinder {
  /* from Available Interfaces */
  public int canMatchInterface(an5Object fromO, an5Service netSrv, an5Service protoSrvs, an5AvailableInterfaces availIf) {
    int cnt = 0,
    	bindCnt;
    List<an5MapIf> toIfs = null;
    Set<String> ifFnd = new HashSet<>();
    for (an5MapIf fromI: fromO.AN5SG_sigKeyUnion.ifSet.values()) {
      toIfs = availIf.ifCollection.get(fromI.sigKey);
      if (toIfs != null) {
    	for (an5MapIf toI: toIfs) {
    	  if (! ifFnd.contains(toI.forInterface.interfaceDefinition.getGUID())) {
    	    ifFnd.add(toI.forInterface.interfaceDefinition.getGUID());
      	    /* check interface match */
    		bindCnt = fromI.forInterface.canBind(fromO, toI.forInterface, netSrv, protoSrvs);
    	    cnt += bindCnt;
    	  }
    	}
      }
    }
	return cnt;
  }
  public an5Path[] probePaths(an5Object startO, an5Service netSrv, an5Service protoSrvs, an5AvailableInterfaces avail) {
    an5Path[] res = null;
    an5Object fromO;
    
    List<an5MapIf> candidateIf = null;
    Collection<Object> testIf = new HashSet<>();
    an5Binding binds = null;
    List<an5Binding> bindings = new LinkedList<>();
    
    if (startO instanceof an5Path) {
      fromO = (an5Object)startO.getLast().clone();
    } else {
      fromO = (an5Object)startO;
    }
    for (an5MapIf fromI: fromO.AN5SG_sigKeyUnion.ifSet.values()) {
      candidateIf = avail.ifCollection.get(fromI.sigKey);
      if (candidateIf != null) {
    	for (an5MapIf toI: candidateIf) {
    	  binds = fromI.forInterface.bind(fromO, toI.ref, toI.forInterface, netSrv, protoSrvs);
    	  if (binds != null) {
    		bindings.add(binds);
    	  }
    	}
      }
    }
    if (bindings.size() > 0) {
      res = new an5Path[bindings.size()];
      int i = 0;
      for (an5Binding b: bindings) {
    	res[i] = new an5Path(startO, b);
    	i++;
      }
    }
	return res;
  }
  /* from Available Resources */
  public int canMatchInterface(an5Object fromO, an5Service netSrv, an5Service protoSrvs, an5AvailableResource availRes) {
    int cnt = 0;
	availRes.load();
	cnt = canMatchInterface(fromO, netSrv, protoSrvs, availRes.availableInterface);
	return cnt;
  }
  public an5Path[] probePaths(an5Object fromO, an5Service netSrv, an5Service protoSrv, an5AvailableResource availRes) {
    an5Path[] res = null;
	availRes.load();
	res = probePaths(fromO, netSrv, protoSrv, availRes.availableInterface);
	return res;
  }
}
