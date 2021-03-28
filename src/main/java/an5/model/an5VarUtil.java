
package an5.model;

public class an5VarUtil {
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