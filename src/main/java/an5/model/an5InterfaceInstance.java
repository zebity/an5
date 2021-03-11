package an5.model;

import java.util.ArrayList;
import java.util.List;

public class an5InterfaceInstance extends an5VariableInstance {
  public enum allocationPolicy { STATIC, DYNAMIC};
  public an5Interface interfaceDefinition;
  List<an5Binding> bindings = new ArrayList<>();
  int min,
      max;
  allocationPolicy alloc = allocationPolicy.DYNAMIC;
  String nameTemplate;
  public an5InterfaceInstance(String varNm, an5Interface ifDef, int mn, int mx) {
	super(varNm);
    interfaceDefinition = ifDef;
    min = mn;
    max = mx;
  }
  public int allocateBinding(an5InterfaceInstance ifInst) {
	int i = 0;
    min = ifInst.min;
    max = ifInst.max;
    alloc = ifInst.alloc;
    // bindings = new ArrayList<>();
    for (;i < ifInst.bindings.size(); i++) {
      bindings.add(interfaceDefinition.getBinding(ifInst.bindings.get(i)));
    }
    return i;
  }
  public int allocateBinding(int sz, String Nm) {
    int i = 0,
        adj = sz;
    
    if (max > 0) {
      adj = Integer.min(max, sz);
    }
    alloc = allocationPolicy.STATIC;
    for (;i < adj; i++) {
      bindings.add(interfaceDefinition.getBinding(Nm, i));
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
	an5Binding res = null;
	boolean found = false;
	/* check interface match - fromI == this */
	an5InterfaceMatch match = interfaceDefinition.matchSignature(toI.interfaceDefinition);
	
	if (match.matchResult == an5InterfaceMatch.matchState.all ||
		match.matchResult == an5InterfaceMatch.matchState.partial) {
	  if (alloc == allocationPolicy.STATIC) {
		for (int j = 0; (! found) && (j < bindings.size()); j++) {
		  res = bindings.get(j);
		  if (res.state == an5Binding.bindState.OPEN) {
			found = true;
			res.bind(fromO, this, toO, toI, match);
		  }
		}
	  } else {
		int k = bindings.size();
		res = interfaceDefinition.getBinding(nameTemplate, k);
		res.bind(fromO, this, toO, toI, match);
	  }
	}
	return res;
  }
  public void setNameTemplate(String nt) {
    nameTemplate = new String(nt);
  }
}
