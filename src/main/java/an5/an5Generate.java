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
  int generateInterfaceDefinitions() throws FileNotFoundException {
	int cnt = 0;
	PrintStream ifStrm;
	String ifNm;
	
    for (an5TypeValue nd : symtab.current.identifier.values()) {
      if (nd instanceof an5InterfaceValue) {
    	an5InterfaceValue ifNd = (an5InterfaceValue)nd;
    	ifNm = new String(global.interfacePrefix + ifNd.value);
        ifStrm = new PrintStream(dirPath + packagePath + global.pathSeperator + ifNm + global.fileSuffix);
        
        ifStrm.println("/* -- AN5 Generated File -- */");
        ifStrm.println("package " + symtab.current.forPackage + ";");
        ifStrm.println("import an5.model.*;");
        ifStrm.print("interface " + ifNm);
        if (ifNd.interfacesExtended.size() > 0) {
          ifStrm.print(" extends " + global.interfacePrefix + ifNd.interfacesExtended.get(0).value);
          for (int i = 1; i < ifNd.interfacesExtended.size(); i++) {
            ifStrm.print(", " + global.interfacePrefix + ifNd.interfacesExtended.get(i).value);
          }
        }
        ifStrm.println(" {");
        ifStrm.println("  String an5name = \"" + ifNd.value + "\";");
        ifStrm.println("}");
        cnt++;
      }
    }
    return cnt;
  }
  int generateInterfaceImplementations() throws FileNotFoundException {
    int cnt = 0;
	PrintStream ifStrm;
	String ifNm;
	
    for (an5TypeValue nd : symtab.current.identifier.values()) {
      if (nd instanceof an5InterfaceValue) {
    	an5InterfaceValue ifNd = (an5InterfaceValue)nd;
    	ifNm = new String(global.classPrefix + ifNd.value);
        ifStrm = new PrintStream(dirPath + packagePath + global.pathSeperator + ifNm + global.fileSuffix);
        
        ifStrm.println("/* -- AN5 Generated File -- */");
        ifStrm.println("package " + symtab.current.forPackage + ";");
        ifStrm.println("import an5.model.*;");
        ifStrm.print("class " + ifNm);
        if (ifNd.interfacesExtended.size() > 0) {
          ifStrm.print(" extends " + global.classPrefix + ifNd.interfacesExtended.get(0).value);
          for (int i = 1; i < ifNd.interfacesExtended.size(); i++) {
            ifStrm.print(", " + global.classPrefix + ifNd.interfacesExtended.get(i).value);
          }
        }
        else {
            ifStrm.print(" extends an5interface");	
        }
        if (ifNd.interfacesExtended.size() > 0) {
          ifStrm.print(" implements " + global.interfacePrefix + ifNd.interfacesExtended.get(0).value);
            for (int i = 1; i < ifNd.interfacesExtended.size(); i++) {
              ifStrm.print(", " + global.interfacePrefix + ifNd.interfacesExtended.get(i).value);
            }
        }
        ifStrm.println(" {");
        ifStrm.println("  " + ifNm + "();");
        ifStrm.println("}");
        cnt++;
      }
    }
    return cnt;
  }
  int generateClassImplementations() {
    int cnt = 0;
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
