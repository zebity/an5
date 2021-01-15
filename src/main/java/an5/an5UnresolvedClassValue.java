/**
 Unresolved Class.
 Needs to be resolved before parse is finished.
 
 @author John Hartley
 */
package an5;

public class an5UnresolvedClassValue extends an5ClassValue {
  an5TypeValue resolvedTo = null;
  an5UnresolvedClassValue(String target, String val) {
	super("class", val);
  }
}
