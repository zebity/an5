package an5.model;

import java.util.Map;

public class an5Network extends an5Object {
  public Map<String, an5Element> hasMembers;
  public an5Network() {  
  }
  public an5Network(an5ConstructArguments args) {  
  }
  public an5Service providesServices() {
    an5Service res = null;
	
    an5VariableInstance s = clVars.get("service");
    if (s != null) {
	  res =  ((an5Service)s).provides();
    }
	return res;  
  }
}
