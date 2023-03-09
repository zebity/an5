/**
 @what Generate Java code from an5 definitions
 
 @note this should be refactored to use "freemaker" templates...
 
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
  
  String adjustDeserializerClass(String in, String prefix) {
    String res;

	if (in.equals("link")) {
	  res = new String("an5LinkJsonDes");
	} else if (in.equals("element")) {
	  res = new String("an5ElementJsonDes");
	} else if (in.equals("network")) {
	  res = new String("an5NetworkJsonDes");
	} else if (in.equals("object")) {
	  res = new String("an5ObjectJsonDes");
	} else {
	  res = new String(prefix + in);
	}
	return res;
  }
  String adjustSerializerClass(String in, String prefix) {
	String res;

	if (in.equals("link")) {
	  res = new String("an5LinkJsonSer");
	} else if (in.equals("element")) {
	  res = new String("an5ElementJsonSer");
	} else if (in.equals("network")) {
	  res = new String("an5NetworkJsonSer");
	} else if (in.equals("object")) {
	  res = new String("an5ObjectJsonSer");
	} else {
	  res = new String(prefix + in);
	}
	return res;
  }
  String adjustType(String in, String prefix) {
	String res;
	
	if (in.equals("string")) {
	  res = new String("String");
	} else if (in.equals("boolean")) {
	  res = new String("boolean");
	} else if (in.equals("int")) {
	  res = new String("int");
	} else if (in.equals("enum")) {
	    res = new String("enum");
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
        jvStrm.println("/* -- AN5 Concept by John Hartley - Graphica Software/Dokmai Pty Ltd -- */");
        jvStrm.println("package " + symtab.current.forPackage + ";");
        /* jvStrm.println("import an5.model.*;"); */
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

	jvStrm.println("  public " + nm + "(String nmTemplate) {");
	jvStrm.println("    super(nmTemplate);");
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
        jvStrm.println("/* -- AN5 Concept by John Hartley - Graphica Software/Dokmai Pty Ltd -- */");
        jvStrm.println("package " + symtab.current.forPackage + ";");
        /* jvStrm.println("import java.util.List;");
        jvStrm.println("import java.util.ArrayList;"); */
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
        // jvStrm.println("  /*  review and work out */");
        jvStrm.println("  String an5name = \"" + ifNd.value + "\";");
        // jvStrm.println("  String an5bindingName = \"" + ifNd.nameTemplate + "\";");
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
        jvStrm.println(global.classPrefix + ifVar.interfaceReflected.value + "(\"" + ifVar.interfaceReflected.nameTemplate + "\"), 0, " + ifVar.size + ");");
      }
      
      cnt = 0;
      jvStrm.println("  an5InterfaceTable[] AN5AT_interface = new an5InterfaceTable[]{");
      ifVar = nd.interfacesReflected.get(cnt);
      jvStrm.print("   new an5InterfaceTable(" + cnt + ", new String(\"" + ifVar.value + "\"), " + global.attrPrefix + ifVar.value + ")");
      for (cnt = 1; cnt < nd.interfacesReflected.size(); cnt++) {
        ifVar = nd.interfacesReflected.get(cnt);
    	jvStrm.println(",");
        jvStrm.print("   new an5InterfaceTable(" + cnt + ", new String(\"" + ifVar.value + "\"), " + global.attrPrefix + ifVar.value + ")");
        /* jvStrm.print("    " + global.attrPrefix + ifVar.value); */
      }
      jvStrm.println("};");
      jvStrm.println("  an5Service AN5AT_service = new an5ServiceMap(AN5AT_interface);");
    }
    else {
      jvStrm.println("  an5InterfaceTable[] AN5AT_interface = new an5InterfaceTable[0];");
    }
    return cnt;
  }
  int generateClassObjectVariablesImplementation(PrintStream jvStrm, an5ClassValue nd, boolean goalSuppress) {
	int cnt = 0;
	an5ClassVariableValue clVar = null;
	boolean mandatoryFlag;
	    
	if (nd.contained.size() > 0) {

	  for (cnt = 0; cnt < nd.contained.size(); cnt++) {
	    clVar = nd.contained.get(cnt);
	    mandatoryFlag = checkMandatoryFlag(clVar.annotation);
	    if (goalSuppress && mandatoryFlag) {
	      mandatoryFlag = false;
	    }
	    jvStrm.print("  an5ClassInstance " + global.attrPrefix + clVar.value);
	    jvStrm.print(" = new an5ClassInstance(\"" + clVar.value + "\", new ");
	    jvStrm.println(adjustClass(clVar.contained.value, global.classPrefix) + "(), 0, " + clVar.size + ", " + cnt + ", " + mandatoryFlag + ");");
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
  int generateClassFieldVariablesImplementation(PrintStream jvStrm, an5ClassValue nd, boolean goalSuppress) {
	int cnt = 0;
	String[] attVar = null;
	    
	if (nd.attributes.size() > 0) {

	  for (cnt = 0; cnt < nd.attributes.size(); cnt++) {
	    attVar = nd.attributes.get(cnt);
	    jvStrm.print("  " + adjustType(attVar[0], "an5") + " " + global.attrPrefix + attVar[1]);
	    if (attVar.length ==  3) {
	      jvStrm.println(" = " + attVar[2] + ";");
	    } else {
	      jvStrm.println(";");
	    } 
	  }
	}
	return cnt;
  }
  int generateClassFieldVariablesSetImplementation(PrintStream jvStrm, String[] attVal) {
    int res = 0;
    
    if (attVal[0].equals("string")) {
      res++;
      jvStrm.println("      " + global.attrPrefix + attVal[1] + " = new String(val);");
      jvStrm.println("      res = new String(" + global.attrPrefix + attVal[1] + ");");
    } else if (attVal[0].equals("boolean")) {
      res++;
      jvStrm.println("      " + global.attrPrefix + attVal[1] + " = Boolean.parseBoolean(val);");
      jvStrm.println("      res = String.valueOf(" + global.attrPrefix + attVal[1] + ");");
    } else if (attVal[0].equals("int")) {
      res++;
      jvStrm.println("      " + global.attrPrefix + attVal[1] + " = Integer.parseInt(val);");
      jvStrm.println("      res = String.valueOf(" + global.attrPrefix + attVal[1] + ");");
    }

    return res;
  }
  int generateClassFieldVariablesGetImplementation(PrintStream jvStrm, String[] attVal) {
    int res = 0;
	    
	if (attVal[0].equals("string")) {
	  res++;
	  jvStrm.println("      res = new String(" + global.attrPrefix + attVal[1] + ");");
	} else if (attVal[0].equals("boolean")) {
	  res++;
	  jvStrm.println("      res = String.valueOf(" + global.attrPrefix + attVal[1] + ");");
	} else if (attVal[0].equals("int")) {
	  res++;
	  jvStrm.println("      res = String.valueOf(" + global.attrPrefix + attVal[1] + ");");
	}

	return res;
  }
  int generateClassFieldVariablesGetSetImplementation(PrintStream jvStrm, an5ClassValue nd, boolean goalSuppress) {
    int cnt = 0;
	String[] attVar = null;
	
	jvStrm.println("  public String getVallue(String nam) {");
	jvStrm.println("    String res = null;");		    
	if (nd.attributes.size() > 0) {
		
	  attVar = nd.attributes.get(cnt);
	  jvStrm.println("    if (nam.equals(\"" + attVar[1] + "\")) {");
	  generateClassFieldVariablesGetImplementation(jvStrm, attVar);
	  for (cnt = 1; cnt < nd.attributes.size(); cnt++) {
		attVar = nd.attributes.get(cnt);
		jvStrm.println("    } else if (nam.equals(\"" + attVar[1] + "\")) {");
		generateClassFieldVariablesGetImplementation(jvStrm, attVar);		  
	  }
	  jvStrm.println("    } else {");
	  jvStrm.println("      res = super.getValue(nam);");
	  jvStrm.println("    }");
	} else {
	  jvStrm.println("    res = super.getValue(nam);");		
	}
	jvStrm.println("    return res;");
	jvStrm.println("  }");
	
	cnt = 0;
	jvStrm.println("  public String setVallue(String nam, String val) {");
	jvStrm.println("    String res = null;");		    
	if (nd.attributes.size() > 0) {
		
	  attVar = nd.attributes.get(cnt);
	  jvStrm.println("    if (nam.equals(\"" + attVar[1] + "\")) {");
	  generateClassFieldVariablesSetImplementation(jvStrm, attVar);
	  for (cnt = 1; cnt < nd.attributes.size(); cnt++) {
		attVar = nd.attributes.get(cnt);
		jvStrm.println("    } else if (nam.equals(\"" + attVar[1] + "\")) {");
		generateClassFieldVariablesSetImplementation(jvStrm, attVar);		  
	  }
	  jvStrm.println("    } else {");
	  jvStrm.println("      res = super.setValue(nam, val);");
	  jvStrm.println("    }");
	} else {
	  jvStrm.println("    res = super.setValue(nam, val);");		
	}
	jvStrm.println("    return res;");
	jvStrm.println("  }");
	return cnt;
  }
  int generateClassServiceSetVariablesImplementation(PrintStream jvStrm, an5ClassValue nd, boolean goalSuppress) {
	int i = 0;

	if (goalSuppress) {
	  if (nd.networkServices.size() > 0) {
	    jvStrm.println("  an5Service AN5AT_service = new an5ServiceMap();");  
	  }
	} else {
	  if (nd.networkServices.size() > 0) {
		
        jvStrm.print("  an5Service AN5AT_service = new an5ServiceMap(new String[]{");  
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
	  } /* else {
	    jvStrm.println("  an5Service AN5AT_service = null;");
	  } */
	}
	return i;		
  }
  
  int generateTemplateConstraintClassImplementation(an5ClassValue nd, String cClass, PrintStream jvStrm) throws FileNotFoundException {
	int cnt = 0;
	String clNm = new String(global.templatePrefix + nd.value);

    jvStrm.println("  /* -- AN5 Generated Constraint Class -- */");
    jvStrm.print("  public class " + clNm);
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
    jvStrm.println("    String an5name = \"" + nd.value + "\";");
    generateClassInterfaceVariablesImplementation(jvStrm, nd);  
    generateClassServiceSetVariablesImplementation(jvStrm, nd, false);
    generateClassObjectVariablesImplementation(jvStrm, nd, false);
    jvStrm.println("    public an5Object createInstance() {");
    jvStrm.println("      return (new " + cClass + "()).new " + global.classPrefix + nd.value + "(this, false);");    
	jvStrm.println("    }");
    jvStrm.println("    public an5Service expose() {");
	if (nd.networkServices.size() > 0) {
      jvStrm.println("      return AN5AT_service == null ? null : AN5AT_service.provides();");
	} else {
	  jvStrm.println("      return new an5ServiceMap();");
	}
	jvStrm.println("    }");
    jvStrm.println("    public " + clNm + "() {");
    jvStrm.println("      super();");
	jvStrm.println("      for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	jvStrm.println("      for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
    if (nd.interfacesReflected.size() > 0 || nd.networkServices.size() > 0) {
      jvStrm.println("      AN5AT_serviceUnion.add(AN5AT_service);");
      jvStrm.println("      AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
    }
	jvStrm.println("      goalSpec = true;");
	jvStrm.println("    }");
    /* jvStrm.println("    protected " + clNm + "(an5ClassTemplate t, boolean gf) {");
    jvStrm.println("      super(t,true);");
	jvStrm.println("      for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	jvStrm.println("      for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
    if (nd.interfacesReflected.size() > 0 || nd.networkServices.size() > 0) {
      jvStrm.println("      AN5AT_serviceUnion.add(AN5AT_service);");
      jvStrm.println("      AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
    }
	jvStrm.println("    }"); */
    /* jvStrm.println("    public " + clNm + "(JsonNode nd) {");
    jvStrm.println("      super(nd == null ? null : nd.get(\"extends\"));");
	jvStrm.println("      for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	jvStrm.println("      for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
    if (nd.interfacesReflected.size() > 0 || nd.networkServices.size() > 0) {
      jvStrm.println("      AN5AT_serviceUnion.add(AN5AT_service);");
      jvStrm.println("      AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
    }
    generateClassAttributeDeserializatonImplemenation(jvStrm, nd);
	jvStrm.println("      varUtil.setConstrutArgs(nd, this);");
    jvStrm.println("      goalSpec = true;");
	jvStrm.println("    }"); */
    jvStrm.println("  }");
  
    return cnt;
  }
  int generateTemplateClassImplementations(an5ClassValue nd) throws FileNotFoundException {
    int cnt = 0;
    PrintStream jvStrm;
	String clNm, crNm;
	
    clNm = new String(global.templatePrefix + nd.value);
    crNm = new String(global.classPrefix + nd.value);
    jvStrm = new PrintStream(dirPath + packagePath + global.pathSeperator + clNm + global.fileSuffix);
  
    jvStrm.println("/* -- AN5 Generated Class Template File -- */");
    jvStrm.println("/* -- AN5 Concept by John Hartley - Graphica Software/Dokmai Pty Ltd -- */");
    jvStrm.println("package " + symtab.current.forPackage + ";");
    jvStrm.println("import an5.model.*;");
    jvStrm.println("import com.fasterxml.jackson.databind.JsonNode;");
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
    for (an5ClassValue ccnd: nd.containedClass) {
      generateTemplateConstraintClassImplementation(ccnd, crNm, jvStrm);
    }
    jvStrm.println("  String an5name = \"" + nd.value + "\";");
    generateClassInterfaceVariablesImplementation(jvStrm, nd);
    generateClassServiceSetVariablesImplementation(jvStrm, nd, false);
    generateClassObjectVariablesImplementation(jvStrm, nd, false);
    jvStrm.println("  public an5Object createInstance() {");
    jvStrm.println("    return new " + global.classPrefix + nd.value + "(this, false);");    
	jvStrm.println("  }");
    jvStrm.println("  public an5Service expose() {");
	if (nd.networkServices.size() > 0) {
      jvStrm.println("    return AN5AT_service == null ? null : AN5AT_service.provides();");
	} else {
	  jvStrm.println("    return new an5ServiceMap();");
	}
	jvStrm.println("  }");
	if (nd.containedClass.size() > 0) {
      jvStrm.println("  public an5Object[] createConstraints() {");
	  jvStrm.println("    an5Object[] res = new an5Object[" + nd.containedClass.size() + "];");
	  for (int j = 0; j < nd.containedClass.size(); j++) {
		jvStrm.println("    res[" + j + "] = new " + global.templatePrefix + nd.containedClass.get(j).value + "();");	  
		// jvStrm.println("    res[" + j + "] = (new " + crNm + "()).new " + global.templatePrefix + nd.containedClass.get(j).value + "();");	  
	  }
	  jvStrm.println("    return res;");
	  jvStrm.println("  }");
	}
    jvStrm.println("  public " + clNm + "() {");
    jvStrm.println("    super();");
	jvStrm.println("    for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
    if (nd.interfacesReflected.size() > 0 || nd.networkServices.size() > 0) {
      jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
      jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
    }
	jvStrm.println("    goalSpec = true;");
	jvStrm.println("  }");
    /* jvStrm.println("  protected " + clNm + "(an5ClassTemplate t, boolean gf) {");
    jvStrm.println("    super(t,gf);");
	jvStrm.println("    for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
    if (nd.interfacesReflected.size() > 0 || nd.networkServices.size() > 0) {
      jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
      jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
    }
	jvStrm.println("  }"); */
    /* jvStrm.println("  public " + clNm + "(JsonNode nd) {");
    jvStrm.println("    super(nd == null ? null : nd.get(\"extends\"));");
	jvStrm.println("    for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
    if (nd.interfacesReflected.size() > 0 || nd.networkServices.size() > 0) {
     jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
     jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
    }
    generateClassAttributeDeserializatonImplemenation(jvStrm, nd);
	jvStrm.println("    varUtil.setConstrutArgs(nd, this);");
    jvStrm.println("    goalSpec = true;");
	jvStrm.println("  }"); */
    jvStrm.println("}");
    
    return cnt;
  }
  int generateJsonSerializerReflectedImplementations(PrintStream jvStrm, an5ClassValue nd) {
    int cnt = 0;
	an5InterfaceVariableValue ifVar = null;
	    
	if (nd.interfacesReflected.size() > 0) {
      jvStrm.println("    gen.writeFieldName(\"reflects\");");
      jvStrm.println("    gen.writeStartArray();");
	  for (cnt = 0; cnt < nd.interfacesReflected.size(); cnt++) {
	    ifVar = nd.interfacesReflected.get(cnt);
	    jvStrm.println("    gen.writeStartObject();");
	    jvStrm.println("    gen.writeStringField(\"name\", " + "\"" + ifVar.value + "\");");
	    jvStrm.println("    gen.writeStringField(\"policy\", an5InterfaceInstance.PolicyString[vo." + global.attrPrefix + ifVar.value + ".policy]);");
	    jvStrm.println("    if (vo." + global.attrPrefix + ifVar.value + ".policy == an5InterfaceInstance.Policy.STATIC) {");
	    jvStrm.println("      gen.writeNumberField(\"size\", vo." + global.attrPrefix + ifVar.value + ".bindings.size());");
	    jvStrm.println("      gen.writeStringField(\"template\", vo." + global.attrPrefix + ifVar.value + ".interfaceDefinition.bindingNameTemplate);");
	    jvStrm.println("    }");	    
	    jvStrm.println("    gen.writeNumberField(\"min\", vo." + global.attrPrefix + ifVar.value + ".min);");
	    jvStrm.println("    gen.writeNumberField(\"max\", vo." + global.attrPrefix + ifVar.value + ".max);");
	    jvStrm.println("    gen.writeEndObject();");
	  }
      jvStrm.println("    gen.writeEndArray();");
	}
    return cnt;
  }
  int generateJsonSerializerImplementations() throws FileNotFoundException {
    int cnt = 0;
	PrintStream jvStrm;
	String clNm, obNm;
 	String[] attVar = null;
	
    for (an5TypeValue nd : symtab.current.identifier.values()) {
      if (nd instanceof an5ClassValue) {
    	an5ClassValue clNd = (an5ClassValue)nd;
    	
    	if (! (clNd.goalSpec || clNd.constraintSpec)) {
    	  clNm = new String(global.serializerPrefix + clNd.value);
    	  obNm = new String(global.classPrefix + clNd.value);
          jvStrm = new PrintStream(dirPath + packagePath + global.pathSeperator + clNm + global.fileSuffix);
        
          jvStrm.println("/* -- AN5 Generated JSON Serializer Class File -- */");
          jvStrm.println("/* -- AN5 Concept by John Hartley - Graphica Software/Dokmai Pty Ltd -- */");
          jvStrm.println("package " + symtab.current.forPackage + ";");
          jvStrm.println("import an5.model.*;");
          jvStrm.println("import java.io.IOException;");
          jvStrm.println("import com.fasterxml.jackson.core.JsonGenerator;");
          jvStrm.println("import com.fasterxml.jackson.databind.SerializerProvider;");
          jvStrm.println("import com.fasterxml.jackson.databind.ser.std.StdSerializer;");
          jvStrm.print("public class " + clNm);
          if (clNd.classExtended != null) {
            jvStrm.print(" extends " + adjustSerializerClass(clNd.classExtended.value, global.serializerPrefix));
          }
          jvStrm.println(" {");
          jvStrm.println("  private static final long serialVersionUID = 1L;");
          jvStrm.println("  public " + clNm + "(Class<an5Object> t) {");
          jvStrm.println("    super(t);");
          jvStrm.println("  }");
          jvStrm.println("  public " + clNm + "() {");
          jvStrm.println("    this(null);");
          jvStrm.println("  }");
          jvStrm.println("  @Override");
          jvStrm.println("  public void serialize(an5Object val, JsonGenerator gen, SerializerProvider provider) throws IOException {");
          jvStrm.println("    " + obNm + " vo = (" + obNm + ")val;");
          jvStrm.println("    gen.writeStartObject();");
          // jvStrm.println("    gen.writeFieldName(\"" + clNd.value + "\");");
          // jvStrm.println("    gen.writeStartObject();");
          jvStrm.println("    gen.writeStringField(\"an5name\", vo.an5name);");
          
    	  if (clNd.attributes.size() > 0) {
    	    for (cnt = 0; cnt < clNd.attributes.size(); cnt++) {
    	      attVar = clNd.attributes.get(cnt);
    	      if (attVar[0].equals("string")) {
    	        jvStrm.println("    gen.writeStringField(\"" + attVar[1] + "\", vo." + global.attrPrefix + attVar[1] + ");");
    	      } else if (attVar[0].equals("boolean")) {
    	    	jvStrm.println("    gen.writeBooleanField(\"" + attVar[1] + "\", vo." + global.attrPrefix + attVar[1] + ");");
    	      } else if (attVar[0].equals("int")) {
    	    	jvStrm.println("    gen.writeNumberField(\"" + attVar[1] + "\", vo." + global.attrPrefix + attVar[1] + ");");
    	      }
    	    }
    	  }
    	  generateJsonSerializerReflectedImplementations(jvStrm, clNd);
          jvStrm.println("    gen.writeFieldName(\"extends\");");
          jvStrm.println("    super.serialize(val, gen, provider);");
          jvStrm.println("    gen.writeEndObject();");
          // jvStrm.println("    gen.writeEndObject();");
    	  jvStrm.println("  }");
    	  jvStrm.println("}");
    	}
      }
    }
    return cnt;
  }  
  int generateJsonDeserializerImplementations() throws FileNotFoundException {
	int cnt = 0;
    PrintStream jvStrm;
	String clNm, obNm;
	String[] attVar = null;
		
	for (an5TypeValue nd : symtab.current.identifier.values()) {
	  if (nd instanceof an5ClassValue) {
	    an5ClassValue clNd = (an5ClassValue)nd;
	    
	    if (! (clNd.goalSpec || clNd.constraintSpec)) {
	      clNm = new String(global.deserializerPrefix + clNd.value);
	      obNm = new String(global.classPrefix + clNd.value);
	      jvStrm = new PrintStream(dirPath + packagePath + global.pathSeperator + clNm + global.fileSuffix);
	        
	      jvStrm.println("/* -- AN5 Generated JSON Deserializer Class File -- */");
	      jvStrm.println("/* -- AN5 Concept by John Hartley - Graphica Software/Dokmai Pty Ltd -- */");
	      jvStrm.println("package " + symtab.current.forPackage + ";");
	      jvStrm.println("import an5.model.*;");
	      jvStrm.println("import java.io.IOException;");
	      jvStrm.println("import com.fasterxml.jackson.core.JsonParser;");
	      jvStrm.println("import com.fasterxml.jackson.core.JsonProcessingException;");
	      jvStrm.println("import com.fasterxml.jackson.databind.DeserializationContext;");
	      jvStrm.println("import com.fasterxml.jackson.databind.JsonDeserializer;");
	      jvStrm.println("import com.fasterxml.jackson.databind.JsonNode;");
          jvStrm.print("public class " + clNm);
          // if (clNd.classExtended != null) {
          //   jvStrm.print(" extends " + adjustDeserializerClass(clNd.classExtended.value, global.serializerPrefix));
          // }
          jvStrm.print(" extends JsonDeserializer<" + obNm + "> {");
          // jvStrm.println(" {");
	      jvStrm.println("  private static final long serialVersionUID = 1L;");
	      // jvStrm.println("  public " + clNm + "(Class<an5Object> t) {");
	      // jvStrm.println("    super(t);");
	      // jvStrm.println("  }");
	      // jvStrm.println("  public " + clNm + "() {");
	      // jvStrm.println("    this(null);");
	      // jvStrm.println("  }");
	      jvStrm.println("  @Override");
	      jvStrm.println("  public " + obNm + " deserialize(JsonParser jp, DeserializationContext cxt) throws IOException, JsonProcessingException {");
	      jvStrm.println("    JsonNode node = jp.getCodec().readTree(jp);");
	 	  jvStrm.println("    return new " + obNm + "(node);");
	      // jvStrm.println("    return res;");
	      jvStrm.println("  }");
	      jvStrm.println("}");
	    }
	  }
	}
	return cnt;
  }
  int generateClassAttributeDeserializatonImplemenation(PrintStream jvStrm, an5ClassValue nd) {
	int cnt = 0;
	String[] attVar = null;

	if (nd.attributes.size() > 0) {
	  jvStrm.println("    if (nd != null) {");
	  jvStrm.println("      JsonNode val = null;");
  	  for (cnt = 0; cnt < nd.attributes.size(); cnt++) {
  	    attVar = nd.attributes.get(cnt);
	    jvStrm.println("      val = nd.get(\"" + attVar[1] + "\");");
  	    if (attVar[0].equals("string")) {
  	      jvStrm.println("      if (val != null) {");
  	      jvStrm.println("        " + global.attrPrefix + attVar[1] + " = val.asText();");
  	      jvStrm.println("      }");
  	    } else if (attVar[0].equals("boolean")) {
      	  jvStrm.println("      if (val != null) {");
      	  jvStrm.println("        " + global.attrPrefix + attVar[1] + " = val.asBoolean();");
      	  jvStrm.println("      }");
  	    } else if (attVar[0].equals("int")) {
      	  jvStrm.println("      if (val != null) {");
      	  jvStrm.println("        " + global.attrPrefix + attVar[1] + " = val.asInt();");
      	  jvStrm.println("      }");
  	    }
  	  }
  	  jvStrm.println("    }");
	}
    return cnt;
  }
  
  void generateJsonSerDeserInitialisation(PrintStream jvStrm, an5ClassValue clNd) {
	String clNm = new String(global.classPrefix + clNd.value);
	String serNm = new String(global.serializerPrefix + clNd.value);  
    String deserNm = new String(global.deserializerPrefix + clNd.value);
    
    jvStrm.println("  static boolean sdReady = sdInit();");
    jvStrm.println("  static boolean sdInit() {");
    jvStrm.println("   an5JSONSerDeser.addSerDeser(" + clNm + ".class, new " + serNm + "(), new " + deserNm + "());");
    jvStrm.println("   return true;");
    jvStrm.println("  }");
  }
  
  int generateConstraintClassImplementation(an5ClassValue clNd, String ctNm, PrintStream jvStrm) throws FileNotFoundException {
	int cnt = 0;

	String clNm = new String(global.classPrefix + clNd.value);

	jvStrm.println("  /* -- AN5 Generated Constraint Class -- */");
	jvStrm.print("  public class " + clNm);
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
	jvStrm.println("    String an5name = \"" + clNd.value + "\";");
	generateClassInterfaceVariablesImplementation(jvStrm, clNd);
	generateClassServiceSetVariablesImplementation(jvStrm, clNd, clNd.goalSpec);
    generateClassObjectVariablesImplementation(jvStrm, clNd, clNd.goalSpec);
	generateClassFieldVariablesImplementation(jvStrm, clNd, clNd.goalSpec);
	if (clNd.goalSpec  || clNd.constraintSpec) {
	  jvStrm.println("    public " + clNm + "(" + ctNm + "." + global.templatePrefix + clNd.value + " from, boolean ab) {");
	  jvStrm.println("      super(from, ab);");
	  jvStrm.println("      for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	  jvStrm.println("      for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
	  if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
		jvStrm.println("      AN5AT_serviceUnion.add(AN5AT_service);");
		jvStrm.println("      AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
	  }
	  jvStrm.println("  }");    		
	}
	jvStrm.println("    public " + clNm + "() {");
	jvStrm.println("      super();");
	jvStrm.println("      for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
    jvStrm.println("      for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
	if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
	  jvStrm.println("      AN5AT_serviceUnion.add(AN5AT_service);");
	  jvStrm.println("      AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
	}
	jvStrm.println("    }");
	jvStrm.println("    public " + clNm + "(JsonNode nd) {");
	jvStrm.println("      super(nd == null ? null : nd.get(\"extends\"));");
	jvStrm.println("      for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	jvStrm.println("      for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
	if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
	  jvStrm.println("      AN5AT_serviceUnion.add(AN5AT_service);");
	  jvStrm.println("      AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
	}
	// generateClassAttributeDeserializatonImplemenation(jvStrm, clNd);
    jvStrm.println("      varUtil.setConstrutArgs(nd, this);");
	jvStrm.println("    }");
	jvStrm.println("    public " + clNm + "(" + clNm + " o) {");
	jvStrm.println("      super(o);");
	jvStrm.println("      for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	jvStrm.println("      for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
    if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
	  jvStrm.println("      AN5AT_serviceUnion.add(AN5AT_service);");
	  jvStrm.println("      AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
	}
	jvStrm.println("      varUtil.copyVars(o, this);");
    jvStrm.println("    }");
	jvStrm.println("    public Object clone() {");
	jvStrm.println("      return new " + clNm + "(this);");
	jvStrm.println("    }");
	generateClassFieldVariablesGetSetImplementation(jvStrm, clNd, clNd.goalSpec);
    jvStrm.println("  }");

	return cnt;
  }
  
  int generateClassImplementations() throws FileNotFoundException {
    int cnt = 0;
	PrintStream jvStrm;
	String clNm, ctNm;

	for (an5TypeValue nd : symtab.current.identifier.values()) {
	  if (nd instanceof an5ClassValue && (((an5ClassValue)nd).constraintSpec == false)) {
	    an5ClassValue clNd = (an5ClassValue)nd;
	    if (clNd.goalSpec) {
	      generateTemplateClassImplementations(clNd);
	    }
	    clNm = new String(global.classPrefix + clNd.value);
	    ctNm = new String(global.templatePrefix + clNd.value);
	    
	    jvStrm = new PrintStream(dirPath + packagePath + global.pathSeperator + clNm + global.fileSuffix);
	        
	    jvStrm.println("/* -- AN5 Generated Class File -- */");
	    jvStrm.println("/* -- AN5 Concept by John Hartley - Graphica Software/Dokmai Pty Ltd -- */");
	    jvStrm.println("package " + symtab.current.forPackage + ";");
	    jvStrm.println("import an5.model.*;");
	    jvStrm.println("import com.fasterxml.jackson.databind.JsonNode;");
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
	    for (an5ClassValue ccnd: clNd.containedClass) {
	        generateConstraintClassImplementation(ccnd, ctNm, jvStrm);
	      }
	    jvStrm.println("  String an5name = \"" + clNd.value + "\";");
	    if (! clNd.goalSpec) {
	      generateJsonSerDeserInitialisation(jvStrm, clNd);
	    }
	    generateClassInterfaceVariablesImplementation(jvStrm, clNd);
	    generateClassServiceSetVariablesImplementation(jvStrm, clNd, clNd.goalSpec);
	    generateClassObjectVariablesImplementation(jvStrm, clNd, clNd.goalSpec);
	    generateClassFieldVariablesImplementation(jvStrm, clNd, clNd.goalSpec);
	    if (clNd.goalSpec) {
	      jvStrm.println("  public " + clNm + "(" + global.templatePrefix + clNd.value + " from, boolean ab) {");
	      jvStrm.println("    super(from, ab);");
	      jvStrm.println("    for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	      jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
	      if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
	        jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
	        jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
	      }
	      jvStrm.println("  }");    		
	    }
	    jvStrm.println("  public " + clNm + "() {");
	    jvStrm.println("    super();");
	    jvStrm.println("    for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	    jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
	    if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
	      jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
	      jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
	    }
	    jvStrm.println("  }");
	    jvStrm.println("  public " + clNm + "(JsonNode nd) {");
	    jvStrm.println("    super(nd == null ? null : nd.get(\"extends\"));");
	    jvStrm.println("    for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	    jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
	    if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
	      jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
	      jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
	    }
	    generateClassAttributeDeserializatonImplemenation(jvStrm, clNd);
	    jvStrm.println("    varUtil.setConstrutArgs(nd, this);");
	    jvStrm.println("  }");
	    jvStrm.println("  public " + clNm + "(" + clNm + " o) {");
	    jvStrm.println("    super(o);");
	    jvStrm.println("    for (an5InterfaceTable v: AN5AT_interface) AN5AT_interfaces.put(v.name, v);");
	    jvStrm.println("    for (an5ClassInstance v: AN5AT_class) AN5AT_classes.put(v.var, v);");
	    if (clNd.interfacesReflected.size() > 0 || clNd.networkServices.size() > 0) {
	      jvStrm.println("    AN5AT_serviceUnion.add(AN5AT_service);");
	      jvStrm.println("    AN5SG_sigKeyUnion.add(this, AN5AT_interface);");
	    }
	    jvStrm.println("    varUtil.copyVars(o, this);");
	    jvStrm.println("  }");
	    jvStrm.println("  public Object clone() {");
	    jvStrm.println("    return new " + clNm + "(this);");
	    jvStrm.println("  }");
	    generateClassFieldVariablesGetSetImplementation(jvStrm, clNd, clNd.goalSpec);
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
