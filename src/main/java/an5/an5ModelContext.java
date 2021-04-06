/**
 @what Manage scope/visibility of symbols for an5 model
          
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
  
 @note Acknowledgement -  I peeked at this: https://github.com/aperkaz/mini-java-symbol-table
                          to get initial structure, but did not base my code
                          on this as it does not include "package" support.
*/
  
package an5;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

class an5ModelContext {
  an5ModelContext parentCxt = null,
	              rootCxt = null;
  String forPackage;
  List<an5ModelContext> children = new ArrayList<>();
  Map<String, an5TypeValue> identifier = new HashMap<>();
  an5ModelContext() {
  } 
  /* an5ModelContext(an5ModelContext def) {
	defaultCxt = def;
    parentCxt = null;
  } */
  an5ModelContext(String forPkg) {
	forPackage = new String(forPkg);
	rootCxt = this;
  }
  an5ModelContext(an5ModelContext par) {
    parentCxt = par;
    forPackage = par.forPackage;
    rootCxt = par.rootCxt;
  }
  an5ModelContext addChild() {
    an5ModelContext res = new an5ModelContext(this);
    return res;
  }
  an5ModelContext getParent() {
    return parentCxt;
  }
/*  an5ModelContext getRoot() {
	an5ModelContext res = this,
			        nxt = parentCxt;
    while (nxt != null) {
      nxt = nxt.parentCxt;
    }
    return res;
  } */
}
