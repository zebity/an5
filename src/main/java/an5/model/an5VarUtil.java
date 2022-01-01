/**
 @what Class to support constructors and variables within an5Object
 
 @note Needs to be replaced with JSON based mechanism for dump/restore of class objects
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;

public class an5VarUtil {
  public void setConstrutArgs(JsonNode nd, an5Object obj) {
	JsonNode aNd = null, peakNd = null;
	String val;
    if (nd != null) {
      JsonNode ifNd = nd.get("reflects");
      if (ifNd != null) {
        for (Iterator<JsonNode> nxt = ifNd.elements(); nxt.hasNext();) {
    	  aNd = nxt.next();
          String nam = aNd.get("name").asText();
	      an5InterfaceTable v = obj.AN5AT_interfaces.get(nam);
	      if (v != null && v.instance instanceof an5InterfaceInstance) {
	        peakNd = aNd.get("template");
	        if (peakNd != null) {
	    	  ((an5InterfaceInstance)v.instance).setNameTemplate(peakNd.asText());
	        }
	        peakNd = aNd.get("policy");
	        if (peakNd != null && peakNd.asText().equals(an5InterfaceInstance.PolicyString[an5InterfaceInstance.Policy.STATIC])) {
	          int sz = aNd.get("size").asInt();
	          ((an5InterfaceInstance)v.instance).allocateBinding(sz);
	        }
	      }
	    }
      }
    }
  }
  public void setConstrutArgs(an5ConstructArguments args, an5Object obj) {
    for (String[] varInit: args.args) {
	  if (varInit.length > 1) {
	    an5InterfaceTable v = obj.AN5AT_interfaces.get(varInit[0]);
		if (v != null && v.instance instanceof an5InterfaceInstance) {
		  if (varInit.length >= 2) {
		    String num = varInit[1].substring(1, varInit[1].length()-1);
		    int sz = Integer.valueOf(num);
			an5InterfaceInstance ifVar = (an5InterfaceInstance)v.instance;
			if (varInit.length == 3) {
			  ifVar.setNameTemplate(varInit[2]);
			}
			ifVar.allocateBinding(sz);
		  }
		}
	  }
	}
  }
  public void copyVars(an5Object src, an5Object dest) {
	an5InterfaceInstance srcIV,
	                     destIV;
    an5InterfaceTable dv;
    for (an5InterfaceTable sv : src.AN5AT_interfaces.values()) {
	  if (sv.instance instanceof an5InterfaceInstance) {
		srcIV = (an5InterfaceInstance)sv.instance;
		dv = dest.AN5AT_interfaces.get(srcIV.var);
		if (dv.instance instanceof an5InterfaceInstance) {
		  destIV = (an5InterfaceInstance)dv.instance;
		  destIV.allocateBinding(dest, srcIV);
		}
      }
	}
  }
}