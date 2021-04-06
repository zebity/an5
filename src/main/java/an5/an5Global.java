/**
 @what Global definitiosn and utils
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
 */
package an5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// import java.util.function.BiFunction;

public class an5Global {
  String mainPackage = "an5",
		 basePackage = "an5.lang",
		 interfacePrefix = "AN5IF_",
		 classPrefix = "AN5CL_",
		 templatePrefix = "AN5TP_",
		 attrPrefix = "AN5AT_",
		 sigPrefix = "AN5SG_",
         fileSuffix = ".java",
         arrayFlag = "[",
         arrayErr = "!",
         mandatoryFlag = "mandatory";
  char pathSeperator = '/',
       packageSeparator = '.';
  an5ClassValue objectRoot = new an5ClassValue("object", basePackage);
  
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
  
  an5TypeValue networkFactory(String val, an5ClassValue ext) {
    an5ClassValue res = new an5ClassValue(val, basePackage);
    res.classExtended = ext;
    return res;
  }
  an5TypeValue elementFactory(String val, an5ClassValue ext) {
	an5ClassValue res = new an5ClassValue(val, basePackage);
	res.classExtended = ext;
	return res;
  }
  an5TypeValue interfaceFactory(String val, an5ClassValue ext) {
	an5InterfaceValue res = new an5InterfaceValue(val, basePackage);
	return res;
  }
  an5TypeValue linkFactory(String val, an5ClassValue ext) {
	an5ClassValue res = new an5ClassValue(val, basePackage);
	res.classExtended = ext;
	return res;
  }
  an5TypeValue pathFactory(String val, an5ClassValue ext) {
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
	symtab.searchList = new ArrayList<>();
	symtab.packageContexts = new HashMap<>();
	
	an5ModelContext baseCxt = new an5ModelContext(basePackage);
	symtab.packageContexts.put(basePackage, baseCxt);
	
    baseCxt.identifier.put(objectRoot.value, objectRoot);
    baseCxt.identifier.put("network", networkFactory("network", objectRoot));
    baseCxt.identifier.put("element", elementFactory("element", objectRoot));    
    baseCxt.identifier.put("interface", interfaceFactory("interface", null));
    baseCxt.identifier.put("link", linkFactory("link", objectRoot));
    baseCxt.identifier.put("path", pathFactory("path", objectRoot));
    symtab.searchList.add(baseCxt);
    symtab.current = new an5ModelContext(".");
    symtab.packageContexts.put(".", symtab.current);
    symtab.searchList.add(symtab.current);
  }
  int parseArgs(String[] args, Map<String, String> flags, List<String> sf) {
	an5Logging log = new an5Logging();
	int res = 0;

	for (int i = 0; i < args.length; i++) {
      String a = args[i];
	  if (a.charAt(0) == '-' && a.length() > 1) {
	    switch(a.charAt(1)) {
	      case 'd': if (i+1 > args.length) {
	  		          log.ERR(3, "<log.ERR>:AN5:Invalid argument: [" + a + "].");
	  		          res--;
	                }
	                else {
	    	          flags.put("gendir", args[i+1]);
	    	          i++; /* skip past dirpath */
	                }
	                break;
	      case 'n': flags.put("nogen", "");
	                break;
	      default: log.ERR(3, "<log.ERR>:AN5:Invalid argument: [" + a + "].");
	               res--;
	               break;
	    }
	  }
	  else if (a.length() > 1) {
        sf.add(a);
	  }
	  else {
		log.ERR(3, "<log.ERR>:AN5:Invalid argument: [" + a + "].");
		res--;
	  }
	}
	return res;
  }
}
