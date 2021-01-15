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
import java.util.Map.Entry;

class an5SymbolTable {
  an5Logging log= new an5Logging();
  int diags = 5;
  an5ModelContext root,
                  current;
  Map<String, an5ModelContext> packageContexts = new HashMap<>();
  an5SymbolTable() {
    root = new an5ModelContext();
    packageContexts.put(".", root);
  }
  an5TypeValue select(String key) {
	an5TypeValue res = null;
    an5ModelContext nxt = current;
    log.DBG(diags, "<INFO> symtab.select[" + key + "]");
    
    while (nxt != null) {
      for (Entry<String, an5TypeValue> me: nxt.identifier.entrySet()) {
     	 boolean tst = key == me.getKey();
     	 log.DBG(diags, "<INFO> symtab - Map[" + me.getKey() +
    			 "]='" + me.getValue().getClass().getName() + "' key==me.key = " +
     			 tst + ".");

      }
      res = nxt.identifier.get(key);
      if (res != null)
    	break;
      else
    	nxt = nxt.parentCxt;
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