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
import java.util.Map;

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
  public boolean[] providesService(an5Service mustProvide, an5Service canProvide) {
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
  public an5Object() {}
  public an5Object(an5Object o) {
    abstractSpec = o.abstractSpec;
	mandatory = o.mandatory;
	o.getGUID();
	uniqueId = new String[]{new String(o.uniqueId[0]), new String(o.uniqueId[1])};
	persistentUniqueId = new String[]{new String(o.persistentUniqueId[0]), new String(o.persistentUniqueId[1])};
  }
  public an5Object createInstance() {
    return null;
  }
}
