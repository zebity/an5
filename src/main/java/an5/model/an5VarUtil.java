
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
		    ifVar.allocateBinding(sz, varInit[2]);
	      }
	    }
	  }
	}
  }
}