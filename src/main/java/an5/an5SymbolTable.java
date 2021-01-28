/*
  What: Symbol Table for "an5" compiler and
          runtime.
          This supports an5:
             classs, interface and
             package based namespace

  By: John Hartley - Graphica Software/Dokmai Pty Ltd
*/


package an5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
// import java.util.Map.Entry;

class an5SymbolTable {
  an5Logging log= new an5Logging();
  an5ModelContext current = null;
  List<an5ModelContext> searchList = null;
  Map<String, an5ModelContext> packageContexts = null;
  an5Global global;
  int diags = 5;
  
  an5SymbolTable(an5Global glob) {
    global = glob;
  }
  void reset() {
	global.initSymbolTable(this);
  }
  an5TypeValue selectFrom(an5ModelContext srch, String key) {
	an5TypeValue res = null;
    an5ModelContext nxt = srch;
//    log.DBG(diags, "<INFO> symtab.select[" + key + "]");
    
    while (nxt != null) {
/*      for (Entry<String, an5TypeValue> me: nxt.identifier.entrySet()) {
     	 boolean tst = key.equals(me.getKey());
     	 log.DBG(diags, "<INFO> symtab - Map[" + me.getKey() +
    			 "]='" + me.getValue().getClass().getName() + "' key==me.key = " +
     			 tst + ".");

      } */
      res = nxt.identifier.get(key);
      if (res != null)
    	break;
      else
    	nxt = nxt.parentCxt;
    }
    return res;
  }
  an5TypeValue select(String key) {
    return select(key, true);
  }
  an5TypeValue select(String key, boolean checkCurrent) {
	an5TypeValue res = null;
    an5ModelContext nxt;
    log.DBG(diags, "<INFO> symtab.select[" + key + "]");
    
    if (checkCurrent)
      res = selectFrom(current, key);
    
    if (res == null) {
      for (ListIterator<an5ModelContext> it = searchList.listIterator(searchList.size()); it.hasPrevious();) {
    	nxt = it.previous();
    	if (nxt == current.rootCxt) {
          continue; /* Skip current */
    	}
    	res = selectFrom(nxt, key);
    	if (res != null)
    	  break;
      }
    }
    return res;
  }
  an5TypeValue insert(String key, an5TypeValue val) {
	an5TypeValue res = null;
	
	res = current.identifier.get(key);
	if (res == null) {
	  res = select(key, false);
	  if (res == null)
        current.identifier.put(key, val);
	}
	
	return res;
  }	
}