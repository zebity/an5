/**
 @what an5 model base class.
   Needs to support:
     discovery
     interface binding
     Has Java class id, unique id [local to VM] and persistent unique id which persists and
     would be same over multiple VM instances
     
 @author John Hartley - Graphica Software/Dokmai Pty Ltd.
*/

package an5.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;

public class an5Object implements an5ClassTemplate {
  String an5name = "object";
  an5ClassTemplate template = null;
  public boolean abstractSpec = false;
  public boolean mandatory = false;
  public String[] uniqueId = new String[2];
  public String[] persistentUniqueId = new String[2];
  public Map<String, an5InterfaceTable> AN5AT_interfaces = new HashMap<>();
  public Map<String, an5VariableInstance> AN5AT_classes = new HashMap<>();
  public an5Service AN5AT_serviceUnion = new an5ServiceMap();
  public an5InterfaceSignatureKeys AN5SG_sigKeyUnion = new an5InterfaceSignatureKeys();
  public an5VarUtil varUtil = new an5VarUtil();
  public an5Object() {}
  public an5Object(an5ClassTemplate t, boolean ab) {
    template = t;
    abstractSpec = ab;
  }
  public an5Object(an5Object o) {
	template = o.template;
    abstractSpec = o.abstractSpec;
	mandatory = o.mandatory;
	o.getGUID();
	uniqueId = new String[]{new String(o.uniqueId[0]), new String(o.uniqueId[1])};
	if (o.persistentUniqueId[0] == null) {
	  persistentUniqueId = new String[2];
	} else {
	  persistentUniqueId = new String[]{new String(o.persistentUniqueId[0]), new String(o.persistentUniqueId[1])};
	}
  }
  public an5Object(JsonNode nd) {
	if (nd != null) {
      abstractSpec = nd.get("abstractSpec").asBoolean();
      mandatory = nd.get("mandatory").asBoolean();
      JsonNode idNd = nd.get("persistentUniqueId");
      if (idNd != null) {
        persistentUniqueId = new String[]{idNd.get(0).asText(), idNd.get(1).asText()};
      }
    }
  }
  public String getGUID() {
	String res = null;
    if (persistentUniqueId[0] != null) {
      res = new String(persistentUniqueId[0] + "=" + persistentUniqueId[1]);
    } else if (uniqueId[0] != null) {
      res = new String(uniqueId[0] + "=" + uniqueId[1]);
    } else {
      uniqueId[0] = new String("xid");
      uniqueId[1] = an5UIDGenerator.getNextUID(this);
      res = new String(uniqueId[0] + "=" + uniqueId[1]);
    }
    return res;
  }
  public Object clone() {
    Object res = null;
    try {
      res = super.clone();
    } catch (CloneNotSupportedException e) {
      // To Do
    }
    return res;
  }
  public an5Object getInstance(String nmPat, int i) {
    return null;
  }
  public an5Binding[] enumerateBindings(int whereState) {
    an5Binding[] res = null;
    List<an5Binding> links = new ArrayList<>();
    
    /* Should refactor to use reflection to walk up object and find interface vars directly */
    for (an5InterfaceTable var : AN5AT_interfaces.values()) {
      if (var.instance instanceof an5InterfaceInstance)	{
    	an5InterfaceInstance ifI = (an5InterfaceInstance)var.instance;
    	for (an5Binding bind : ifI.bindings) {
          if ((whereState & bind.state) != 0) {
            links.add(bind);
          }
    	}
      }
    }
    if (links.size() > 0) {
      res = new an5Binding[links.size()];
      for (int i = 0; i < res.length; i++) {
    	res[i] = links.get(i);
      }
    }
    return res;
  }
  public boolean[] checkServicesProvided(an5Service mustProvide, an5Service canProvide) {
	boolean must = true,
			can = true;
	for (int i = 0; i < mustProvide.size(); i++) {
	  must = must && AN5AT_serviceUnion.contains(mustProvide.getService(i));
	}
	for (int i = 0; i < canProvide.size(); i++) {
	  can = can && AN5AT_serviceUnion.contains(canProvide.getService(i));
    }
	return new boolean[]{must, can};
  }
  public an5Service providesServices() {
	/* Change to only use ServiceMap */
	return new an5ServiceMap((an5ServiceMap)AN5AT_serviceUnion);
  }
  public an5Service acceptsServices() {
	/* Initially only provide what template defines ... */
	an5Service res = null;
	if (template != null) {
	  res = template.expose();
	}
	return res;
  }
  public int[] locateBinding(an5Binding bd) {
    int[] res = null;
    boolean found = false;
    an5InterfaceTable useIf = null;
    an5Binding target = null;
    
    if (bd.aORef.equals(this)) {
      useIf = AN5AT_interfaces.get(bd.aIRef.var);
      target = bd;
    } else {
      useIf = AN5AT_interfaces.get(bd.bIRef.var);
      target = bd.boundTo;
    }
    
    if (useIf != null) {
      for (int i = 0; (! found) && i < useIf.instance.bindings.size(); i++) {
        found = useIf.instance.bindings.get(i).equals(target);
        if (found) {
          res = new int[]{useIf.id, i};	
        }
      }
    }
    return res;
  }
  public an5InterfaceTable locateInterface(String nm, int[] hint) {
    an5InterfaceTable res = null, found;
    
    found = AN5AT_interfaces.get(nm);
    if (found != null) {
      if (hint != null) {
    	if (found.id == hint[0] && found.instance.bindings.size() > hint[1]) {
    	  res = found;
    	}
      } else {
        res = found;
      }
    }
    return res;
  }
  public int canBind(an5InterfaceInstance viaI, an5Object toO, an5Service netSrv, an5Service protoSrv,
		             boolean removeLocalEquivalents, boolean bindUnique) {
	/* Note that netSrv & protoSrv are hints to help select across multiple bind options */
    int cnt = 0,
              match;
    an5MapIf fromI = null;
    Set<Object> foundIf = new HashSet<>();
        
    if (viaI == null) {
      for (an5MapIf toI: toO.AN5SG_sigKeyUnion.ifSet.values()) {
        fromI = AN5SG_sigKeyUnion.ifSet.get(toI.sigKey);
        if (fromI != null) { 
          if (! foundIf.contains(fromI.forInterface)) {
            match = fromI.forInterface.canBind(this, toI.forInterface, netSrv, protoSrv);
            if (match > 0) {
        	  foundIf.add(fromI.forInterface);
        	  cnt += match;
        	}
          }
        }
      }
    } else {
    
      an5MapIf toI = null;
      /* Verify Interface */
      fromI = AN5SG_sigKeyUnion.ifSet.get(viaI.interfaceDefinition.getLastKey());
      if (fromI != null) {
    	for (String[] fromKey : viaI.interfaceDefinition.signatureKeys.values()) {          
    	  toI = toO.AN5SG_sigKeyUnion.ifSet.get(fromKey[1]);
    	  if (toI != null) {
            if (! foundIf.contains(toI.forInterface)) {
              match = fromI.forInterface.canBind(this, toI.forInterface, netSrv, protoSrv);
              if (match > 0) {
              	foundIf.add(toI.forInterface);
              	cnt += match;
              }
    	    }
    	  }
        }
      }
    }
    return cnt;
  } 
  /* public an5Binding bind(an5Object toO, an5InterfaceInstance toI, an5Service netSrv, an5Service protoSrv) {
	an5Binding res = null;
    an5MapIf fromI = null;
      
    if (toO != null && toI != null) {
      int j = 0;
      boolean fndIf = false;
      while ((! fndIf) && j < toI.interfaceDefinition.signatureKeys.size()) {
        fromI = AN5SG_sigKeyUnion.ifSet.get(toI.interfaceDefinition.signatureKeys.get(j)[1]);
  	    if (this != null) {
  	      res = fromI.forInterface.bind(this, toO, toI, netSrv, protoSrv);
  	      fndIf = res != null;
  	    }
  	    j++;
      }
    }
    return res;
  } */
  public an5Binding[] bind(an5Object toO, an5Service netSrv, an5Service protoSrv,
		                   boolean removeLocalEquivalents, boolean bindUnique) {
	an5Binding[] res = null;
    an5MapIf fromI = null;
    Set<Object> ifFound = new HashSet<>();
    List<an5Binding> bindings = new LinkedList<>();   
    an5Binding bind = null;
	
    for (an5MapIf ifInfo: toO.AN5SG_sigKeyUnion.ifSet.values()) {
      fromI = AN5SG_sigKeyUnion.ifSet.get(ifInfo.sigKey);
      if (fromI != null) { 
        if (bindUnique && (! ifFound.contains(ifInfo.forInterface))) {
          /* Only bind to interface if we have not done so before */
          bind = fromI.forInterface.bind(this, toO, ifInfo.forInterface, netSrv, protoSrv);
          if (bind != null) {
      	    ifFound.add(ifInfo.forInterface);
      	    bindings.add(bind);
      	  }
        } else {
          bind = fromI.forInterface.bind(this, toO, ifInfo.forInterface, netSrv, protoSrv);
          if (bind != null) {
        	bindings.add(bind);
          }
        }
      }
    }
    if (bindings.size() > 0) {
      res = new an5Binding[bindings.size()];
      for (int i = 0; i < bindings.size(); i++) {
    	res[i] = bindings.get(i);
      }
    }
    return res;
  }
  public an5Object createInstance() {
    return null;
  }
  public an5Object getFirst() {
	return this;
  }
  public an5Object getLast() {
	return this;
  }
  public String setValue(String nam, String val) {
    return null;
  }
  public String getValue(String nam) {
    return null;
  }
  public an5Service expose() {
    an5Service res = null;
    if (template != null) {
      res = template.expose();
    }
    return res;
  }
}
