/**
 This needs to be revisited to manage "layers" of inherited services
 */
package an5.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class an5ServiceInstance extends an5VariableInstance {
  // an5Binding binding;
  an5Service service;
  /* List<String> service = new ArrayList<>;
  List<int[]> cardinality = new ArrayList<>(); */
  public an5ServiceInstance(String[] srvs, int[][] card) {
    super("service");
    service = new an5ServiceList();
    for (int i = 0; i < card.length; i++) {
      service.addService(srvs[i], card[i][0], card[i][1]);
    }
  }
  public an5ServiceInstance(List<String> srvs, List<int[]> card) {
	super("service");
	service = new an5ServiceList(srvs, card);

  }
  public an5Service provides() {
	return service.provides();
  }
  public int size() {
    return service.size();
  }
  public String getService(int i) {
    return service.getService(i);
  }
  public int[] getCardinality(int i) {
	return service.getCardinality(i);
  }
  public boolean addService(String srv, int n, int x) {
    return service.addService(srv, n, x);
  }
  public an5Service getWhere(int n, int x) {
    return service.getWhere(n, x);
  }
}