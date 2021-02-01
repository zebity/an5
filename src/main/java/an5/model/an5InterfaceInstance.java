package an5.model;

import java.util.ArrayList;
import java.util.List;

public class an5InterfaceInstance extends an5VariableInstance {
  static class allocationPolicy { static int STATIC = 0, DYNAMIC = 1; };
  an5Interface interfaceDefinition;
  List<an5Binding> bindings = new ArrayList<>();
  int min,
      max;
  int alloc = allocationPolicy.STATIC;
  public an5InterfaceInstance(String varNm, an5Interface ifDef, int mn, int mx) {
	super(varNm);
    interfaceDefinition = ifDef;
    min = mn;
    max = mx;
  }
  public int allocateBinding(int sz, String Nm) {
    int i = 0,
        adj = sz;
    
    if (max > 0) {
      adj = Integer.min(max, sz);
    }
    for (;i < adj; i++) {
      bindings.add(interfaceDefinition.getBinding(Nm, i));
    }
    return i;
  }
}
