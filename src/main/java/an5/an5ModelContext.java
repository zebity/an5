/*
  What: Manage scope/visibility of symbols for
          an5 model
          
  By: John Hartley - Graphica Software/Dokmai Pty Ltd
  
  Acknowledgement: I peeked at this: https://github.com/aperkaz/mini-java-symbol-table
                     to get initial structure, but did not base my code
                     on this as it does not include "package" support.
  */
  
package an5;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class an5ModelContext {
  an5ModelContext defaultCxt = null,
	              parentCxt = null;
  ArrayList<an5ModelContext> children = new ArrayList<>();
  Map<String, an5TypeValue> identifier = new HashMap<>();
  an5ModelContext() {
  } 
  /* an5ModelContext(an5ModelContext def) {
	defaultCxt = def;
    parentCxt = null;
  } */
  an5ModelContext(an5ModelContext d, an5ModelContext par) {
    defaultCxt = d;
    parentCxt = par;
  }
  an5ModelContext addChild() {
    an5ModelContext res = new an5ModelContext(defaultCxt, this);
    return res;
  }
  an5ModelContext getParent() {
    return parentCxt;
  }
}
