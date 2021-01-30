package an5.model;

public class an5InterfaceInstance {
  static class allocationPolicy { static int STATIC = 0, DYNAMIC = 1; };
  an5Interface prototype;
  int minInstance,
      maxInstanc;
  int alloc = allocationPolicy.STATIC;
  public an5InterfaceInstance() {
  }
}
