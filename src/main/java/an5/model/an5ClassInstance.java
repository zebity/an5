package an5.model;

import java.util.ArrayList;
import java.util.List;

public class an5ClassInstance extends an5VariableInstance {
  static class allocationPolicy { static int STATIC = 0, DYNAMIC = 1; };
  an5Object objectDefinition;
  List<an5Object> instances = new ArrayList<>();
  int min,
      max;
  boolean mandatory = false;
  int alloc = allocationPolicy.DYNAMIC;
  public an5ClassInstance(String varNm, an5Object ifDef, int mn, int mx, boolean man) {
	super(varNm);
    objectDefinition = ifDef;
    min = mn;
    max = mx;
    mandatory = man;
  }
  public int allocateInstance(int sz, String Nm) {
    int i = 0,
        adj = sz;
    
    if (max > 0) {
      adj = Integer.min(max, sz);
      alloc = allocationPolicy.STATIC;
    }
    for (;i < adj; i++) {
      instances.add(objectDefinition.getInstance(Nm, i));
    }
    return i;
  }
}
