package an5.model;

import java.util.ArrayList;
import java.util.List;

public class an5InterfaceInstance {
  static class allocationPolicy { static int STATIC = 0, DYNAMIC = 1; };
  an5Interface interfaceDefinition;
  List<an5Binding> bindings = new ArrayList<>();
  String var;
  int min,
      max;
  int alloc = allocationPolicy.STATIC;
  public an5InterfaceInstance(String varNm, an5Interface ifDef, int mn, int mx) {
    interfaceDefinition = ifDef;
    min = mn;
    max = mx;
  }
}
