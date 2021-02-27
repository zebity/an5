package an5.solve;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import an5.model.an5Binding;
import an5.model.an5MapIf;
import an5.model.an5Object;

public class an5AvailableInterfaces {
  Map<String, List<an5MapIf>> ifCollection;
  an5AvailableInterfaces() {
	ifCollection = new HashMap<>() ;  
  }
  public void available(an5Object o) {
   for (an5MapIf k : o.AN5SG_sigKeyUnion.ifSet.values()) {
	 List<an5MapIf> listO = ifCollection.get(k.sigKey);
	 if (listO == null) {
	   listO = new LinkedList<>();
	 }
	 listO.add(new an5MapIf(new String(k.sigKey),
               new String(k.ifType), k.forInterface, o));
     ifCollection.put(k.sigKey, listO); 
   }
  }
  public void notAvailable(an5Binding[] b) {
  }
}
