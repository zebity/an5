/**
 @what Service Instance is only used with Template and Network Objects
 
 @note This needs to be revisited to manage "layers" of inherited services
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/
package an5.model;

import java.util.List;

public class an5ServiceInstance extends an5VariableInstance {
  // an5Binding binding;
  an5Service service;
  public an5ServiceInstance(String[] srvs, int[][] card) {
    super("service");
    /* change to only use an5ServiceMap */
    service = new an5ServiceMap(srvs, card);
  }
  public an5ServiceInstance(List<String> srvs, List<int[]> card) {
	super("service");
	/* Ditto */
	service = new an5ServiceMap(srvs, card);

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