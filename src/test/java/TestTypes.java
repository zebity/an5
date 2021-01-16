
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

interface singer {
  public String register();
}

interface SingingJumper extends Jumper, singer {
	public String song();
}

class Wrapper implements Inside {
  public interface Inside {
	  public int hidden();
  }
}
public class TestTypes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
