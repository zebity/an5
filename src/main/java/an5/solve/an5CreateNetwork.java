package an5.solve;

import java.util.List;

import an5.model.an5Object;
import an5.model.an5Element;
import an5.model.an5Network;
import an5.model.an5Service;

public class an5CreateNetwork extends an5Template {
  an5Network netType;
  List<an5TemplateElement> using;
  List<an5Service> provides;
  
  public an5CreateNetwork(an5Network n) {
  }
  void seedGoal() {
    for (an5Service t: netType.providesServices) {
      	 
    }  
  }
}
