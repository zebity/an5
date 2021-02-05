package an5.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class an5Element extends an5Object {
  // Map<String, an5InterfaceInstance> interfaces = new HashMap<>();
  Collection<String> eleServices = new HashSet<>();
  boolean builtMap = false;
  
  public an5Element() {
  }
  public an5Element(an5ConstructArguments args) {  
  }
  public boolean[] providesService(an5Service mustProvide, an5Service canProvide) {
	boolean must = true,
			can = true;
	buildMap();
	for (int i = 0; i < mustProvide.size(); i++) {
	  must = must && eleServices.contains(mustProvide.getService(i));
	}
	for (int i = 0; i < canProvide.size(); i++) {
	  can = can && eleServices.contains(canProvide.getService(i));
    }
	return new boolean[]{must, can};
  }
  void buildMap() {
    if (! builtMap) {
      for (an5VariableInstance v : clVars.values()) {
    	if (v instanceof an5InterfaceInstance) {
    	  an5InterfaceInstance ifVar = (an5InterfaceInstance)v;
    	  for (int i = 0; i < ifVar.interfaceDefinition.signatureSet.size(); i++) {
    	    for (String s : ifVar.interfaceDefinition.getServiceSignature(i)) {
    		  eleServices.add(s);
    	    }
    	  }
    	}
      }
      builtMap = true;
    }
  }
}
