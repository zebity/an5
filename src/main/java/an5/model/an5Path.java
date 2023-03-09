/**
 @what A path consists of a set of an5Objects that are linked in chain via bound interfaces
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

public class an5Path extends an5Object {
  String an5name = "path";
  public Map<String, an5Object> path = new LinkedHashMap<>();
  String firstEntry = null,
         lastEntry;
  
  public an5Path(an5ClassTemplate t, boolean ab) {
	super(t, ab);
  }
  public an5Path(an5Object o, an5Binding b) {
	if (o instanceof an5Path) {
	  for (an5Object po : ((an5Path) o).path.values()) {
	    path.put(po.getGUID(), po);
	    if (firstEntry == null) {
	      firstEntry = new String(po.getGUID());
	    }
	  }
	} else {
      path.put(o.getGUID(), o);
      firstEntry = new String(o.getGUID());
	}
    path.put(b.bORef.getGUID(), b.bORef);
    lastEntry = new String(b.bORef.getGUID());
  }
  public an5Path(an5Object ao, an5Object bo, an5Binding[] bindings) {
    if (ao instanceof an5Path) {
	  for (an5Object po : ((an5Path) ao).path.values()) {
	    path.put(po.getGUID(), po);
	    if (firstEntry == null) {
		  firstEntry = new String(po.getGUID());
	    }
	  }
	} else {
	  path.put(ao.getGUID(), ao);
	  firstEntry = new String(ao.getGUID());
	}
    if (bo != null && bindings != null) {
      if (bindings.length > 0 && bindings[0].bORef == bo) {
	    path.put(bo.getGUID(), bo);
	    lastEntry = new String(bo.getGUID());
      } else {
    	lastEntry = new String(firstEntry); 
      }
    }
  }
  public an5Path(an5Path p) {
	int i = 0, k = 0;
	int[] locateBinding = null;
	an5Object cloneA = null, cloneB = null, priorO = null;
	an5Binding[] cloneLinksA;
	an5InterfaceTable cloneBIT;
	
	template = p.template;
	firstEntry = new String(p.firstEntry);
    lastEntry = new String(p.lastEntry);

    
    for (an5Object currentO : p.path.values()) {
      if (i == 0) {
        priorO = currentO;
        cloneA = (an5Object)priorO.clone();
        path.put(cloneA.getGUID(), cloneA);
      } else {
        cloneB = (an5Object)currentO.clone();
      
        cloneLinksA = cloneA.enumerateBindings(an5Binding.bindState.BOUND);
        /* cloneLinksB = cloneB.enumerateBindings(an5Binding.bindState.BOUND); */
        if (cloneLinksA != null) { /* bug fix - rebind BOUND only */
          for (an5Binding bd : cloneLinksA) {
            locateBinding = bd.bORef.locateBinding(bd);
            cloneBIT = cloneB.locateInterface(bd.bIRef.var, locateBinding);
            if (cloneBIT != null && locateBinding != null) {
              bd.reBind(cloneA, bd.aIRef, cloneB, cloneBIT.instance, cloneBIT.instance.bindings.get(locateBinding[1]));
            }
          }
        }
        path.put(cloneB.getGUID(), cloneB);
        priorO = currentO;
        cloneA = cloneB;
      }
      i++;
    }
  }
  public an5Path(JsonNode nd) {
    super(nd == null ? null : nd.get("extends"));
    /* Now traverse through pathList */
  }
  public Object clone() {
    return new an5Path(this);
  }
  public String getGUID() {
    String res = null;
	if (persistentUniqueId[0] != null) {
	  res = new String(persistentUniqueId[0] + "=" + persistentUniqueId[1]);
	} else if (uniqueId[0] != null) {
	  res = new String(uniqueId[0] + "=" + uniqueId[1]);
	} else {
	  uniqueId[0] = new String("java-oid");
	  StringBuilder pathStr = new StringBuilder(toString());
	  pathStr.append(">>");
	  pathStr.append(path.values().toArray()[0].toString());
	  uniqueId[1] = new String(pathStr.toString());
	  res = new String(uniqueId[0] + "=" + uniqueId[1]);
	}
	return res;
  }
  public an5Object getLast() {
    return path.get(lastEntry);
  }
  public an5Object getFirst() {
    return path.get(firstEntry);
  }
  public int getPathLength() {
    return path.size();
  }
  int commit() {
    int res = 0;
    return res;
  }
  void relinkBindings() {
  }
}
