
package an5.model;

import java.util.ArrayList;
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
  public an5Service(List<String> srvs, List<int[]> card) {
    super("service");
    service = srvs;
    cardinality = card;
  }
  public an5Service getWhere(int n, int x) {
    List<String> srv = new ArrayList<>();
    List<int[]> card = new ArrayList<>();
    for (int i = 0; i < cardinality.size(); i++) {
      if (cardinality.get(i)[0] >= n && cardinality.get(i)[0] <= x) {
    	srv.add(service.get(i));
    	card.add(new int[]{cardinality.get(i)[0], cardinality.get(i)[1]});
      }
    }
    return new an5Service(srv, card);
  }
}