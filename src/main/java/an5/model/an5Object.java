/**
 an5 model base class.
   Needs to support:
     discovery
     interface binding
     Has Java class id, unique id [local to VM] and persistent unique id which persists and
     would be same over multiple VM instances
  @author John Hartley - Graphica Software/Dokmai Pty Ltd.
 */

package an5.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class an5Object implements an5ClassTemplate {
  public boolean abstractSpec = false;
  public boolean mandatory = false;
  public String[] uniqueId = new String[2];
  public String[] persistentUniqueId = new String[2];
  public Map<String, an5VariableInstance> AN5AT_vars = new HashMap<>();
  public an5Service AN5AT_serviceUnion = new an5ServiceMap();
  public an5InterfaceSignatureKeys AN5SG_sigKeyUnion = new an5InterfaceSignatureKeys();
  public an5VarUtil varUtil = new an5VarUtil();
  public String getGUID() {
	String res = null;
    if (persistentUniqueId[0] != null) {
      res = new String(persistentUniqueId[0] + "=" + persistentUniqueId[1]);
    } else if (uniqueId[0] != null) {
      res = new String(uniqueId[0] + "=" + uniqueId[1]);
    } else {
      uniqueId[0] = new String("java-oid");
      uniqueId[1] = new String(toString());
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
	return new an5ServiceList((an5ServiceMap)AN5AT_serviceUnion);  
  }
  public int canBind(an5Object o, an5InterfaceInstance i, an5Service netSrv, an5Service protoSrv) {
    int cnt = 0,
              match;
    an5MapIf lookUp = null;
    Set<Object> ifFnd = new HashSet<>();
        
    if (i == null) {
      for (an5MapIf ifInfo: o.AN5SG_sigKeyUnion.ifSet.values()) {
        lookUp = AN5SG_sigKeyUnion.ifSet.get(ifInfo.sigKey);
        if (lookUp != null) { 
          if (! ifFnd.contains(ifInfo.forInterface)) {
            match = ifInfo.forInterface.canBind(o, ifInfo.forInterface, netSrv, protoSrv);
            if (match > 0) {
        	  ifFnd.add(ifInfo.forInterface);
        	  cnt += match;
        	}
          }
        }
      }
    } else {
    
      int j = 0;
      boolean fndIf = false;
      while ((! fndIf) && j < i.interfaceDefinition.signatureKeys.size()) {
    	lookUp = AN5SG_sigKeyUnion.ifSet.get(i.interfaceDefinition.signatureKeys.get(j)[1]);
    	if (lookUp != null) {
    	  match = lookUp.forInterface.canBind(o, i, netSrv, protoSrv);
    	  fndIf = match > 0;
    	  cnt += match;
    	}
      }
    }
    return cnt;
  } 
  public an5Binding bind(an5Object o, an5InterfaceInstance i, an5Service netSrv, an5Service protoSrv) {
	an5Binding res = null;
    an5MapIf lookUp = null;
      
    if (o != null && i != null) {
      int j = 0;
      boolean fndIf = false;
      while ((! fndIf) && j < i.interfaceDefinition.signatureKeys.size()) {
        lookUp = AN5SG_sigKeyUnion.ifSet.get(i.interfaceDefinition.signatureKeys.get(j)[1]);
  	    if (lookUp != null) {
  	      res = lookUp.forInterface.bind(this, o, i, netSrv, protoSrv);
  	      fndIf = res != null;
  	    }
      }
    }
    return res;
  }
  public an5Binding[] bind(an5Object o, an5Service netSrv, an5Service protoSrv) {
	an5Binding[] res = null;
    an5MapIf lookUp = null;
    Set<Object> ifFnd = new HashSet<>();
    List<an5Binding> bindings = new LinkedList<>();   
    an5Binding bind = null;
	
    for (an5MapIf ifInfo: o.AN5SG_sigKeyUnion.ifSet.values()) {
      lookUp = AN5SG_sigKeyUnion.ifSet.get(ifInfo.sigKey);
      if (lookUp != null) { 
        if (! ifFnd.contains(ifInfo.forInterface)) {
          bind = ifInfo.ref.bind(o, ifInfo.forInterface, netSrv, protoSrv);
          if (bind != null) {
      	    ifFnd.add(ifInfo.forInterface);
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
  public an5Object() {}
  public an5Object(an5Object o) {
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
  public an5Object createInstance() {
    return null;
  }
}
