/**
 Reflected interface variable
*/

package an5;

class an5ClassVariableValue extends an5VariableValue {
  an5ClassValue contained;
  boolean isArray = false;
  String annotation;
  int size = 1;
  an5ClassVariableValue(String val, String pack, an5ClassValue clVal,
                        String arFlag, String an) {
    super(val, pack);
    contained = clVal;
    if (arFlag.equals("[")) {
      isArray = true;
      size = -1;
    }
    annotation = an;
  }
}