package an5.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class an5Path extends an5Object {
  public Map<String, an5Object> path = new LinkedHashMap<>();
  String firstEntry = null,
         lastEntry;
  
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
  public an5Path(an5Path p) {
	int i = 0, k = 0;
	an5Object cloneA = null, cloneB = null, priorO = null;
	an5Binding[] cloneLinksA, cloneLinksB;
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
        cloneLinksB = cloneB.enumerateBindings(an5Binding.bindState.BOUND);
        for (an5Binding bd : cloneLinksA) {
          if (bd.bORef.getGUID().equals(cloneB.getGUID())) {
        	for (k = 0; k < bd.bIRef.bindings.size(); k++) {
        	  if (bd.bIRef.bindings.get(k).equals(bd.boundTo)) {
                bd.reBind(cloneA, bd.aIRef, cloneB, cloneLinksB[k].aIRef, cloneLinksB[k]);
        	  }
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
  int commit() {
    int res = 0;
    return res;
  }
  void relinkBindings() {
  }
}
