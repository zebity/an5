package an5.model;

import java.util.HashMap;
import java.util.Map;

public class an5Network extends an5Object {
  public Map<String, an5Object> members = new HashMap<>();
  public Map<String, an5Network> memberNetworks = new HashMap<>();
  public Map<String, an5Object> candidates = new HashMap<>();
  public an5Network() {  
  }
  public an5Network(an5ConstructArguments args) {  
  }
  public an5Network(an5Network n) {
    super(n);
  }
}
