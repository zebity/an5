/**
 @what Holds the instantiated interface of interface and its bindings
         Bindings can be:
           STATIC - pre-allocated or
           DYNAMIC - managed dynamically

 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import java.util.ArrayList;
import java.util.List;

public class an5InterfaceInstance extends an5VariableInstance {
  public static class Policy { public static final int STATIC = 0, DYNAMIC = 1; };
  public an5Interface interfaceDefinition;
  public List<an5Binding> bindings = new ArrayList<>();
  public int min,
             max;
  public int policy = Policy.DYNAMIC;
  public static String[] PolicyString = { "STATIC", "DYNAMIC"};
  public an5InterfaceInstance(String varNm, an5Interface ifDef, int mn, int mx) {
	super(varNm);
    interfaceDefinition = ifDef;
    min = mn;
    max = mx;
  }
  public int allocateBinding(an5Object oA, an5InterfaceInstance ifInst) {
	an5Binding newBinding = null;
	int i = 0;
    min = ifInst.min;
    max = ifInst.max;
    policy = ifInst.policy;
    // bindings = new ArrayList<>();
    for (;i < ifInst.bindings.size(); i++) {
      newBinding = interfaceDefinition.getBinding(ifInst.bindings.get(i));
      if ((newBinding.state & an5Binding.bindState.BOUND) != 0) {
        newBinding.aORef = oA;
        newBinding.aIRef = this;
      }
      bindings.add(newBinding);
    }
    return i;
  }
  public int allocateBinding(int sz) {
    int i = 0,
        adj = sz;
    
    if (max > 0) {
      adj = Integer.min(max, sz);
    }
    policy = Policy.STATIC;
    for (;i < adj; i++) {
      bindings.add(interfaceDefinition.getBinding(i));
    }
    return i;
  }
  public int canBind(an5Object fromO, an5InterfaceInstance toI, an5Service netSrv, an5Service protoSrv) {
    int res = 0;
    
	/* check interface match - fromI == this */
	an5InterfaceMatch match = interfaceDefinition.matchSignature(toI.interfaceDefinition);
	if (match.matchResult == an5InterfaceMatch.matchState.all ||
		match.matchResult == an5InterfaceMatch.matchState.partial) {
	  res = 1;
	}
    
    return res;
  }
  public an5Binding bind(an5Object fromO, an5Object toO, an5InterfaceInstance toI, an5Service netSrv, an5Service protoSrv) {
	an5Binding resFrom = null,
			   resTo = null,
	           next;
	boolean found = false;
	/* check interface match - fromI == this */
	an5InterfaceMatch match = interfaceDefinition.matchSignature(toI.interfaceDefinition);
	
	if (match.matchResult == an5InterfaceMatch.matchState.all ||
		match.matchResult == an5InterfaceMatch.matchState.partial) {
	  if (policy == Policy.STATIC) {
		for (int j = 0; (! found) && (j < bindings.size()); j++) {
		  next = bindings.get(j);
		  if (next.state == an5Binding.bindState.OPEN) {
			found = true;
			resTo = toI.getAvailableBinding(next);
			if (resTo != null) {
			  next.bind(fromO, this, toO, toI, resTo, match);
			  resFrom = next;
			}
		  }
		}
	  } else {
		int k = bindings.size();
		next = interfaceDefinition.getBinding(k);
		resTo = toI.getAvailableBinding(next);
		if (resTo != null) {
		  next.bind(fromO, this, toO, toI, resTo, match);
		  resFrom = next;
		}
	  }
	}
	return resFrom;
  }
  public an5Binding getAvailableBinding(an5Binding toLink) {
	an5Binding res = null,
			   next;
	boolean found = false;
	
	if (policy == Policy.STATIC) {
	  for (int j = 0; (! found) && (j < bindings.size()); j++) {
	    next = bindings.get(j);
		if (next.state == an5Binding.bindState.OPEN && next != toLink) {
		  found = true;
		  res = next;
		 }
	  }
	} else {
	  int k = bindings.size();
	  res = interfaceDefinition.getBinding(k);
	  bindings.add(res);
	}
	return res;  
  }
  public void setNameTemplate(String nt) {
    interfaceDefinition.bindingNameTemplate = new String(nt);
  }
}
