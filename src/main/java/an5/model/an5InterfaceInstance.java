package an5.model;

import java.util.ArrayList;
import java.util.List;

public class an5InterfaceInstance extends an5VariableInstance {
  static class allocationPolicy { static int STATIC = 0, DYNAMIC = 1; };
  public an5Interface interfaceDefinition;
  List<an5Binding> bindings = new ArrayList<>();
  int min,
      max;
  int alloc = allocationPolicy.DYNAMIC;
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
      alloc = allocationPolicy.STATIC;
    }
    for (;i < adj; i++) {
      bindings.add(interfaceDefinition.getBinding(Nm, i));
    }
    return i;
  }
  public int canBind(an5Object o, an5InterfaceInstance i, an5Service netSrv, an5Service protoSrv) {
    int res = 0;
    return res;
  }
  public an5Binding bind(an5Object from, an5Object to, an5InterfaceInstance i, an5Service netSrv, an5Service protoSrv) {
	an5Binding res = null;
	
	/* check interface match */
	an5InterfaceMatch sigMatch = interfaceDefinition.matchSignature(i.interfaceDefinition);
	
	return res;
  }
}
