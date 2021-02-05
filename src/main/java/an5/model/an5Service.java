/**
 This needs to be revisited to manage "layers" of inherited services
 */
package an5.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class an5Service extends an5VariableInstance {
  // an5Binding binding;
  List<String> service = new ArrayList<>();
  List<int[]> cardinality = new ArrayList<>();
  public an5Service(String[] srvs, int[][] card) {
    super("service");
    for (int i = 0; i < card.length; i++) {
      service.add(srvs[i]);	
      cardinality.add(new int[]{card[i][0], card[i][1]});
    }
  }
  public an5Service provides() {
    Collection<String> unique = new HashSet<>();
    List<String> s = new ArrayList<>();
    List<int[]> c = new ArrayList<>();
    for (int i = 0; i < service.size(); i++) {
      if (! unique.contains(service.get(i))) {
    	unique.add(service.get(i));
    	s.add(new String(service.get(i)));
    	c.add(new int[]{cardinality.get(i)[0], cardinality.get(i)[1]});
      }
    }
    return new an5Service(s,c);
  }
  public int size() {
    return service.size();
  }
  public String getService(int i) {
    String res = null;
    if (i >= 0 && i < service.size()) {
      res = service.get(i);
    }
    return res;
  }
  public int[] getCardinality(int i) {
	    int[] res = new int[0];
	    if (i >= 0 && i < cardinality.size()) {
	      res = cardinality.get(i);
	    }
	    return new int[]{res[0], res[1]};
	  }
  public boolean addService(String srv, int n, int x) {
    boolean res = service.add(srv);
    res = res && cardinality.add(new int[]{n,x});
    return res;
  }
  public an5Service(List<String> srvs, List<int[]> card) {
    super("service");
    service = srvs;
    cardinality = card;
  }
  public an5Service getWhere(int n, int x) {
    List<String> srv = new ArrayList<>();
    List<int[]> card = new ArrayList<>();
    for (int i = 0; i < cardinality.size(); i++) {
      if (n > 0 && cardinality.get(i)[0] >= n) {
      	srv.add(service.get(i));
      	card.add(new int[]{cardinality.get(i)[0], cardinality.get(i)[1]});
      }
      else if (n == 0 && (cardinality.get(i)[0] >= n && cardinality.get(i)[1] <= x)) {
    	srv.add(service.get(i));
    	card.add(new int[]{cardinality.get(i)[0], cardinality.get(i)[1]});
      }
    }
    return new an5Service(srv, card);
  }
}