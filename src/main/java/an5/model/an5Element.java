package an5.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class an5Element extends an5Object {  
  public an5Element() {
  }
  public an5Element(an5ConstructArguments args) {  
  }
  public boolean[] providesService(an5Service mustProvide, an5Service canProvide) {
	boolean must = true,
			can = true;
	for (int i = 0; i < mustProvide.size(); i++) {
	  must = must && AN5AT_serviceUnion.contains(mustProvide.getService(i));
	}
	for (int i = 0; i < canProvide.size(); i++) {
	  can = can && AN5AT_serviceUnion.contains(canProvide.getService(i));
    }
	return new boolean[]{must, can};
  }
}
