/**
 Reflected interface variable
*/

package an5;

import java.util.ArrayList;
import java.util.List;

class an5ServiceSetValue extends an5VariableValue {
  public List<String> service;;
  public List<int[]> cardinality;
  an5ServiceSetValue(String val, String pack, List<String> srvs, List<int[]> cards) {
    super(val, pack);
    service = srvs;
    cardinality = cards;
  }
}