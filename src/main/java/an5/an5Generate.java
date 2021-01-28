/**
 Generate Java code from an5 definitions
 NOTE: this should be refactored to use "freemaker" templates...
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
 */
package an5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class an5Generate {
  an5Global global;
  an5SymbolTable symtab;
  String packagePath,
         dirPath;
  
  an5Generate(an5Global glob, an5SymbolTable t, String dir) {
	dirPath = new String(dir);
    symtab = t;
    global = glob;
  }
  
  String adjustClass(String in, String prefix) {
    String res;
    
    if (in.equals("link")) {
      res = new String("an5Link");
    } else if (in.equals("element")) {
      res = new String("an5Element");
    } else if (in.equals("network")) {
      res = new String("an5Network");
    } else {
      res = new String(prefix + in);
    }
    return res;
  }
  int generateInterfaceDefinitions() throws FileNotFoundException {
	int cnt = 0;
	PrintStream jvStrm;
	String ifNm;
	
    for (an5TypeValue nd : symtab.current.identifier.values()) {
      if (nd instanceof an5InterfaceValue) {
    	an5InterfaceValue ifNd = (an5InterfaceValue)nd;
    	ifNm = new String(global.interfacePrefix + ifNd.value);
        jvStrm = new PrintStream(dirPath + packagePath + global.pathSeperator + ifNm + global.fileSuffix);
        
        jvStrm.println("/* -- AN5 Generated File -- */");
        jvStrm.println("package " + symtab.current.forPackage + ";");
        jvStrm.println("import an5.model.*;");
        jvStrm.print("public interface " + ifNm);
        if (ifNd.interfacesExtended.size() > 0) {
          jvStrm.print(" extends " + global.interfacePrefix + ifNd.interfacesExtended.get(0).value);
          for (int i = 1; i < ifNd.interfacesExtended.size(); i++) {
            jvStrm.print(", " + global.interfacePrefix + ifNd.interfacesExtended.get(i).value);
          }
        }
        jvStrm.println(" {");
        jvStrm.println("  String an5name = \"" + ifNd.value + "\";");
        jvStrm.println("}");
        cnt++;
      }
    }
    return cnt;
  }
  int generateInterfaceImplementations() throws FileNotFoundException {
    int cnt = 0;
	PrintStream jvStrm;
	String ifNm;
	
    for (an5TypeValue nd : symtab.current.identifier.values()) {
      if (nd instanceof an5InterfaceValue) {
    	an5InterfaceValue ifNd = (an5InterfaceValue)nd;
    	ifNm = new String(global.classPrefix + ifNd.value);
        jvStrm = new PrintStream(dirPath + packagePath + global.pathSeperator + ifNm + global.fileSuffix);
        
        jvStrm.println("/* -- AN5 Generated File -- */");
        jvStrm.println("package " + symtab.current.forPackage + ";");
        jvStrm.println("import an5.model.*;");
        jvStrm.print("public class " + ifNm);
        if (ifNd.interfacesExtended.size() > 0) {
          jvStrm.print(" extends " + global.classPrefix + ifNd.interfacesExtended.get(0).value);
          for (int i = 1; i < ifNd.interfacesExtended.size(); i++) {
            jvStrm.print(", " + global.classPrefix + ifNd.interfacesExtended.get(i).value);
          }
        }
        else {
            jvStrm.print(" extends an5Interface");	
        }
        if (ifNd.interfacesExtended.size() > 0) {
          jvStrm.print(" implements " + global.interfacePrefix + ifNd.interfacesExtended.get(0).value);
            for (int i = 1; i < ifNd.interfacesExtended.size(); i++) {
              jvStrm.print(", " + global.interfacePrefix + ifNd.interfacesExtended.get(i).value);
            }
        }
        jvStrm.println(" {");
        jvStrm.println("  String an5name = \"" + ifNd.value + "\";");
        jvStrm.println("  " + ifNm + "() {");
        jvStrm.println("  }");
        jvStrm.println("}");
        cnt++;
      }
    }
    return cnt;
  }
  int generateClassImplementations() throws FileNotFoundException {
    int cnt = 0;
	PrintStream jvStrm;
	String clNm;
	
    for (an5TypeValue nd : symtab.current.identifier.values()) {
      if (nd instanceof an5ClassValue) {
    	an5ClassValue clNd = (an5ClassValue)nd;
    	clNm = new String(global.classPrefix + clNd.value);
        jvStrm = new PrintStream(dirPath + packagePath + global.pathSeperator + clNm + global.fileSuffix);
        
        jvStrm.println("/* -- AN5 Generated File -- */");
        jvStrm.println("package " + symtab.current.forPackage + ";");
        jvStrm.println("import an5.model.*;");
        jvStrm.print("public class " + clNm);
        if (clNd.classExtended != null) {
          jvStrm.print(" extends " + adjustClass(clNd.classExtended.value, global.classPrefix));
        }
        if (clNd.interfacesExposed.size() > 0) {
          jvStrm.print(" implements " + adjustClass(clNd.interfacesExposed.get(0).value, global.interfacePrefix));
          for (int i = 1; i < clNd.interfacesExposed.size(); i++) {
            jvStrm.print(", " + adjustClass(clNd.interfacesExposed.get(i).value, global.interfacePrefix));
          }
        }
        jvStrm.println(" {");
        jvStrm.println("  String an5name = \"" + clNd.value + "\";");
        jvStrm.println("  " + clNm + "() {");
        jvStrm.println("  }");
        jvStrm.println("}");
      }
    }
    return cnt;
  }
  int makePackage() {
	int res = 0;
    File dir;
    
    packagePath = symtab.current.forPackage.replace(global.packageSeparator, global.pathSeperator);
	dir = new File(dirPath + packagePath,"");
	 
	if (! dir.exists()) {
	  if (dir.mkdirs()) {
        res = 1;
	  }
	  else {
        res = -1;
	  }
    }
	return res;
  }
}
