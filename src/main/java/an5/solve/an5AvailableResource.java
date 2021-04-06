/**
 @what Holds collection of objects that are available to support network solving
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.solve;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import an5.model.an5Binding;
import an5.model.an5Object;
import an5.model.an5Path;

public class an5AvailableResource {
  Map<String, an5Object> available = new LinkedHashMap<>();
  an5AvailableInterfaces availableInterface = new an5AvailableInterfaces();
  
  an5AvailableResource(an5AvailableResource from) {
    for (an5Object o: from.available.values()) {
      put((an5Object)o.clone());
    }
  }
  public an5AvailableResource(List<an5Object> a) {
    for (an5Object no: a) {
    	put((an5Object)no.clone());
    }
  }
  public an5AvailableResource(boolean load) {		
  }
  public void available(an5Object[] a) {
	for (an5Object no: a) {
	  put(no);	
	}
  }
  public Object clone() {
	an5AvailableResource cl = new an5AvailableResource(this);
    return cl;
  }
  public Map<String, an5Object> copyMap() {
	Map<String, an5Object> res = new LinkedHashMap<>();
	for (an5Object o: this.available.values()) {
	  res.put(o.getGUID(), o);
	}
	return res;
  }
  int[] load() {
	/* load for optimised access */
    return new int[]{available.size(), availableInterface.ifCollection.size()};
  }
  public boolean add(an5Object o) {
	an5Object res = available.put(o.getGUID(), o);
    availableInterface.available(o);
    return res != null;
  }
  public an5Object get(int i) {
	an5Object res = null;
	int j = 0;
	
	if (i >= 0 && i < available.size()) {
	  for (an5Object no: available.values()) {
	    if (j == i) {
	      res = no;
	      break;
	    }
	    j++;
	  }
	}
    return res;
  }
  public an5Object put(an5Object o) {
	an5Object res = null;
	
	an5Object found = available.get(o.getGUID());
	if (found == null) {
	  res = available.put(o.getGUID(), o);
	  availableInterface.available(o);
	}
    return res;
  }
  public int size() {
	int res = available.size();
    return res;
  }
  public void notAvailable(an5Binding[] b) {
  }
  public void reload() {
	availableInterface = new an5AvailableInterfaces();
	for (an5Object o: available.values())
	  availableInterface.available(o);
  }
  public an5AvailableResource notAvailable(an5Object o, boolean reload) {
    an5AvailableResource to = new an5AvailableResource(this);
	if (o instanceof an5Path) {
	  an5Path p = (an5Path)o;
	  to.available.remove(p.getLast().getGUID());
	} else {
	  to.available.remove(o.getGUID());
	  if (reload) {
	    to.reload();
	  }
	}
	return to;
  }
}
