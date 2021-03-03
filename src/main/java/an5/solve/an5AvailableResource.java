package an5.solve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import an5.model.an5Binding;
import an5.model.an5Object;
import an5.model.an5Path;
import an5.model.an5Service;

public class an5AvailableResource {
  List<an5Object> loadSet;
  Map<String, an5Object> available = new HashMap<>();
  an5AvailableInterfaces availableInterface = new an5AvailableInterfaces();
  boolean loaded = false;
  an5AvailableResource(an5AvailableResource from) {
  }
  public void availableResource(List<an5Object> a) {
   loadSet = a;
  }
  public void availableResource() {
    loaded = true;
  }
  public void available(an5Object[] a) {
    loadSet = new ArrayList<>();
	for (int i = 0; i < a.length; i++) {
	  loadSet.add(a[i]);	
	}
  }
  public Object clone() {
	an5AvailableResource cl = new an5AvailableResource(this);
    return cl;
  }
  public Map<String, an5Object> copyMap() {
	Map<String, an5Object> res = new HashMap<>();
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
	boolean res = loadSet.add(o);
	if (loaded) {
      availableInterface.available(o);
	}
    return res;
  }
  public an5Object get(int i) {
	an5Object res = null;
	if (! loaded) {
      res = loadSet.get(i);
	}
    return res;
  }
  public an5Object put(an5Object o) {
	an5Object res = null;
	if (loaded) {
	  an5Object found = available.get(o.getGUID());
	  if (found == null) {
	    res = available.put(o.getGUID(), o);
	    availableInterface.available(o);
	  }
	}
    return res;
  }
  public int size() {
	int res = 0;
	if (loaded) {
      res = available.size();
	} else if (loadSet != null) {
	  res = loadSet.size();
	}
    return res;
  }
  public void notAvailable(an5Binding[] b) {
  }
  public an5AvailableResource notAvailable(an5Object o) {
    an5AvailableResource to = new an5AvailableResource(this);
	if (o instanceof an5Path) {
	  an5Path p = (an5Path)o;
	  to.available.remove(p.path.get(p.path.size() - 1).getGUID());
	} else {
	  to.available.remove(o.getGUID());
	}
	return to;
  }
  public int canMatchInterface(an5Object o, an5Service netSrv, an5Service protoSrvs) {
    int res = 0;
    return res;
  }
  public an5Path[] probePaths(an5Object o, an5Service netSrv, an5Service protoSrvs) {
    an5Path[] res = null;
	return res;
  }
}
