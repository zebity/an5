package an5.solve;

import java.util.List;

import an5.model.an5Object;
import an5.model.an5Element;
import an5.model.an5Network;
import an5.model.an5Service;

public class createNetwork extends an5Template {
  an5Network netType;
  List<an5TemplateElement> using;
  List<an5Service> provides;
  
  createNetwork(an5Network n, an5Object[] from, an5Service [] providing) {
  }
  void seedGoal() {
    for (an5Service t: netType.providesServices) {
      	 
    }  
  }
}
