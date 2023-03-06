package an5.model;

import com.github.shamil.Xid;

public class an5UIDGenerator {
  public static String getNextUID(Object forType) {
    String res = null;
    res = Xid.string();
    return(res);
  }
}