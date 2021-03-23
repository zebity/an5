package an5.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class an5Network extends an5Object {
  public static class Cloned { public static final int MEMBERS = 0x01, NETWORKS = 0x02, CANDIDATES = 0x04, ALL = 0x07; }
  Map<String, an5Object> members = new HashMap<>();
  Map<String, an5Network> memberNetworks = new HashMap<>();
  Map<String, an5Object> candidates = new HashMap<>();
  boolean cloned = false;
  int clonedMaps = 0;
  int updatingCount = 0;
  an5Network clonedFrom = null;
  Map<String, an5Network> clonedBy = new LinkedHashMap<>();
  public an5Network() {  
  }
  public an5Network(an5ConstructArguments args) {  
  }
  public an5Network(an5Network n) {
    super(n);
    cloned = true;
    clonedFrom = n;
    n.clonedBy.put(this.getGUID(), this);
  }
  int decoupleClone(an5Network from, int trigger) {
    int res = 0;
    
    if ((trigger & Cloned.MEMBERS) > 0) {
  	  for (an5Object co : clonedFrom.members.values()) {
  	    members.put(co.getGUID(), (an5Object)co.clone());
  	    clonedMaps = clonedMaps | Cloned.MEMBERS;
  	  }      
    }
    
    if ((trigger & Cloned.CANDIDATES) > 0) {
      for (an5Object co : clonedFrom.candidates.values()) {
    	candidates.put(co.getGUID(), (an5Object)co.clone());
    	clonedMaps = clonedMaps | Cloned.CANDIDATES;
       }      
    }
    
    if ((trigger & Cloned.NETWORKS) > 0) {
      for (an5Network co : clonedFrom.memberNetworks.values()) {
      	candidates.put(co.getGUID(), (an5Object)co.clone());
      	clonedMaps = clonedMaps | Cloned.NETWORKS;
       }      
    }
    
    if ((clonedMaps & Cloned.ALL) == Cloned.ALL) {
      from.mapsDecoupled(this);
    }
    return res;
  }
  public void mapsDecoupled(an5Network me) {
  /* Decouple cloned network from master */
    clonedBy.remove(me.getGUID());
  }
  public an5Object getMember(String id) {
    an5Object obj = null;
    
	if (cloned && ((clonedMaps & Cloned.MEMBERS) == 0)) {
	  obj = clonedFrom.getMember(id);
	} else {
	  obj = members.get(id);
	}
	return obj;
  }
  public int getMemberSize() {
	int res;;
	    
	if (cloned && ((clonedMaps & Cloned.MEMBERS) == 0)) {
	  res = clonedFrom.getMemberSize();
	} else {
	  res = members.size();
	}
	return res;
  }
  public an5Object putMember(an5Object o) {
	an5Object obj = null;
		    
	if (cloned && ((clonedMaps & Cloned.MEMBERS) == 0)) {
	  for (an5Object mo : clonedFrom.members.values()) {
		members.put(mo.getGUID(), (an5Object)mo.clone());
		clonedMaps = clonedMaps | Cloned.MEMBERS;
      }
	}
		
	if (clonedBy.size() > 0) {
	  for (an5Network updateO : clonedBy.values()) {
		updateO.decoupleClone(this, Cloned.MEMBERS); 
	  }
	}
		
	obj = members.put(o.getGUID(), o);
		
	return obj;
  }
  public an5Object putCandidate(an5Object o) {
    an5Object obj = null;
	    
	if (cloned && ((clonedMaps & Cloned.CANDIDATES) == 0)) {
	  for (an5Object co : clonedFrom.candidates.values()) {
	    candidates.put(co.getGUID(), (an5Object)co.clone());
	    clonedMaps = clonedMaps | Cloned.CANDIDATES;
	  }
	}
	
	if (clonedBy.size() > 0) {
	  for (an5Network updateO : clonedBy.values()) {
		updateO.decoupleClone(this, Cloned.CANDIDATES); 
	  }
	}
	
	obj = candidates.put(o.getGUID(), o);
	
	return obj;
  }
}
