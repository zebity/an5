/**
 @what Unresolved Interface.
 
 @note Needs to be resolved before parse is finished.
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5;

public class an5UnresolvedInterfaceValue extends an5InterfaceValue {
  an5TypeValue resolvedTo = null;
  String target;
  an5UnresolvedInterfaceValue(String targ, String val, String pack) {
	super(val, pack);
	target = targ;
  }
}
