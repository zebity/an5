/**
 Generate Java code from an5 definitions
 NOTE: this should be refactored to use "freemaker" templates...
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
 */
package an5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

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
    } else if (in.equals("object")) {
        res = new String("an5Object");
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
        
        jvStrm.println("/* -- AN5 Generated Interface Definition File -- */");
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
  int generateInterfaceSignatureImplementation(PrintStream jvStrm, String sigs, List<String[]> pairs) {
	int cnt = 0;


	jvStrm.print("  String[][] " + sigs + " = new String["); 
	if (pairs != null && pairs.size() > 0) {

	  jvStrm.println("][]{");
	  jvStrm.print("       {new String(\"" + pairs.get(cnt)[0] + "\"), new String(\"" + pairs.get(cnt)[1] + "\")}");

	  for (cnt = 1; cnt < pairs.size(); cnt++) {
	    jvStrm.println(",");
        jvStrm.print("       {new String(\"" + pairs.get(cnt)[0] + "\"), new String(\"" + pairs.get(cnt)[1] + "\")}");
	  }
	  jvStrm.println("};");      
	}
	else {
	  jvStrm.println("2][];");	
	}
	return cnt;
  }
  int generateInterfaceSignatureLoadingImplementation(PrintStream jvStrm) {
	int cnt = 0;

	jvStrm.println("  public void loadSignatures() {");
	jvStrm.println("    List<String[]> newC = new ArrayList<>();");
	jvStrm.println("    List<String[]> newN = new ArrayList<>();");
	jvStrm.println("    List<String[]> newP = new ArrayList<>();");
	jvStrm.println("    for (String[] namVal : common) newC.add(new String[]{namVal[0], namVal[1]});");
    jvStrm.println("    for (String[] namVal : needs) newC.add(new String[]{namVal[0], namVal[1]});");
    jvStrm.println("    for (String[] namVal : provides) newC.add(new String[]{namVal[0], namVal[1]});");
    jvStrm.println("    signatureSet.add(new an5InterfaceSignature(newC, newN, newP));");
	jvStrm.println("  };");      

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
        
        jvStrm.println("/* -- AN5 Generated Interface Class File -- */");
        jvStrm.println("package " + symtab.current.forPackage + ";");
        jvStrm.println("import java.util.List;");
        jvStrm.println("import java.util.ArrayList;");
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
        generateInterfaceSignatureImplementation(jvStrm, "common", ifNd.commonPair);
        generateInterfaceSignatureImplementation(jvStrm, "needs", ifNd.needsPair);
        generateInterfaceSignatureImplementation(jvStrm, "provides", ifNd.providesPair);
        jvStrm.println("  /*  review and workout */");
        jvStrm.println("  String an5name = \"" + ifNd.value + "\";");
        jvStrm.println("  public " + ifNm + "() {");
        jvStrm.println("    loadSignatures();");
        jvStrm.println("    super.loadSignatures();");
        jvStrm.println("  }");
        generateInterfaceSignatureLoadingImplementation(jvStrm);
        jvStrm.println("}");
        cnt++;
      }
    }
    return cnt;
  }
  int generateClassInterfaceVariablesImplementation(PrintStream jvStrm, an5ClassValue nd) {
    int cnt = 0;
    
    jvStrm.print("  an5InterfaceInstance[] ifInst = new an5InterfaceInstance[");
    
    if (nd.interfacesReflected.size() > 0) {

      jvStrm.println("]{");
      jvStrm.print("       new an5InterfaceInstance()");

      for (cnt = 1; cnt < nd.interfacesReflected.size(); cnt++) {
        jvStrm.println(",");
        jvStrm.print("       new an5InterfaceInstance()");       
      }
      jvStrm.println("};");      
    }
    else {
      jvStrm.println("0];");	
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
        generateClassInterfaceVariablesImplementation(jvStrm, clNd);
        jvStrm.println("  public " + clNm + "() {");
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
