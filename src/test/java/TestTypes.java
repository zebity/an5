import java.util.HashMap;
import java.util.Map;

interface Jumper {
  public int jumpHeight();
  public int jumpDistance();
}

class Frog implements Jumper {
  public int jumpHeight() {
	return 1;
  }
  public int jumpDistance() {
    return 2;
  }
}

class Sleeper {
  public boolean snoors() {
	  return true;
  }
}

class /* interface */ Slooper extends Sleeper {	
}

interface Singer {
  public String register();
  public class Notes {
    String[] sings = { "a", "b", "c" };
  }
}

interface SingingJumper extends Jumper, Singer {
	public String song();
}

class KangarooSoprano implements SingingJumper {
  public String register() {
    return "high";
  }
  public String song() {
    return "happy kangaroo";
  }
  public int jumpHeight() {
    return 1;
  }
  public int jumpDistance() {
	return 3;
  }
}
/* Scope test - Inside not visible
class Wrapper implements Inside {
  public interface Inside {
	  public int hidden();
  }
} */

public class TestTypes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

class CollectionOfJumpers {
  SingingJumper ref;
  void putIfSinger(Jumper item) {
    if (item instanceof SingingJumper) {
      ref = (SingingJumper)item;
    }
  }
}

abstract class NotConcrete {

}

class Concrete {
  static int SharedInt;
  static int[] SharedArray = {1,2};
}

class ArrayOfArray {
  String[][] array = new String[2][];
}

class MapofMap {
  Map<String, Map<Object, Object>> mapOjects = new HashMap<>();
}
