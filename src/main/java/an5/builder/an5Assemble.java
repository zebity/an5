package an5.builder;

import an5.model.an5Binding;
import an5.model.an5InterfaceInstance;
import an5.model.an5Object;
import an5.model.an5Path;
import an5.model.an5Service;

public class an5Assemble {
  public static class buildFlags { final static int ANY = 0x01, ALL = 0x02, ONLY = 0x04; } 
  public int canLink(an5Object a, an5Object b) {
    int res = 0;
    return res;
  }
  public an5InterfaceInstance[] compatableInterfaces(an5Object from, an5Object to, an5Service where) {
    an5InterfaceInstance[] res = null;
    return res;
  }
  public an5InterfaceInstance[] compatableInterfaces(an5Object from, an5InterfaceInstance via, an5Object b, an5Service where) {
	an5InterfaceInstance[] res = null;
	return res;	  
  }
  public an5Path[] link(an5Object from, an5Object to, an5Service where, int flags) {
    an5Path[] res = null;
    boolean done = false;
    an5InterfaceInstance[] fromOptions, toOptions;
    
    fromOptions = compatableInterfaces(from, to, where);
    
    for (int i = 0; (! done) && i < fromOptions.length; i++) {
      toOptions = compatableInterfaces(from, fromOptions[i], to, where);
      if (toOptions.length > 0) {
        if ((flags & buildFlags.ANY) != 0) {
        	
        }
      }
    }
    return res;
  }
}
