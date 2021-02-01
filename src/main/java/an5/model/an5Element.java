package an5.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class an5Element extends an5Object {
  Map<String, an5InterfaceInstance> interfaces = new HashMap<>();
  
  public int[] canProvide(List<an5Service> serv) {
    return null;
  }
  public an5Element() {
  }
  public an5Element(an5ConstructArguments args) {  
  }
}
