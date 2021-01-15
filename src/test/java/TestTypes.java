
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

/* interface Slooper extends Sleeper {	
} */

public class TestTypes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
