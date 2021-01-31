/**
 Reflected interface variable
*/

package an5;

class an5InterfaceVariableValue extends an5VariableValue {
  an5InterfaceValue interfaceReflected;
  boolean isArray = false;
  int size = 1;
  an5InterfaceVariableValue(String val, String pack, an5InterfaceValue ifVal, String arFlag) {
    super(val, pack);
    interfaceReflected = ifVal;
    if (arFlag.equals("[")) {
      isArray = true;
      size = -1;
    }
  }
}