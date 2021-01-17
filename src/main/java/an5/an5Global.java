/**
 Global definitiosn and utils
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
 */
package an5;

// import java.util.function.BiFunction;

public class an5Global {
  static String mainPackage = "an5",
		        basePackage = "an5.lang";
  static an5ClassValue objectRoot = new an5ClassValue("object", basePackage);
  
// private static BiFunction<String, an5ClassValue, an5TypeValue> networkFactory;
//   class an5LangTypes {
//	String an5Class;
//	/* TriFunction<String, String, an5TypeValue, an5TypeValue>; */
//	an5TypeValue baseRef;
//  }
//  private static BiFunction<String, an5ClassValue, an5TypeValue> tstFactory = an5Global.networkFactory;
  
//  an5LangTypes[] factory = {{"network", networkFactory, objectRoot},
//		                    {"element", elementFactory, objectRoot},
//		                    {"interface", interfaceFactory, null},
//		                    {"link", linkFactory, objectRoot},
//		                    {"path", pathFactory, objectRoot}};
  
  static an5TypeValue networkFactory(String val, an5ClassValue ext) {
    an5ClassValue res = new an5ClassValue(val, basePackage);
    res.classExtended = ext;
    return res;
  }
  static an5TypeValue elementFactory(String val, an5ClassValue ext) {
	an5ClassValue res = new an5ClassValue(val, basePackage);
	res.classExtended = ext;
	return res;
  }
  static an5TypeValue interfaceFactory(String val, an5ClassValue ext) {
	an5InterfaceValue res = new an5InterfaceValue(val, basePackage);
	return res;
  }
  static an5TypeValue linkFactory(String val, an5ClassValue ext) {
	an5ClassValue res = new an5ClassValue(val, basePackage);
	res.classExtended = ext;
	return res;
  }
  static an5TypeValue pathFactory(String val, an5ClassValue ext) {
	an5ClassValue res = new an5ClassValue(val, basePackage);
	res.classExtended = ext;
	return res;
  }
//  static an5TypeValue objectFactory(String val, an5ClassValue ext) {
//	an5ClassValue res = new an5ClassValue(val, basePackage);
//	res.classExtended = ext;
//	return res;
//  }
//  void addLangType(an5ModelContext baseCxt, String typ, String lang, String pkg) {
//	an5ClassValue newClass = new an5ClassValue("class", "network", basePackage);
//	baseCxt.identifier.put(newClass.value, newClass);
//  }
  void initSymbolTable(an5SymbolTable symtab) {
	an5ModelContext baseCxt = new an5ModelContext(basePackage);
	symtab.packageContexts.put(basePackage, baseCxt);
	
    baseCxt.identifier.put(objectRoot.value, objectRoot);
    baseCxt.identifier.put("network", networkFactory("network", objectRoot));
    baseCxt.identifier.put("element", elementFactory("element", objectRoot));    
    baseCxt.identifier.put("interface", interfaceFactory("interface", null));
    baseCxt.identifier.put("link", linkFactory("link", objectRoot));
    baseCxt.identifier.put("path", pathFactory("path", objectRoot)); 
  }
}
