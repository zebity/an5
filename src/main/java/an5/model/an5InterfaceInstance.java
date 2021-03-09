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
  public int canBind(an5Object o, an5InterfaceInstance i, an5Service netSrv, an5Service protoSrv) {
    int res = 0;
    
	/* check interface match */
	an5InterfaceMatch match = interfaceDefinition.matchSignature(i.interfaceDefinition);
	if (match.sigMatch[match.sigMatch.length-1][0] == match.all &&
	    match.sigMatch[match.sigMatch.length-1][1] == match.all &&
		match.sigMatch[match.sigMatch.length-1][2] == match.all) {
	  res = 1;
	}
    
    return res;
  }
  public an5Binding bind(an5Object from, an5Object to, an5InterfaceInstance i, an5Service netSrv, an5Service protoSrv) {
	an5Binding res = null;
	boolean found = false;
	/* check interface match */
	an5InterfaceMatch match = interfaceDefinition.matchSignature(i.interfaceDefinition);
	
	if (match.sigMatch[match.sigMatch.length-1][0] == match.all &&
		match.sigMatch[match.sigMatch.length-1][1] == match.all &&
		match.sigMatch[match.sigMatch.length-1][2] == match.all) {
	  if (alloc == allocationPolicy.STATIC) {
		for (int j = 0; (! found) && (j < bindings.size()); j++) {
		  res = bindings.get(j);
		  if (res.state == an5Binding.bindState.OPEN) {
			found = true;
			res.bind(from, this, to, i, match);
		  }
		}
	  } else {
		int k = bindings.size();
		res = interfaceDefinition.getBinding(nameTemplate, k);
		res.bind(from, this, to, i, match);
	  }
	}
	return res;
  }
  public void setNameTemplate(String nt) {
    nameTemplate = new String(nt);
  }
}
