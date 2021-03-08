/**
 This needs to be revisited to manage "layers" of inherited services
 */
package an5.model;

public interface an5Service {
  public an5Service provides();
  public int size();
  public String getService(int i);
  public int[] getCardinality(int i);
  public boolean addService(String srv, int n, int x);
  public an5Service getWhere(int n, int x);
  public void add(an5Service srvs);
  public boolean contains(String srv);
  // public void check();
}