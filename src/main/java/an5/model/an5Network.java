/**
 @what an5 network class contains the objects within network.
         members can can:
           regular an5Objects
           an5 Networks
           candidate members - which have uncommitted bindings
           
 @author John Hartley - Graphica Software/Dokmai Pty Ltd 
*/
package an5.model;

import java.io.PrintStream;
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
  int lazyCloneThreshold = 1000;
  int clonedMaps = 0;
  int updatingCount = 0;
  an5Network clonedFrom = null;
  Map<String, an5Network> clonedBy = new LinkedHashMap<>();
  public an5Network() {  
  }
  public an5Network(an5ConstructArguments args) {  
  }
  public an5Network(an5Network cn) {
    super(cn);
    
    int testSize = members.size() + 100 * memberNetworks.size() + 5 * candidates.size();
    if (testSize < lazyCloneThreshold) {
      for (an5Object o: cn.members.values()) {
    	members.put(o.getGUID(), (an5Object)o.clone());
      }
      for (an5Network no: cn.memberNetworks.values()) {
      	members.put(no.getGUID(), (an5Network)no.clone());
      }
      for (an5Object o: cn.candidates.values()) {
    	members.put(o.getGUID(), (an5Object)o.clone());
      }
    } else {
      cloned = true;
      clonedFrom = cn;
      cn.clonedBy.put(this.getGUID(), this);
    }
  }
  int decoupleClone(an5Network from, int trigger) {
    int res = 0;
    
    if (cloned) {
      if ((clonedMaps & Cloned.MEMBERS) == 0 && (trigger & Cloned.MEMBERS) > 0) {
  	    for (an5Object co : clonedFrom.getMembersValues()) {
  	      members.put(co.getGUID(), (an5Object)co.clone());
  	    }
	    clonedMaps = clonedMaps | Cloned.MEMBERS;
      }
    
      if ((clonedMaps & Cloned.CANDIDATES) == 0 && (trigger & Cloned.CANDIDATES) > 0) {
        for (an5Object co : clonedFrom.getCandidatesValues()) {
    	  candidates.put(co.getGUID(), (an5Object)co.clone());
         }
  	     clonedMaps = clonedMaps | Cloned.CANDIDATES;
      }
    
      if ((clonedMaps & Cloned.NETWORKS) == 0 && (trigger & Cloned.NETWORKS) > 0) {
        for (an5Network co : clonedFrom.getMemberNetworksValues()) {
      	  memberNetworks.put(co.getGUID(), (an5Network)co.clone());
         }
         clonedMaps = clonedMaps | Cloned.NETWORKS;
      }
    
      if ((clonedMaps & Cloned.ALL) == Cloned.ALL) {
        from.mapsDecoupled(this);
        cloned = false;
      }
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
  public void dumpJSON(PrintStream ps) {
	int i;
	boolean comma = false,
			ncomma = false;
	
	if (cloned) {
	  decoupleClone(clonedFrom, Cloned.ALL);
	}
	ps.println("{");
	ps.println("  \"an5Network\": {");
	ps.println("    \"class\": \"" + getClass().getSimpleName() + "\",");
	ps.println("    \"GUID\": \"" + getGUID() + "\",");
	ps.println("    \"services\": {");
	for (i = 0; i < AN5AT_serviceUnion.size(); i++) {
	  ps.println("      \"" + AN5AT_serviceUnion.getService(i) +  "\": {");
	  int[] cd = AN5AT_serviceUnion.getCardinality(i);
	  ps.println("        \"min\": " + cd[0] + ",");
	  ps.println("        \"max\": " + cd[0]);
	  ps.println("      }"); 
	}
	ps.println("    }");
	ps.println("    \"members\": {");
	for (an5Object o: members.values()) {
	  if (comma) {
	    ps.println(",");		  
	  }
	  ps.print("      \"" + o.getClass().getSimpleName() + "\": \"" + o.getGUID() + "\"");
	  comma = true;
	}
	if (comma) {
	  ps.println();	
	}
	ps.println("    }");
	comma = false;
	ps.println("    \"candidates\": {");
	for (an5Object o: candidates.values()) {
	  if (comma) {
	    ps.println(",");		  
	  }
	  ps.println("      \"" + o.getClass().getSimpleName() + "\": {");
	  an5Path po = (an5Path)o;
	  ncomma = false;
	  for (an5Object pmo: po.path.values()) {
		if (ncomma) {
		  ps.println(",");		  
		}
		ps.print("        \"" + pmo.getClass().getSimpleName() + "\": \"" + pmo.getGUID() + "\"");
	    ncomma = true;
	  }
	  if (ncomma) {
		ps.println();		  
	  }
	  ps.println("      }");
	}
	if (comma) {
	  ps.println();	
	}
	ps.println("    }");
	ps.println("  }");
	ps.println("}");
  }
}
