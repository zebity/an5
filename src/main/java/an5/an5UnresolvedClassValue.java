/**
 @what Unresolved Class.
 
 @note Needs to be resolved before parse is finished.
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5;

public class an5UnresolvedClassValue extends an5ClassValue {
  an5TypeValue resolvedTo = null;
  String target;
  an5UnresolvedClassValue(String targ, String val, String pack) {
	super(val, pack);
	target = targ;
  }
}
