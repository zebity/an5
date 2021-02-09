package an5.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class an5ServiceMap implements an5Service {
  public class mapSrv { public String service;
                        public int min,
                                   max;
                        public mapSrv(String s, int n, int x) {
                          service = new String(s);
                          min = n;
                          max = x;
                        }
                        public mapSrv(mapSrv f) {
                          service = new String(f.service);
                          min = f.min;
                          max = f.max;
                        }
                      }
  Map<String, mapSrv> map;
  boolean mappedUnique = false;
  // an5ServiceMap.mapSrv tst = new mapSrv(){service = new String("frog"), min = 1, max = 2};
  public an5ServiceMap() {
    map = new HashMap<>();
  }
  public an5ServiceMap(List<String> srvs, List<int[]> card) {
	map = new HashMap<>();
	for (int i = 0; i < srvs.size(); i++) {
      map.put(srvs.get(i), new mapSrv(srvs.get(i), card.get(i)[0], card.get(i)[1]));
    }
  }
  public an5ServiceMap(String[] srvs, int[][] card) {
	map = new HashMap<>();
	for (int i = 0; i < srvs.length; i++) {
	  map.put(srvs[i], new mapSrv(srvs[i], card[i][0], card[i][1]));
	}
  }
  public an5ServiceMap(an5ServiceList from, boolean unique) {
	map = new HashMap<>();
    Collection<String> uniqueSet = new HashSet<>();
    mappedUnique = unique;
    for (int i = 0; i < from.size(); i++) {
      if (unique && ! uniqueSet.contains(from.getService(i))) {
        uniqueSet.add(from.getService(i));
        map.put(from.getService(i), new mapSrv(from.getService(i), from.getCardinality(i)[0], from.getCardinality(i)[1]));
      }
      else if (! unique) {
        map.put(from.getService(i), new mapSrv(from.getService(i), from.getCardinality(i)[0], from.getCardinality(i)[1]));    	  
      }
    }
  }
  public an5ServiceMap(an5ServiceMap from) {
    map = new HashMap<>();
    for (mapSrv m: from.map.values()) {
      map.put(m.service, new mapSrv(m));	
    }
  }
  public an5ServiceMap(an5InterfaceInstance[] from) {
	map = new HashMap<>();
    for (an5InterfaceInstance v : from) {
	  for (int i = 0; i < v.interfaceDefinition.signatureSet.size(); i++) {
	    for (String s : v.interfaceDefinition.getServiceSignature(i)) {
		  map.put(s, new mapSrv(s, v.min, v.max));
	    }
      }
    }
  }
  public an5Service provides() {
	an5Service res;
	if (mappedUnique) {
	  res = new an5ServiceMap(this);
	}
	else {
      Collection<String> unique = new HashSet<>();
      List<String> s = new ArrayList<>();
      List<int[]> c = new ArrayList<>();
      for (mapSrv m: map.values()) {
        if (! unique.contains(m.service)) {
          unique.add(m.service);
          s.add(new String(m.service));
          c.add(new int[]{m.min, m.max});
        }
      }
      res = new an5ServiceMap(s,c);
    }
    return res;
  }
  public int size() {
    return 0;
  }
  public String getService(int i) {
    String res = null;
    return res;
  }
  public int[] getCardinality(int i) {
    return new int[0];
  }
  public boolean addService(String srv, int n, int x) {
    mapSrv res = map.put(srv, new mapSrv(new String(srv), n, x));
    return res != null;
  }
  public an5Service getWhere(int n, int x) {
    return null;
  }
  public void add(an5Service srvs) {
    if (srvs instanceof an5ServiceList) {
      for (int i = 0; i < srvs.size(); i++) {
    	if (mappedUnique && ! map.containsKey(srvs.getService(i))) {
    	  map.put(srvs.getService(i), new mapSrv(srvs.getService(i), srvs.getCardinality(i)[0], srvs.getCardinality(i)[1]));
    	}
    	else if (! mappedUnique) {
      	  map.put(srvs.getService(i), new mapSrv(srvs.getService(i), srvs.getCardinality(i)[0], srvs.getCardinality(i)[1]));    		
    	}
      }
    }
    else {
      an5ServiceMap m = (an5ServiceMap)srvs;
      for (mapSrv ms : m.map.values()) {
    	map.put(ms.service, new mapSrv(new String(ms.service), ms.min, ms.max));
      }
    }
  }
  public boolean contains(String srv) {
    return map.containsKey(srv);
  }
}
