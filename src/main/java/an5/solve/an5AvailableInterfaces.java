package an5.solve;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import an5.model.an5Binding;
import an5.model.an5InterfaceMatch;
import an5.model.an5MapIf;
import an5.model.an5Object;
import an5.model.an5Path;
import an5.model.an5Service;

public class an5AvailableInterfaces {
  public Map<String, List<an5MapIf>> ifCollection;
  public an5AvailableInterfaces() {
	ifCollection = new HashMap<>();  
  }
  public an5AvailableInterfaces(an5AvailableInterfaces from) {
	ifCollection = new HashMap<>();
	an5Object ref = null;
	Collection<String> got = new HashSet<>();
	for (List<an5MapIf> ifs : from.ifCollection.values()) {
	  for (an5MapIf ifMap : ifs) {
		ref = ifMap.ref;
	    if (ref != null) {
	      if (! got.contains(ref.getGUID())) {
	        got.add(ref.getGUID());
	        available((an5Object)ref.clone());
	      }
	    }
	  }
	}
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
  public Object clone() {
	an5AvailableInterfaces cl = new an5AvailableInterfaces(this);
    return cl;
  }
}
