package an5.solve;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import an5.model.an5InterfaceSignatureKeys;
import an5.model.an5Object;

public class an5AvailableInterfaces {
  Map<String, List<an5Object[]>> ifCollection;
  an5AvailableInterfaces() {
	ifCollection = new HashMap<>() ;  
  }
  public void available(an5Object o) {
   for (an5InterfaceSignatureKeys.mapIf k : o.AN5SG_sigKeyUnion.ifSet.values()) {
  
   }
  }
  public void notAvailable(an5Object o) {
	  
  }
}
