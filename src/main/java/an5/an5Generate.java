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
  boolean checkMandatoryFlag(String anno) {
    boolean res = anno.equalsIgnoreCase(global.mandatoryFlag);
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

	if (pairs != null && pairs.size() > 0) {
		
	  jvStrm.println("      new String[][]{"); 
	  jvStrm.print("          {new String(\"" + pairs.get(cnt)[0] + "\"), new String(\"" + pairs.get(cnt)[1] + "\")}");

	  for (cnt = 1; cnt < pairs.size(); cnt++) {
	    jvStrm.println(",");
        jvStrm.print("          {new String(\"" + pairs.get(cnt)[0] + "\"), new String(\"" + pairs.get(cnt)[1] + "\")}");
	  }
	  jvStrm.println("},");      
	}
	else {
	  jvStrm.println("      new String[0][],");	
	}
	return cnt;
  }
  int generateInterfaceServiceImplementation(PrintStream jvStrm, String sigs, List<String> srv) {
	int cnt = 0;

	if (srv != null && srv.size() > 0) {
	  jvStrm.println("    new String[]{");
	  jvStrm.print("      new String(\"" + srv.get(cnt) + "\")");

	  for (cnt = 1; cnt < srv.size(); cnt++) {
	    jvStrm.println(",");
        jvStrm.print("    new String(\"" + srv.get(cnt) + "\")");
	  }
	  jvStrm.println("},");      
	}
	else {
	  jvStrm.println("      new String[0],");	
	}
	return cnt;
  }
  int generateInterfaceSignatureConstructor(PrintStream jvStrm, an5InterfaceValue nd, String nm, int min, int max) {
	int cnt = 0;

	jvStrm.println("  public " + nm + "() {");
	jvStrm.println("    super();");
	jvStrm.println("    an5InterfaceSignature AN5SG_signature = new an5InterfaceSignature(an5name,");
    generateInterfaceSignatureImplementation(jvStrm, "common", nd.commonPair);
    generateInterfaceSignatureImplementation(jvStrm, "needs", nd.needsPair);
    generateInterfaceSignatureImplementation(jvStrm, "provides", nd.providesPair);
    generateInterfaceServiceImplementation(jvStrm, "services", nd.services);
	jvStrm.println("      " + min +"," + max + ");");
    jvStrm.println("    addSignatureSet(AN5SG_signature);");
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
        jvStrm.println("  /*  review and workout */");
        jvStrm.println("  String an5name = \"" + ifNd.value + "\";");
        generateInterfaceSignatureConstructor(jvStrm, ifNd, ifNm, ifNd.cardinalityMin, ifNd.cardinalityMax);
        jvStrm.println("}");
        cnt++;
      }
    }
    return cnt;
  }
  int generateClassInterfaceVariablesImplementation(PrintStream jvStrm, an5ClassValue nd) {
    int cnt = 0;
    an5InterfaceVariableValue ifVar = null;
    
    if (nd.interfacesReflected.size() > 0) {
      for (cnt = 0; cnt < nd.interfacesReflected.size(); cnt++) {
    	ifVar = nd.interfacesReflected.get(cnt);
        jvStrm.print("  an5InterfaceInstance " + global.attrPrefix + ifVar.value);
        jvStrm.print(" = new an5InterfaceInstance(\"" + ifVar.value + "\", new ");
        jvStrm.println(global.classPrefix + ifVar.interfaceReflected.value + "(), 0, " + ifVar.size + ");");
      }
      
      cnt = 0;
      jvStrm.println("  an5InterfaceInstance[] AN5AT_interface = new an5InterfaceInstance[]{");
      ifVar = nd.interfacesReflected.get(cnt);
      jvStrm.print("    " + global.attrPrefix + ifVar.value);
      for (cnt = 1; cnt < nd.interfacesReflected.size(); cnt++) {
        ifVar = nd.interfacesReflected.get(cnt);
    	jvStrm.println(",");
        jvStrm.print("    " + global.attrPrefix + ifVar.value);
      }
      jvStrm.println("};");
      jvStrm.println("  an5Service AN5AT_service = new an5ServiceMap(AN5AT_interface);");
    }
    else {
      jvStrm.println("  an5InterfaceInstance[] AN5AT_interface = new an5InterfaceInstance[0];");
    }
    return cnt;
  }
  int generateClassObjectVariablesImplementation(PrintStream jvStrm, an5ClassValue nd, boolean abstractSuppress) {
	int cnt = 0;
	an5ClassVariableValue clVar = null;
	boolean mandatoryFlag;
	    
	if (nd.contained.size() > 0) {

	  for (cnt = 0; cnt < nd.contained.size(); cnt++) {
	    clVar = nd.contained.get(cnt);
	    mandatoryFlag = checkMandatoryFlag(clVar.annotation);
	    if (abstractSuppress && mandatoryFlag) {
	      mandatoryFlag = false;
	    }
	    jvStrm.print("  an5ClassInstance " + global.attrPrefix + clVar.value);
	    jvStrm.print(" = new an5ClassInstance(\"" + clVar.value + "\", new ");
	    jvStrm.println(adjustClass(clVar.contained.value, global.classPrefix) + "(), 0, " + clVar.size + ", " + mandatoryFlag + ");");
	  }
	      
	  cnt = 0;
	  jvStrm.println("  an5ClassInstance[] AN5AT_class = new an5ClassInstance[]{");
	  clVar = nd.contained.get(cnt);
	  jvStrm.print("    " + global.attrPrefix + clVar.value);
	  for (cnt = 1; cnt < nd.contained.size(); cnt++) {
	    clVar = nd.contained.get(cnt);
	    jvStrm.println(",");
	    jvStrm.print("    " + global.attrPrefix + clVar.value);
	  }
	  jvStrm.println("};");
	} else {
	  jvStrm.println("  an5ClassInstance[] AN5AT_class = new an5ClassInstance[0];");
	}
	return cnt;
  }
  int generateClassServiceSetVariablesImplementation(PrintStream jvStrm, an5ClassValue nd, boolean abstractSuppress) {
	int i = 0;
	an5InterfaceVariableValue ifVar = null;

	if (abstractSuppress) {
	  if (nd.networkServices.size() > 0) {
	    jvStrm.println("  an5Service AN5AT_service = new an5ServiceList();");  
	  }
	} else {
	  if (nd.networkServices.size() > 0) {
		
        jvStrm.print("  an5Service AN5AT_service = new an5ServiceList(new String[]{");  
	    for (an5ServiceSetValue set: nd.networkServices) {
		  jvStrm.print("\"" + set.service.get(i) + "\"");
		  for (i = 1; i < set.service.size(); i++)
		    jvStrm.print(", \"" + set.service.get(i) + "\"");
	    }
	    jvStrm.println("},");
	  
        jvStrm.print("                   new int[][]{");  
	    for (an5ServiceSetValue set: nd.networkServices) {
		  i = 0;
		  jvStrm.print("{" + set.cardinality.get(i)[0] + ", " + set.cardinality.get(i)[1] + "}");
		  for (i = 1; i < set.cardinality.size(); i++)
		    jvStrm.print(", {" + set.cardinality.get(i)[0] + ", " + set.cardinality.get(i)[1] + "}");
	    }
	    jvStrm.println("});");
	  }
	}
	return i;		
  }
  int generateTemplateClassImplementations(an5ClassValue nd) throws FileNotFoundException {
    int cnt = 0;
    PrintStream jvStrm;
	String clNm;
	
    clNm = new String(global.templatePrefix + nd.value);
    jvStrm = new PrintStream(dirPath + packagePath + global.pathSeperator + clNm + global.fileSuffix);
  
    jvStrm.println("/* -- AN5 Generated Class Template File -- */");
    jvStrm.println("package " + symtab.current.forPackage + ";");
    jvStrm.println("import an5.model.*;");
    jvStrm.print("public class " + clNm);
    if (nd.classExtended != null) {
      jvStrm.print(" extends " + adjustClass(nd.classExtended.value, global.templatePrefix));
    }
    jvStrm.print(" implements an5ClassTemplate ");
    if (nd.interfacesExposed.size() > 0) {
      for (int i = 0; i < nd.interfacesExposed.size(); i++) {
        jvStrm.print(", " + adjustClass(nd.interfacesExposed.get(i).value, global.interfacePrefix));
      }
    }
    jvStrm.println(" {");
    jvStrm.println("  String an5name = \"" + nd.value + "\";");
    generateClassInterfaceVariablesImplementation(jvStrm, nd);
    generateClassServiceSetVariablesImplementation(jvStrm, nd, false);
    generateClassObjectVariablesImplementation(jvStrm, nd, false);
    jvStrm.println("  public an5Object createInstance() {");
    jvStrm.println("    return new " + global.classPrefix + nd.value + "(this);");    
	jvStrm.println("  }");
    jvStrm.println("  public " + clNm + "() {");
    jvStrm.println("    super();");
	jvStrm.println("    for (an5VariableInstance v: AN5AT_interface) AN5AT_vars.put(v.var, v);");
	jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_vars.put(v.var, v);");
    if (nd.interfacesReflected.size() > 0 || nd.networkServices.size() > 0) {
      jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
      jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
    }
	jvStrm.println("    abstractSpec = true;");
	jvStrm.println("  }");
    jvStrm.println("  public " + clNm + "(an5ConstructArguments args) {");
    jvStrm.println("    super(args.getSuperArgs());");
	jvStrm.println("    for (an5VariableInstance v: AN5AT_interface) AN5AT_vars.put(v.var, v);");
	jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_vars.put(v.var, v);");
    if (nd.interfacesReflected.size() > 0 || nd.networkServices.size() > 0) {
     jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
     jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
    }
	jvStrm.println("    varUtil.setConstrutArgs(args, this);");
    jvStrm.println("    abstractSpec = true;");
	jvStrm.println("  }");
    jvStrm.println("}");
    
    return cnt;
  }
  int generateClassImplementations() throws FileNotFoundException {
    int cnt = 0;
	PrintStream jvStrm;
	String clNm;
	
    for (an5TypeValue nd : symtab.current.identifier.values()) {
      if (nd instanceof an5ClassValue) {
    	an5ClassValue clNd = (an5ClassValue)nd;
    	if (clNd.abstractSpec) {
    	  generateTemplateClassImplementations(clNd);
    	}
    	clNm = new String(global.classPrefix + clNd.value);
        jvStrm = new PrintStream(dirPath + packagePath + global.pathSeperator + clNm + global.fileSuffix);
        
        jvStrm.println("/* -- AN5 Generated Class File -- */");
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
        generateClassServiceSetVariablesImplementation(jvStrm, clNd, clNd.abstractSpec);
        generateClassObjectVariablesImplementation(jvStrm, clNd, clNd.abstractSpec);
    	if (clNd.abstractSpec) {
          jvStrm.println("  public " + clNm + "(" + global.templatePrefix + clNd.value + " from) {");
          jvStrm.println("    super();");
          jvStrm.println("    for (an5VariableInstance v: AN5AT_interface) AN5AT_vars.put(v.var, v);");
          jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_vars.put(v.var, v);");
          if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
            jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
            jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
          }
          jvStrm.println("  }");    		
    	}
        jvStrm.println("  public " + clNm + "() {");
        jvStrm.println("    super();");
    	jvStrm.println("    for (an5VariableInstance v: AN5AT_interface) AN5AT_vars.put(v.var, v);");
    	jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_vars.put(v.var, v);");
        if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
          jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
          jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
        }
    	jvStrm.println("  }");
        jvStrm.println("  public " + clNm + "(an5ConstructArguments args) {");
        jvStrm.println("    super(args.getSuperArgs());");
    	jvStrm.println("    for (an5VariableInstance v: AN5AT_interface) AN5AT_vars.put(v.var, v);");
    	jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_vars.put(v.var, v);");
        if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
          jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
          jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
        }
    	jvStrm.println("    varUtil.setConstrutArgs(args, this);");
    	jvStrm.println("  }");
        jvStrm.println("  public " + clNm + "(" + clNm + " o) {");
        jvStrm.println("    super(o);");
    	jvStrm.println("    for (an5VariableInstance v: o.AN5AT_interface) AN5AT_vars.put(v.var, v);");
    	jvStrm.println("    for (an5ClassInstance v: o.AN5AT_class) AN5AT_vars.put(v.var, v);");
        if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
          jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
          jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
        }
    	jvStrm.println("    varUtil.copyVars(o, this);");
    	jvStrm.println("  }");
        jvStrm.println("  public Object clone() {");
        jvStrm.println("    return new " + clNm + "(this);");
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
