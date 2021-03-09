
package an5.model;

public class an5VarUtil {
  public void setConstrutArgs(an5ConstructArguments args, an5Object obj) {
    for (String[] varInit: args.args) {
	  if (varInit.length > 1) {
	    an5VariableInstance v = obj.AN5AT_vars.get(varInit[0]);
	    if (v instanceof an5InterfaceInstance) {
	      if (varInit.length > 2) {
	    	String num = varInit[1].substring(1, varInit[1].length()-1);
	    	int sz = Integer.valueOf(num);
		    an5InterfaceInstance ifVar = (an5InterfaceInstance)v;
		    ifVar.setNameTemplate(varInit[2]);
		    ifVar.allocateBinding(sz, varInit[2]);
	      }
	    }
	  }
	}
  }
  public void copyVars(an5Object src, an5Object dest) {
	an5InterfaceInstance sIfVar,
	                     dIfVar;
    an5VariableInstance dv;
    for (an5VariableInstance sv : src.AN5AT_vars.values()) {
	  if (sv instanceof an5InterfaceInstance) {
		sIfVar = (an5InterfaceInstance)sv;
		dv = dest.AN5AT_vars.get(sIfVar.var);
		if (dv instanceof an5InterfaceInstance) {
		  dIfVar = (an5InterfaceInstance)dv;
		  dIfVar.allocateBinding(sIfVar);
		}
      }
	}
  }
}