/*
  What: Symbol Table for "an5" compiler and
          runtime.
          This supports an5:
             classs, interface and
             package based namespace

  By: John Hartley - Graphica Software/Dokmai Pty Ltd
*/


package an5;

import java.util.HashMap;
import java.util.Map;

class SymbolTable {
  an5ModelContext root,
	              current;
  Map<String, an5ModelContext> packageContexts = new HashMap<>();
  an5TypeValue select(String key) {
	an5TypeValue res = null;
    an5ModelContext nxt = current;
    
    while (nxt != null) {
      res = nxt.identifier.get(key);
      if (res != null)
    	break;
      else
    	nxt = current.parentCxt;
    }
    return res;
  }
  an5TypeValue insert(String key, an5TypeValue val) {
	an5TypeValue res = null;
	
	res = current.identifier.get(key);
	if (res == null)
      current.identifier.put(key, val);
	
	return res;
  }	
}