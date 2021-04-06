/**
 @what an5 network class contains the objects within network.
         members can can:
           regular an5Objects
           an5 Networks
           candidate members - which have uncommitted bindings
           
 @author John Hartley - Graphica Software/Dokmai Pty Ltd 
*/
package an5.model;

import java.util.Collection;
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
  	  for (an5Object co : clonedFrom.getMembersValues()) {
  	    members.put(co.getGUID(), (an5Object)co.clone());
  	  }
	  clonedMaps = clonedMaps | Cloned.MEMBERS;
    }
    
    if ((trigger & Cloned.CANDIDATES) > 0) {
      for (an5Object co : clonedFrom.getCandidatesValues()) {
    	candidates.put(co.getGUID(), (an5Object)co.clone());
       }
  	  clonedMaps = clonedMaps | Cloned.CANDIDATES;
    }
    
    if ((trigger & Cloned.NETWORKS) > 0) {
      for (an5Network co : clonedFrom.getMemberNetworksValues()) {
      	candidates.put(co.getGUID(), (an5Object)co.clone());
       }
       clonedMaps = clonedMaps | Cloned.NETWORKS;
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
  public Collection<an5Network> getMemberNetworksValues() {
    if (cloned && ((clonedMaps & Cloned.NETWORKS) == 0)) {
	  return clonedFrom.getMemberNetworksValues();
	} else {
	  return memberNetworks.values();
	}
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
  public int getMembersSize() {
	int res;;
	    
	if (cloned && ((clonedMaps & Cloned.MEMBERS) == 0)) {
	  res = clonedFrom.getMembersSize();
	} else {
	  res = members.size();
	}
	return res;
  }
  public Collection<an5Object> getMembersValues() {
    if (cloned && ((clonedMaps & Cloned.MEMBERS) == 0)) {
	  return clonedFrom.getMembersValues();
	} else {
	  return members.values();
	}
  }
  public an5Object putMember(an5Object o) {
	an5Object obj = null;
		    
	if (cloned && ((clonedMaps & Cloned.MEMBERS) == 0)) {
	  for (an5Object mo : clonedFrom.getMembersValues()) {
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
  public Collection<an5Object> getCandidatesValues() {
    if (cloned && ((clonedMaps & Cloned.CANDIDATES) == 0)) {
      return clonedFrom.getCandidatesValues();
    } else {
      return candidates.values();
    }
  }
  public an5Object putCandidate(an5Object o) {
    an5Object obj = null;
	    
	if (cloned && ((clonedMaps & Cloned.CANDIDATES) == 0)) {
	  for (an5Object co : clonedFrom.getCandidatesValues()) {
	    candidates.put(co.getGUID(), (an5Object)co.clone());
	  }
	  clonedMaps = clonedMaps | Cloned.CANDIDATES;
	}
	
	if (clonedBy.size() > 0) {
	  for (an5Network updateO : clonedBy.values()) {
		updateO.decoupleClone(this, Cloned.CANDIDATES); 
	  }
	}
	
	obj = candidates.put(o.getGUID(), o);
	
	return obj;
  }
  public int getCandidatesSize() {
	int res;;
	    
	if (cloned && ((clonedMaps & Cloned.CANDIDATES) == 0)) {
	  res = clonedFrom.getCandidatesSize();
	} else {
	  res = candidates.size();
	}
	return res;
  }
}
