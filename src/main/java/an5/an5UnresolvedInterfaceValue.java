/**
 Unresolved Interface.
 Needs to be resolved before parse is finished.
 
 @author John Hartley
 */
package an5;

public class an5UnresolvedInterfaceValue extends an5InterfaceValue {
  an5TypeValue resolvedTo = null;
  an5UnresolvedInterfaceValue(String target, String val) {
	super("interface", val);
  }
}
