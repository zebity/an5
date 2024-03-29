/**
 @what Build an5 definitions symbol table via "Listener" class
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/

package an5;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import an5.an5Parser.AnnotationFieldDeclarationContext;
import an5.an5Parser.ClassDeclarationContext;
import an5.an5Parser.ClassOrInterfaceModifierContext;
import an5.an5Parser.ExpressionContext;
import an5.an5Parser.LiteralContext;
import an5.an5Parser.PrimaryContext;
import an5.an5Parser.SignatureTypeContext;
import an5.an5Parser.TypeTypeContext;
import an5.an5Parser.VariableDeclaratorContext;
import an5.an5Parser.VariableInitializerContext;

class an5ModelDefinitionsActions {
  an5Logging log = new an5Logging();
  an5Global global;
  an5SymbolTable symtab;
  String dirPath = null;
  int diags = 5;
  
  an5ModelDefinitionsActions(an5Global glob, an5SymbolTable st, String dir) {
	dirPath = dir;
    symtab = st;
    global = glob;
  }
  
  boolean isLocked(an5TypeValue nd) {
    if (nd.locked) {
  	  log.ERR(0, "<log.ERR>:AN5:Locked Node Assertion Failed: Value - '" + nd.value + "' type - '" + nd.isA + "'");
    }
    return nd.locked;
  }
  String checkArrayFlag(String f1, String f2) {
    String res = "";
    if (f1.equals(global.arrayFlag) || f2.equals(global.arrayFlag)) {
      res = "[";
    } else if (f1.equals(global.arrayFlag) && f2.equals(global.arrayFlag)) {
      res = "!";
    }
    return new String(res);
  }
  void extractTypeTypeKey(an5Parser.TypeTypeContext ctx, StringBuilder[] typeKey) {
	
	if (ctx.classOrInterfaceType() != null) {
	  typeKey[1].setLength(0);
	  typeKey[1].append(ctx.classOrInterfaceType().getText());
	}
	else if (ctx.networkType() != null) {
      typeKey[1].setLength(0);
      typeKey[1].append(ctx.networkType().getText());
    }
	else if (ctx.primitiveType() != null) {
	  typeKey[1].setLength(0);
	  typeKey[1].append(ctx.primitiveType().getText());
	}
	List<TerminalNode> leftBrackets = ctx.LBRACK();
	if (leftBrackets.size() > 0) {
	  typeKey[0].setLength(0);
	  typeKey[0].append(new String(global.arrayFlag));
	}
	else {
	  typeKey[0].setLength(0);
	}
	
    RuleContext up = ctx.parent;
	while (up != null) {
	  if (up instanceof AnnotationFieldDeclarationContext) {
	    break;
	  }
	  up = up.parent;
    }
	
	typeKey[2].setLength(0);
	if (up != null) {
      AnnotationFieldDeclarationContext anFldDec = (AnnotationFieldDeclarationContext)up;
      if (anFldDec.MANDATORY() != null) {
	    typeKey[2].append(anFldDec.MANDATORY().getText());
      }      
	}
  }
  void extractTypeListKeys(an5Parser.TypeListContext exposed, List<String[]> exposesKeys) {
	StringBuilder[] key = new StringBuilder[]{new StringBuilder(), new StringBuilder(), new StringBuilder()};
	
	if (exposed != null) {
	  List<TypeTypeContext> exposedType = exposed.typeType();
	  for (TypeTypeContext nd : exposedType) {
		key[0].setLength(0);
		key[1].setLength(0);
		key[2].setLength(0);
	    extractTypeTypeKey(nd, key);
        exposesKeys.add(new String[]{key[0].toString(), key[1].toString(), key[2].toString()});
  	    log.DBG(diags, "exposes - '" + key[1] + "'");
	  }
	}	  
  }
  void extractVarVal(VariableInitializerContext nd, StringBuilder sigStr) {
    log.DBG("extractVarVal");
    
    ExpressionContext exprCtx = nd.expression();
    PrimaryContext priCtx = exprCtx.primary();
    if (priCtx != null) {
      LiteralContext litCtx = priCtx.literal();
      if (litCtx != null) {
        if (litCtx.STRING_LITERAL() != null) {
          sigStr.setLength(0);
          sigStr.append(litCtx.STRING_LITERAL().getText());
        } else if (litCtx.integerLiteral() != null) {
          sigStr.setLength(0);
          sigStr.append(litCtx.integerLiteral().DECIMAL_LITERAL().toString());
        }
      }
	}
  }
  void extractVariableNames(an5Parser.VariableDeclaratorsContext ctx, List<String[]> varIds) {
	log.DBG("extractFullyQualifiedVariableName");
	List<VariableDeclaratorContext> id = ctx.variableDeclarator();
	
	for (VariableDeclaratorContext varDecCtx: id) {
      String array = new String();
      String arrayDim = new String();
      an5Parser.VariableDeclaratorIdContext varDecIdCtx = varDecCtx.variableDeclaratorId();
      if (varDecIdCtx.LBRACK().size() > 0) {
        array = new String(global.arrayFlag);
        arrayDim = String.valueOf(varDecIdCtx.LBRACK().size());
        varIds.add(new String[]{array, varDecIdCtx.IDENTIFIER().getText(), arrayDim});
      } else {
    	an5Parser.VariableInitializerContext varInitCxt = varDecCtx.variableInitializer();
    	if (varInitCxt != null) {
    	  StringBuilder valStr = new StringBuilder();
    	  extractVarVal(varInitCxt, valStr);
          varIds.add(new String[]{array, varDecIdCtx.IDENTIFIER().getText(), valStr.toString()});
    	} else {
          varIds.add(new String[]{array, varDecIdCtx.IDENTIFIER().getText()});
    	}
      }
    }
  }
  boolean getServicesSet(List<String> srvs, List<String> services, List<int[]> srvCardinality) {
	log.DBG("getServicesSet");
	boolean setCard = false;
	int idx;
	int[] cardinality = new int[]{0,0};
	String card, num;
	 /* NOTE: string literals include the quotes, so remove these */

	for (String val : srvs) {
      val = val.substring(1, val.length()-1);
	  cardinality[0] = 1;
	  cardinality[1] = 1;
	  if (val.charAt(0) == '(') {
	    idx = val.lastIndexOf(')');
	    if (idx != -1) {
	      card = val.substring(idx + 1, val.length());
	      if (card.length() == 1) {
	        switch (card.charAt(0)) {
	          case '*': cardinality[0] = 0;
	        			cardinality[1] = -1;
	        		    val = val.substring(1, idx);
	        			setCard = true;
	        		    break;
	          case '?': cardinality[0] = 0;
	        		    cardinality[1] = 1;
	        		    val = val.substring(1, idx);
	        		    setCard = true;
	        		    break;
	          case '+': cardinality[0] = 1;
	        		    cardinality[1] = -1;
	        		    val = val.substring(1, idx);
	        		    setCard = true;
	        		    break;
	          default: log.ERR(3, "<log.ERR>:AN5:Service has unknown cardinality indicator: [" + val + "] - '" + card + "'."); 
	        }  	 
	      }
	      else {
	        if (card.charAt(0) == '{' && card.charAt(card.length()-1) == '}') {
	          val = val.substring(1, idx);
	          idx = card.indexOf(',');
	          num = card.substring(1, idx);
	          cardinality[0] = Integer.valueOf(num);
	          num = card.substring(idx + 1, card.length()-1);
	          cardinality[1] = Integer.valueOf(num);
	          setCard = true;
	        }
	        else {
	          log.ERR(3, "<log.ERR>:AN5:Service has unkown/incomplete cardinality: [" + srvs + "].");	
	        }
	      }
	    }
	  }
	  services.add(val);
	  srvCardinality.add(new int[]{cardinality[0], cardinality[1]});
	}
	return setCard;
  }
  boolean getSignatureElementPairs(List<String> sigs, List<String[]> pairs, List<String> services, int[] cardinality) {
    log.DBG("getSignatureElementPairs");
    boolean setCard = false;
    int idx,
        cnt = 0;
    String nm, val, card, num;
    /* NOTE: string literals include the quotes, so remove these */
    
    for (String sig : sigs) {
      idx = sig.indexOf('=');
      if (idx == -1) {
		log.ERR(3, "<log.ERR>:AN5:Signature Index has no name=value pair: [" + sig + "]."); 
      }
      else {
        nm = sig.substring(1, idx);
        val = sig.substring(idx + 1, sig.length()-1);
        if (cnt == 0) {
          if (val.charAt(0) == '(') {
        	idx = val.lastIndexOf(')');
        	if (idx != -1) {
        	  card = val.substring(idx + 1, val.length());
        	  if (card.length() == 1) {
        		switch (card.charAt(0)) {
        		  case '*': cardinality[0] = 0;
        				    cardinality[1] = -1;
        				    val = val.substring(1, idx);
        				    setCard = true;
        				    break;
        		  case '?': cardinality[0] = 0;
        		            cardinality[1] = 1;
        				    val = val.substring(1, idx);
        		            setCard = true;
        		            break;
        		  case '+': cardinality[0] = 1;
        		            cardinality[1] = -1;
        				    val = val.substring(1, idx);
        		            setCard = true;
        		            break;
        		  default: log.ERR(3, "<log.ERR>:AN5:Signature Index has unknown cardinality indicator: [" + sig + "] - '" + card + "'."); 
        		}  	 
        	  }
        	  else {
        		if (card.charAt(0) == '{' && card.charAt(card.length()-1) == '}') {
        		  val = val.substring(1, idx);
        		  idx = card.indexOf(',');
        		  num = card.substring(1, idx);
        		  cardinality[0] = Integer.valueOf(num);
        		  num = card.substring(idx + 1, card.length()-1);
        		  cardinality[1] = Integer.valueOf(num);
        		  setCard = true;
        		}
        		else {
              	  log.ERR(3, "<log.ERR>:AN5:Signature Index has unkown/incomplete cardinality: [" + sig + "].");	
        		}
        	  }
        	}
          }
        }
        if (nm.equals(new String("service"))) {
    	  services.add(val);
        }
        pairs.add(new String[]{nm, val});
      }
      cnt++;
    }
    return setCard;
  }
  void extractSignatureArrayInitializer(an5InterfaceValue ifNd, int sigFor, an5Parser.ArrayInitializerContext initCxt) {
	log.DBG("extractSignatureArrayInitializer");
	StringBuilder sigStr = new StringBuilder();
    List<String> sigs = new ArrayList<>();
    boolean cardinalityConflict = false,
    		setCard;
	
	if (initCxt != null) {
	  List<VariableInitializerContext> varInitCxt = initCxt.variableInitializer();
	  for (VariableInitializerContext nd : varInitCxt) {
	    sigStr.setLength(0);
		extractVarVal(nd, sigStr);		
	    sigs.add(sigStr.toString());
	  	log.DBG(diags, "sig item - '" + sigStr + "'");
	  }
	  
	  List<String[]> pairs = new ArrayList<>();
	  List<String> services = new ArrayList<>();
	  int[] cardinality = new int[]{0,-1};
	  setCard = getSignatureElementPairs(sigs, pairs, services, cardinality);

	  isLocked(ifNd);
	  if (sigFor == an5.an5Lexer.COMMON) {
		ifNd.common = sigs;
		ifNd.commonPair = pairs;
		for (String s: services)
		  ifNd.services.add(s);
      }
      else if (sigFor == an5.an5Lexer.PROVIDES) {
  		ifNd.provides = sigs;
  		ifNd.providesPair = pairs;
  		for (String s: services)
  		  ifNd.services.add(s);    	
      }
      else if (sigFor == an5.an5Lexer.NEEDS) {
  		ifNd.needs = sigs;
  		ifNd.needsPair = pairs; 	  
      }
	  if (ifNd.cardinalityDefined && setCard) {
		cardinalityConflict = true;
	  }
	  else if (setCard) {
	    ifNd.cardinalityMin = cardinality[0];
	    ifNd.cardinalityMax = cardinality[1];
	    ifNd.cardinalityDefined = true;
	  }
	  if (cardinalityConflict) {
        log.ERR(3, "<log.ERR>:AN5:Signature cardinarlity confict: [" + ifNd.isA + "]" + ifNd.value + "."); 		  
	  }
    }
  }
  an5InterfaceValue useInterfaceValue(an5Parser.InterfaceDeclarationContext ifCtx) {
    log.DBG("useInterfaceValue");
    String id = ifCtx.IDENTIFIER().getText();
	an5InterfaceValue nd = null;
		      
    an5TypeValue res = symtab.select(id);
    if (res != null) {
	  if (res instanceof an5InterfaceValue) {
		nd = (an5InterfaceValue)res;
		if (! nd.fromMemberDec) {
		  nd = null;
		  log.ERR(3, "<log.ERR>:AN5:Duplicate Name: [" + res.isA + "]" + res.value + ".");    		   
		}
      }
	  else {
		log.ERR(3, "<log.ERR>:AN5:Duplicate Name: [" + res.isA + "]" + res.value + ".");    		    
      }
    }
    else {      
      nd = new an5InterfaceValue(id, symtab.current.forPackage);
	  res = symtab.insert(id, nd);
	}
    return nd;
  }
  an5ClassValue useClassValue(an5Parser.ClassDeclarationContext ctx, int up) {
	log.DBG("useClassValue");
	String id = ctx.IDENTIFIER().getText();
    an5ClassValue nd = null;
			      
	an5TypeValue res = symtab.select(id);
	if (res != null) {
      if (res instanceof an5ClassValue) {
		nd = (an5ClassValue)res;
		if (! nd.fromMemberDec) {
		  nd = null;
		  log.ERR(3, "<log.ERR>:AN5:Duplicate Name: [" + res.isA + "]" + res.value + ".");    		   
		}
	  }
	  else {
		log.ERR(3, "<log.ERR>:AN5:Duplicate Name: [" + res.isA + "]" + res.value + ".");    		    
	  }
	}
	else {      
	  nd = new an5ClassValue(id, symtab.current.forPackage);
	  symtab.insert(id, nd, up);
	  Object[] goalres = handleGoalClassDeclarations(ctx, nd);
	  if (goalres != null && goalres[1] == null) {
	    nd = null;
	  }
	}
	return nd;
  }
  
  public Object[] isGoalClass(an5Parser.ClassDeclarationContext ctx) {
    Object[] res = null;
    
    RuleContext up = ctx.parent;
    if (up instanceof an5Parser.MemberDeclarationContext) {

      while (up != null) {
        if (up instanceof an5Parser.ClassBodyDeclarationContext) {
    	  break;
    	}
    	up = up.parent;
      }
      
  	  if (up != null) {
  	    an5Parser.ClassBodyDeclarationContext typCtx = (an5Parser.ClassBodyDeclarationContext)up;
  	    for (an5Parser.ModifierContext modCtx: typCtx.modifier()) {
  	      if (modCtx.classOrInterfaceModifier() != null) {	    	  
  	        ClassOrInterfaceModifierContext cimc = modCtx.classOrInterfaceModifier();
  		    if (cimc.CONSTRAINT() != null) {
  		      res = new Object[] {true, false, true, "constraint"};
  		    }
  	      }
  		}
  	  }
    } else {
    
	  while (up != null) {
	    if (up instanceof an5Parser.TypeDeclarationContext) {
	      break;
	    }
	    up = up.parent;
      }
	
	  if (up != null) {
	    an5Parser.TypeDeclarationContext typCtx = (an5Parser.TypeDeclarationContext)up;
	    for (ClassOrInterfaceModifierContext modCtx: typCtx.classOrInterfaceModifier()) {
		  if (modCtx.GOAL() != null) {
		    res = new Object[] {true, true, false, "goal"};
		  }
		}
	  }
	}

	return res;
  }
    
  public an5Parser.ClassDeclarationContext getParentClassCxt(an5Parser.ClassDeclarationContext ctx) {
    an5Parser.ClassDeclarationContext res = null;
	    
	RuleContext up = ctx.parent;
	
    while (up != null) {
	  if (up instanceof an5Parser.TypeDeclarationContext) {
		an5Parser.TypeDeclarationContext tdc = (an5Parser.TypeDeclarationContext)up;
		res = tdc.classDeclaration();
		 break;
	  }
	  up = up.parent;
	}		
    return res;
  }
  
  public Object[] handleGoalClassDeclarations(an5Parser.ClassDeclarationContext ctx, an5ClassValue nd) {
  // if we have "goal" class we need to setup constraints parent
    log.DBG("handleGoalClassDeclarations");
    
    Object[] res = null;
    
    String id = ctx.IDENTIFIER().getText();
    
    Object[] classType = isGoalClass(ctx);
    
    if (classType != null && (Boolean)classType[1]) {
      nd.goalSpec = true;
      res = new Object[]{true, nd};  
    } else if (classType != null && (Boolean)classType[2]) {
  	  nd.constraintSpec = true;
  	  ClassDeclarationContext par = getParentClassCxt(ctx);
  	  if (par != null) {
  	    String parid = par.IDENTIFIER().getText();
  	    an5TypeValue fnd = symtab.select(parid);
  	    if (fnd != null) {
  	      if (fnd instanceof an5ClassValue) {
  	        an5ClassValue parnd = (an5ClassValue)fnd;
  	        parnd.containedClass.add(nd);
  	        res = new Object[] {true, nd};
  	      } else {
  	        log.ERR(3, "<ERR>:AN5:Parent Class Type Invalid: [" + fnd.isA + "]" + fnd.value + ".");   
  	      }
  	    } else {
  	      log.ERR(3, "<ERR>:AN5:Constraint Parent Class: '" + parid + "' not found: [" + nd.isA + "]" + nd.value + ".");  
  	    }
  	  } else {
  	    log.ERR(3, "<ERR>:AN5: Failed to create new Constraint Class: '" + id + "'."); 
  	  }
    }
    return res;
  }
  
  public void enterBlock(an5Parser.BlockContext ctx) {
	log.DBG("enterBlock");
	symtab.current = symtab.current.addChild();
  }
  public void enterClassDeclaration(an5Parser.ClassDeclarationContext ctx) {
  // if we have "goal" class we need to add node into symbol table so we can add handler & constraints
    log.DBG("enterClassDeclaration");
  }

  public void enterCompilationUnit(an5Parser.CompilationUnitContext ctx) {
	log.DBG("enterCompilationUnit");
	symtab.reset();
  }
  public void exitBlock(an5Parser.BlockContext ctx) {
	log.DBG("exitBlock");
    symtab.current = symtab.current.getParent();
  }
  public void exitClassDeclaration(an5Parser.ClassDeclarationContext ctx) {
    log.DBG("exitClassDeclaration");
    String id = ctx.IDENTIFIER().getText();
    StringBuilder[] extendsKey = new StringBuilder[]{new StringBuilder(),new StringBuilder("object"), new StringBuilder()};
    List<String[]> exposesKeys = new ArrayList<>();
    an5TypeValue res;
    
    extractTypeTypeKey(ctx.typeType(), extendsKey);
    extractTypeListKeys(ctx.typeList(), exposesKeys);
   
	log.DBG(diags, "Class - '" + id + "' extends - '" + extendsKey[1] + "'");
	    
    an5ClassValue nd = useClassValue(ctx, 0);
    if (nd != null) {
      nd.fromMemberDec = false;
      res = symtab.select(extendsKey[1].toString());
      if (res == null) {
    	nd.classExtended = new an5UnresolvedClassValue("class", extendsKey[1].toString(), symtab.current.forPackage);
      }
      else if (res instanceof an5UnresolvedClassValue) {
    	an5UnresolvedClassValue fix = (an5UnresolvedClassValue)res;
    	fix.resolvedTo = res;
      }
      else if (res instanceof an5ClassValue) {
      	nd.classExtended = (an5ClassValue)res;
      }
      else {
    	log.ERR(3, "<ERR>:AN5:Class Extension Type Invalid: [" + res.isA + "]" + res.value + ".");   	  
      }
      
  	  for (String[] s: exposesKeys) {
        res = symtab.select(s[1]);
        if (res == null) {
    	  nd.interfacesExposed.add(new an5UnresolvedInterfaceValue("interface", s[1], symtab.current.forPackage));
        }
        else if (res instanceof an5UnresolvedInterfaceValue) {
    	  an5UnresolvedInterfaceValue fix = (an5UnresolvedInterfaceValue)res;
    	  fix.resolvedTo = res;
        }
        else if (res instanceof an5InterfaceValue) {
    	  nd.interfacesExposed.add((an5InterfaceValue)res);
        }
        else {
    	  log.ERR(3, "<ERR>:AN5:Interface Exposed Type Invalid: [" + res.isA + "]" + res.value + ".");
        }
  	  }
  	  nd.locked = true;
    }
  }
  public void exitCompilationUnit(an5Parser.CompilationUnitContext ctx) {
	log.DBG("exitCompilationUnit");
	int res;
	
	an5Generate generator = new an5Generate(global, symtab, dirPath);
	
	res = generator.makePackage();
	try {
      res = generator.generateInterfaceDefinitions();
	  res = generator.generateInterfaceImplementations();
	  res = generator.generateClassImplementations();
	  res = generator.generateJsonSerializerImplementations();
	  res = generator.generateJsonDeserializerImplementations();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
  }

  public void exitFieldDeclaration(an5Parser.FieldDeclarationContext ctx) {
    log.DBG("exitFieldDeclaration");
	/* get parent interface */
	RuleContext up = ctx.parent;
	while (up != null) {
      if (up instanceof an5Parser.ClassDeclarationContext) {
        break;
      }
      up = up.parent;
	}
	
	/* Extract attributes */
	if (up == null) {
	  log.ERR(3, "<ERR>:AN5:Class Field Parent Not Found.");		
	}
	else {
	  an5ClassValue nd = useClassValue((an5Parser.ClassDeclarationContext)up, 0);

	  if (nd != null) {

	    TypeTypeContext sigCtx = ctx.typeType();
	    StringBuilder[] typeVal = new StringBuilder[]{new StringBuilder(), new StringBuilder(), new StringBuilder()};
		List<String[]> varIds = new ArrayList<>();
		String arFlag;
		
		extractTypeTypeKey(sigCtx, typeVal);
		extractVariableNames(ctx.variableDeclarators(), varIds);
		
		an5TypeValue res = symtab.select(typeVal[1].toString());
		if (res != null) {
		  if (res instanceof an5ClassValue) {
			for (String[] val: varIds) {
			  arFlag = checkArrayFlag(typeVal[0].toString(), val[0]);
			  if (arFlag.equals(global.arrayErr)) {
				log.ERR(3, "<ERR>:AN5:Class Field [][] used.");				  
			  } else {
			    nd.contained.add(new an5ClassVariableValue(val[1], nd.inPackage,
			    		               (an5ClassValue)res, arFlag, typeVal[2].toString()));
			  }
		    }
		  } else {
            log.ERR(3, "<ERR>:AN5:Class Field Not Class or Primative.");	
		  }
		}
		else {
		  for (String[] val: varIds) {
			if (val.length == 2) {
		      nd.attributes.add(new String[]{typeVal[1].toString(), val[1]});
			} else if (val.length == 3) {
			  nd.attributes.add(new String[]{typeVal[1].toString(), val[1], val[2]});
			}
		  }
		}
      }
    }
  }
  public void exitInterfaceBindingNameTemplate(an5Parser.InterfaceBindingNameTemplateContext ctx) {
	log.DBG("exitInterfaceBindingNameTemplate");
	/* get parent interface */
	RuleContext up = ctx.parent;
	while (up != null) {
      if (up instanceof an5Parser.InterfaceDeclarationContext) {
        break;
      }
      up = up.parent;
	}
	
	/* get naming template */
	if (up == null) {
	  log.ERR(3, "<ERR>:AN5:Interface Binding Naming Template Parent Not Found.");		
	}
	else {
	  an5InterfaceValue ifNd = useInterfaceValue((an5Parser.InterfaceDeclarationContext)up);

	  if (ifNd != null) {
	    isLocked(ifNd);
		TerminalNode termNd = ctx.STRING_LITERAL();
		String tS = new String(termNd.getText());
		ifNd.nameTemplate = new String(tS.substring(1, tS.length()-1));
      }
    }
  }
  public void exitInterfaceDeclaration(an5Parser.InterfaceDeclarationContext ctx) {
    log.DBG("exitInterfaceDeclaration");
    List<String[]> exposesKeys = new ArrayList<>();
    an5TypeValue res = null;
    
    extractTypeListKeys(ctx.typeList(), exposesKeys);
    
    an5InterfaceValue nd = useInterfaceValue(ctx);
    if (nd != null) {
      nd.fromMemberDec = false;
      for (String[] s: exposesKeys) {
        res = symtab.select(s[1]);
    	if (res == null) {
    	  nd.interfacesExtended.add(new an5UnresolvedInterfaceValue("interface", s[1], global.basePackage));
    	}
    	else if (res instanceof an5UnresolvedInterfaceValue) {
    	  an5UnresolvedInterfaceValue fix = (an5UnresolvedInterfaceValue)res;
    	  fix.resolvedTo = res;
    	}
    	else if (res instanceof an5InterfaceValue) {
    	  nd.interfacesExtended.add((an5InterfaceValue)res);
    	}
    	else {
    	  log.ERR(3, "<ERR>:AN5:Interface Extension Type Invalid: [" + res.isA + "]" + res.value + ".");
    	}
      }
      nd.locked = true;
    }
//    symtab.current = symtab.current.getParent();
  }
  public void exitInterfaceAttributeDeclaration(an5Parser.InterfaceAttributeDeclarationContext ctx) {
    log.DBG("exitInterfaceAttributeDeclaration");
	/* get parent interface */
	RuleContext up = ctx.parent;
	while (up != null) {
      if (up instanceof an5Parser.InterfaceDeclarationContext) {
        break;
      }
      up = up.parent;
	}
	
	/* Extract attributes */
	if (up == null) {
	  log.ERR(3, "<ERR>:AN5:Interface Attributes Parent Not Found.");		
	}
	else {
	  an5InterfaceValue ifNd = useInterfaceValue((an5Parser.InterfaceDeclarationContext)up);

	  if (ifNd != null) {
	    isLocked(ifNd);
		TypeTypeContext sigCtx = ctx.typeType();
		StringBuilder[] typeVal = new StringBuilder[]{new StringBuilder(), new StringBuilder(), new StringBuilder()};
		List<String[]> attrs = new ArrayList<String[]>();
		
		if (sigCtx != null) {
          typeVal[1].setLength(0);
		  extractTypeTypeKey(sigCtx, typeVal);
		  List<TerminalNode> ids = ctx.IDENTIFIER();
		  for (TerminalNode id : ids) {
		     attrs.add(new String[]{typeVal[1].toString(), id.getText()});
		  	 log.DBG(diags, "attribute type - '" + typeVal[1] + "' value - '" + id.getText() + "'.");
		  }
	  	  ifNd.attributes = attrs;
		}
      }
    }
  }
  public void exitInterfaceSignatureDeclaration(an5Parser.InterfaceSignatureDeclarationContext ctx) {
	log.DBG("exitInterfaceSignatureDeclaration");
	/* get parent interface */
	RuleContext up = ctx.parent;
	while (up != null) {
      if (up instanceof an5Parser.InterfaceDeclarationContext) {
        break;
      }
      up = up.parent;
	}
	
	/* Extract signatures */
	if (up == null) {
      log.ERR(3, "<ERR>:AN5:Interface Signature Parent Not Found.");		
	}
	else {
	  an5InterfaceValue ifNd = useInterfaceValue((an5Parser.InterfaceDeclarationContext)up);

      if (ifNd != null) {
	    SignatureTypeContext sigCtx = ctx.signatureType();
        if (sigCtx.COMMON() != null) {
    	  extractSignatureArrayInitializer(ifNd, an5Lexer.COMMON, ctx.arrayInitializer());    	  
        }
        else if (sigCtx.NEEDS() != null) {
    	  extractSignatureArrayInitializer(ifNd, an5Lexer.NEEDS, ctx.arrayInitializer());    	  
        }
        else if (sigCtx.PROVIDES() != null) {
    	  extractSignatureArrayInitializer(ifNd, an5Lexer.PROVIDES, ctx.arrayInitializer());    	  
        }
	  }
	}
  }
  public void exitInterfaceVariableDeclaration(an5Parser.InterfaceVariableDeclarationContext ctx) {
	log.DBG("exitInterfaceVariableDeclaration");
		    
	/* get parent class */
	RuleContext up = ctx.parent;
	while (up != null) {
	  if (up instanceof an5Parser.ClassDeclarationContext) {
		break;
	  }
	  up = up.parent;
	}
			
	/* Extract interface instance */
	if (up == null) {
	  log.ERR(3, "<ERR>:AN5:Class Interfaces Parent Not Found.");		
	}
	else {
	  an5ClassValue nd = useClassValue((an5Parser.ClassDeclarationContext)up, 0);

	  if (nd != null) {
		  
		TypeTypeContext sigCtx = ctx.typeType();
		StringBuilder[] typeVal = new StringBuilder[]{new StringBuilder(), new StringBuilder(), new StringBuilder()};
				
		if (sigCtx != null) {
		  List<String[]> varIds = new ArrayList<>();
		  extractTypeTypeKey(sigCtx, typeVal);
		  extractVariableNames(ctx.variableDeclarators(), varIds);
          
		  for (String[] varNm : varIds) {
		    an5TypeValue res = symtab.select(typeVal[1].toString());
		    if (res != null) {
			  if (res instanceof an5InterfaceValue) {
				an5InterfaceValue ifNd = (an5InterfaceValue)res;
			    String flag = new String(typeVal[0].toString());
			    if (flag.equals(global.arrayFlag)  && varNm[0].equals(global.arrayFlag)) {
			      log.ERR(3, "<ERR>:AN5:Class Interface Variable is type [][]: - " + res.value + " is-a:" + res.isA + " .");
			    }
			    else if (varNm[0].equals(global.arrayFlag)) {
			      flag = varNm[0];
			    }
			    nd.interfacesReflected.add(new an5InterfaceVariableValue(varNm[1], symtab.current.forPackage, ifNd, flag));	  
			  } else {
		        log.ERR(3, "<ERR>:AN5:Class Interface Variable wrong type: - " + res.value + " is-a:" + res.isA + " .");	
			  }
		    }
		    else {
		      log.ERR(3, "<ERR>:AN5:Class Interface Variable Undefined: '" + typeVal[1].toString() + "' .");	
		    }
		  }
		}
	  }
    }
  }
  public void exitPackageDeclaration(an5Parser.PackageDeclarationContext ctx) {
    log.DBG("exitPackageDeclaration");
    String qualName;
    List<TerminalNode> nodes = ctx.qualifiedName().IDENTIFIER();
    
    qualName = nodes.get(0).getText();
    for (int i = 1; i < nodes.size(); i++) 
      qualName = qualName + "." + nodes.get(i);
    log.DBG(diags, "Adding Package: " + qualName);
    an5ModelContext res = symtab.packageContexts.get(qualName);
    if (res == null) {
      symtab.current = new an5ModelContext(qualName);
      symtab.packageContexts.put(qualName, symtab.current);
      symtab.searchList.add(symtab.current);
    }
    else {
      symtab.current = res;
    }
  }
  public void exitServiceSignatureDeclaration(an5Parser.ServiceSignatureDeclarationContext ctx) {
	log.DBG("exitServiceSignatureDeclaration");
	/* get parent interface */
	RuleContext up = ctx.parent;
	while (up != null) {
      if (up instanceof an5Parser.ClassDeclarationContext) {
        break;
      }
      up = up.parent;
	}
	
	/* Extract services */
	if (up == null) {
		  log.ERR(3, "<ERR>:AN5:Class Services Parent Not Found.");		
	}
	else {
	  an5ClassValue nd = useClassValue((an5Parser.ClassDeclarationContext)up, 0);
      StringBuilder srvStr = new StringBuilder(); 
	  List<String> vals = new ArrayList<>();
	  List<String> services = new ArrayList<>();
	  List<int[]> card = new ArrayList<>();
	  
	  if (nd != null) {
		isLocked(nd);
        List<VariableInitializerContext> varInitCxt = ctx.arrayInitializer().variableInitializer();
		for (VariableInitializerContext vCtx : varInitCxt) {
		  srvStr.setLength(0);
		  extractVarVal(vCtx, srvStr);		
		  vals.add(srvStr.toString());
		  log.DBG(diags, "service item - '" + srvStr + "'");
		} 
		getServicesSet(vals, services, card);
      }
	  nd.networkServices.add(new an5ServiceSetValue("service", nd.inPackage, services, card));
    }
  }
  public void exitHandlerDeclaration(an5Parser.HandlerDeclarationContext ctx) {
	log.DBG("exitHandlerDeclaration");
	/* get parent interface */
	RuleContext up = ctx.parent;
	while (up != null) {
      if (up instanceof an5Parser.ClassDeclarationContext) {
        break;
      }
      up = up.parent;
	}
	
	/* Extract handler class name */
	if (up == null) {
	  log.ERR(3, "<ERR>:AN5:Class Handler Parent not found.");		
	}
	else {
	  String handler = null;
	  ClassDeclarationContext cdc = (an5Parser.ClassDeclarationContext)up;
	  an5ClassValue nd = useClassValue(cdc, 0);
	  
	  if (nd != null) {
		isLocked(nd);
	    TerminalNode tn = ctx.STRING_LITERAL();
	    if (tn != null) {
	      String ltStr = tn.getText();
          handler = ltStr.substring(1, ltStr.length()-1);
		  log.DBG(diags, "goal handler - '" + handler + "'");
		  nd.handler = handler;
	    }
      }
	  log.ERR(3, "<ERR>:AN5:Class Parent Value Node not found - '" + cdc.IDENTIFIER().getText() + "'.");
    }
  }
}
