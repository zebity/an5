/**
 @what This is hack to allow passing constructor arguments
 
 @note Should be replaced by json based load/dump methods
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
 */
package an5.model;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class an5ConstructArguments {
  public List<String[]> args = new ArrayList<>();

  public an5ConstructArguments(String[][] set) {
    if (set != null) {
      for (int i = 0; i < set.length; i++) {
        args.add(set[i]);
      }
    }
  }
  public an5ConstructArguments() {
  }
  
  public an5ConstructArguments getSuperArgs() {
    an5ConstructArguments res = new an5ConstructArguments();
    return res;
  }
}
