
package an5.model;

import java.util.ArrayList;
import java.util.List;

public class an5Service extends an5VariableInstance {
  // an5Binding binding;
  List<String> service = new ArrayList<>();
  List<int[]> cardinality = new ArrayList<>();
  public an5Service(String[] srvs, int[][] card) {
    super("service");
    for (String s: srvs) service.add(s);
    for (int i = 0; i < card.length; i++)
      cardinality.add(new int[]{card[i][0], card[i][1]});
  }
}
