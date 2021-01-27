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
  an5SymbolTable symtab;
  String packagePath,
         dirPath,
         ifPrefix,
         clPrefix;
  
  an5Generate(an5SymbolTable t, String dir, String iPr, String cPr) {
	dirPath = new String(dir);
    symtab = t;
    ifPrefix = new String(iPr);
    clPrefix = new String(iPr);
  }
  int generateInterfaceDefinitions() throws FileNotFoundException {
	int cnt = 0;
	PrintStream ifStrm;
	String ifNm;
	
    for (an5TypeValue nd : symtab.current.identifier.values()) {
      if (nd instanceof an5InterfaceValue) {
    	an5InterfaceValue ifNd = (an5InterfaceValue)nd;
    	ifNm = new String(ifPrefix + ifNd.value);
        ifStrm = new PrintStream(dirPath + packagePath + an5Global.pathSeperator + ifNm + an5Global.fileSuffix);
        
        ifStrm.println("/* -- AN5 Generated File -- /");
        ifStrm.println("package " + symtab.current.forPackage + ";");
        ifStrm.print("interface " + ifNm);
        if (ifNd.interfacesExtended.size() > 0) {
          ifStrm.print(" extends " + ifPrefix + ifNd.interfacesExtended.get(0).value);
          for (int i = 1; i < ifNd.interfacesExtended.size(); i++) {
            ifStrm.print(", " + ifPrefix + ifNd.interfacesExtended.get(i).value);
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
  int generateInterfaceImplementations() {
    int cnt = 0;
    return cnt;
  }
  int generateClassImplementations() {
    int cnt = 0;
    return cnt;
  }
  int makePackage() {
	int res = 0;
    File dir;
    
    packagePath = symtab.current.forPackage.replace(an5Global.packageSeparator, an5Global.pathSeperator);
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
