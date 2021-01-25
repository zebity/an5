/*
 What: Build an5 definitions symbol table via "Listener" class
*/

package an5;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import an5.an5Parser;
import an5.an5ParserBaseListener;
import an5.an5Parser.AltAnnotationQualifiedNameContext;
import an5.an5Parser.AnnotationConstantRestContext;
import an5.an5Parser.AnnotationContext;
import an5.an5Parser.AnnotationMethodOrConstantRestContext;
import an5.an5Parser.AnnotationMethodRestContext;
import an5.an5Parser.AnnotationTypeBodyContext;
import an5.an5Parser.AnnotationTypeDeclarationContext;
import an5.an5Parser.AnnotationTypeElementDeclarationContext;
import an5.an5Parser.AnnotationTypeElementRestContext;
import an5.an5Parser.ArgumentsContext;
import an5.an5Parser.ArrayInitializerContext;
import an5.an5Parser.BlockContext;
import an5.an5Parser.BlockStatementContext;
import an5.an5Parser.ClassBodyContext;
import an5.an5Parser.ClassBodyDeclarationContext;
import an5.an5Parser.ClassDeclarationContext;
import an5.an5Parser.ClassOrInterfaceModifierContext;
import an5.an5Parser.ClassOrInterfaceTypeContext;
import an5.an5Parser.CompilationUnitContext;
import an5.an5Parser.ConstDeclarationContext;
import an5.an5Parser.ConstantDeclaratorContext;
import an5.an5Parser.DefaultValueContext;
import an5.an5Parser.ElementValueArrayInitializerContext;
import an5.an5Parser.ElementValueContext;
import an5.an5Parser.ElementValuePairContext;
import an5.an5Parser.ElementValuePairsContext;
import an5.an5Parser.EnumBodyDeclarationsContext;
import an5.an5Parser.EnumConstantContext;
import an5.an5Parser.EnumConstantsContext;
import an5.an5Parser.EnumDeclarationContext;
import an5.an5Parser.ExpressionContext;
import an5.an5Parser.ExpressionListContext;
import an5.an5Parser.FieldDeclarationContext;
import an5.an5Parser.FloatLiteralContext;
import an5.an5Parser.FormalParameterContext;
import an5.an5Parser.FormalParameterListContext;
import an5.an5Parser.FormalParametersContext;
import an5.an5Parser.ImportDeclarationContext;
import an5.an5Parser.IntegerLiteralContext;
import an5.an5Parser.InterfaceBodyContext;
import an5.an5Parser.InterfaceBodyDeclarationContext;
import an5.an5Parser.InterfaceDeclarationContext;
import an5.an5Parser.InterfaceMemberDeclarationContext;
import an5.an5Parser.InterfaceMethodDeclarationContext;
import an5.an5Parser.InterfaceMethodModifierContext;
import an5.an5Parser.InterfaceSignatureDeclarationContext;
import an5.an5Parser.LastFormalParameterContext;
import an5.an5Parser.LiteralContext;
import an5.an5Parser.LocalTypeDeclarationContext;
import an5.an5Parser.LocalVariableDeclarationContext;
import an5.an5Parser.MemberDeclarationContext;
import an5.an5Parser.ModifierContext;
import an5.an5Parser.NetworkTypeContext;
import an5.an5Parser.PackageDeclarationContext;
import an5.an5Parser.ParExpressionContext;
import an5.an5Parser.PrimaryContext;
import an5.an5Parser.PrimitiveTypeContext;
import an5.an5Parser.QualifiedNameContext;
import an5.an5Parser.QualifiedNameListContext;
import an5.an5Parser.SignatureTypeContext;
import an5.an5Parser.StatementContext;
import an5.an5Parser.TypeArgumentContext;
import an5.an5Parser.TypeDeclarationContext;
import an5.an5Parser.TypeListContext;
import an5.an5Parser.TypeTypeContext;
import an5.an5Parser.TypeTypeOrVoidContext;
import an5.an5Parser.VariableDeclaratorContext;
import an5.an5Parser.VariableDeclaratorIdContext;
import an5.an5Parser.VariableDeclaratorsContext;
import an5.an5Parser.VariableInitializerContext;
import an5.an5Parser.VariableModifierContext;

class an5ModelDefinitionsListener extends an5ParserBaseListener {
  an5Logging log = new an5Logging();
  an5Global globalDefs = new an5Global();
  int diags = 5;
  an5SymbolTable symtab;
  an5ModelDefinitionsListener() {
    symtab = new an5SymbolTable();
  }
  void extractTypeTypeKey(an5Parser.TypeTypeContext extender, StringBuilder extendsKey) {
	an5Parser.NetworkTypeContext netExtenders;
	an5Parser.ClassOrInterfaceTypeContext clOrIfExtenders;
	an5Parser.PrimitiveTypeContext primExtenders;
	    
	for (ParseTree nd : extender.children) {
	  if (nd instanceof an5Parser.ClassOrInterfaceTypeContext) {
	    clOrIfExtenders = (an5Parser.ClassOrInterfaceTypeContext)nd;
	    extendsKey.setLength(0);
	    extendsKey.append(clOrIfExtenders.getText());
	  } else if (nd instanceof an5Parser.NetworkTypeContext) {
	    netExtenders = (an5Parser.NetworkTypeContext)nd;
	    extendsKey.setLength(0);
	    extendsKey.append(netExtenders.getText());    		
	  } else if (nd instanceof an5Parser.PrimitiveTypeContext) {
	    primExtenders = (an5Parser.PrimitiveTypeContext)nd;
	    extendsKey.setLength(0);
	    extendsKey.append(primExtenders.getText());     		
	  }
	}
  }
  void extractTypeListKeys(an5Parser.TypeListContext exposed, List<String> exposesKeys) {
	StringBuilder key = new StringBuilder();
	
	if (exposed != null) {
	  List<TypeTypeContext> exposedType = exposed.typeType();
	  for (TypeTypeContext nd : exposedType) {
		key.setLength(0);
	    extractTypeTypeKey(nd, key);
        exposesKeys.add(key.toString());
  	    log.DBG(diags, "exposes - '" + key + "'");
	  }
	}	  
  }
  public void enterAltAnnotationQualifiedName(an5Parser.AltAnnotationQualifiedNameContext ctx) { log.DBG("enterAltAnnotationQualifiedName"); }
  public void enterAnnotation(an5Parser.AnnotationContext ctx) { log.DBG("enterAnnotation"); }
  public void enterAnnotationConstantRest(an5Parser.AnnotationConstantRestContext ctx) { log.DBG("enterAnnotationConstantRest"); }
  public void enterAnnotationMethodOrConstantRest(an5Parser.AnnotationMethodOrConstantRestContext ctx) { log.DBG("enterAnnotationMethodOrConstantRest"); }
  public void enterAnnotationMethodRest(an5Parser.AnnotationMethodRestContext ctx) { log.DBG("enterAnnotationMethodRest"); }
  public void enterAnnotationTypeBody(an5Parser.AnnotationTypeBodyContext ctx) { log.DBG("enterAnnotationTypeBody"); }
  public void enterAnnotationTypeDeclaration(an5Parser.AnnotationTypeDeclarationContext ctx) { log.DBG("enterAnnotationTypeDeclaration"); }
  public void enterAnnotationTypeElementDeclaration(an5Parser.AnnotationTypeElementDeclarationContext ctx) { log.DBG("enterAnnotationTypeElementDeclaration"); }
  public void enterAnnotationTypeElementRest(an5Parser.AnnotationTypeElementRestContext ctx) { log.DBG("enterAnnotationTypeElementRest"); }
  public void enterArguments(an5Parser.ArgumentsContext ctx) { log.DBG("enterArguments"); }
  public void enterArrayInitializer(an5Parser.ArrayInitializerContext ctx) { log.DBG("enterArrayInitializer"); }
  public void enterBlock(an5Parser.BlockContext ctx) {
	log.DBG("enterBlock");
	symtab.current = symtab.current.addChild();
  }
  public void enterBlockStatement(an5Parser.BlockStatementContext ctx) { log.DBG("enterBlockStatement"); }
  public void enterClassBody(an5Parser.ClassBodyContext ctx) { log.DBG("enterClassBody"); }
  public void enterClassBodyDeclaration(an5Parser.ClassBodyDeclarationContext ctx) { log.DBG("enterClassBodyDeclaration"); }
  public void enterClassDeclaration(an5Parser.ClassDeclarationContext ctx) {
    log.DBG("enterClassDeclaration");
//    symtab.current = symtab.current.addChild();
  }
  public void enterClassOrInterfaceModifier(an5Parser.ClassOrInterfaceModifierContext ctx) { log.DBG("enterClassOrInterfaceModifier"); }
  public void enterClassOrInterfaceType(an5Parser.ClassOrInterfaceTypeContext ctx) { log.DBG("enterClassOrInterfaceType"); }
  public void enterCompilationUnit(an5Parser.CompilationUnitContext ctx) {
	log.DBG("enterCompilationUnit");
	symtab.reset();
  }
  public void enterConstantDeclarator(an5Parser.ConstantDeclaratorContext ctx) {
    log.DBG("enterConstantDeclarator");
  }
  public void enterConstDeclaration(an5Parser.ConstDeclarationContext ctx) { log.DBG("enterConstDeclaration"); }
  public void enterDefaultValue(an5Parser.DefaultValueContext ctx) { log.DBG("enterDefaultValue"); }
  public void enterElementValue(an5Parser.ElementValueContext ctx) { log.DBG("enterElementValue"); }
  public void enterElementValueArrayInitializer(an5Parser.ElementValueArrayInitializerContext ctx) { log.DBG("enterElementValueArrayInitializer"); }
  public void enterElementValuePair(an5Parser.ElementValuePairContext ctx) { log.DBG("enterElementValuePair"); }
  public void enterElementValuePairs(an5Parser.ElementValuePairsContext ctx) { log.DBG("enterElementValuePairs"); }
  public void enterEnumBodyDeclarations(an5Parser.EnumBodyDeclarationsContext ctx) { log.DBG("enterEnumBodyDeclarations"); }
  public void enterEnumConstant(an5Parser.EnumConstantContext ctx) { log.DBG("enterEnumConstant"); }
  public void enterEnumConstants(an5Parser.EnumConstantsContext ctx) { log.DBG("enterEnumConstants"); }
  public void enterEnumDeclaration(an5Parser.EnumDeclarationContext ctx) { log.DBG("enterEnumDeclaration"); }
  public void enterExpression(an5Parser.ExpressionContext ctx) { log.DBG("enterExpression"); }
  public void enterExpressionList(an5Parser.ExpressionListContext ctx) { log.DBG("enterExpressionList"); }
  public void enterFieldDeclaration(an5Parser.FieldDeclarationContext ctx) { log.DBG("enterFieldDeclaration"); }
  public void enterFloatLiteral(an5Parser.FloatLiteralContext ctx) { log.DBG("enterFloatLiteral"); }
  public void enterFormalParameter(an5Parser.FormalParameterContext ctx) { log.DBG("enterFormalParameter"); }
  public void enterFormalParameterList(an5Parser.FormalParameterListContext ctx) { log.DBG("enterFormalParameterList"); }
  public void enterFormalParameters(an5Parser.FormalParametersContext ctx) { log.DBG("enterFormalParameters"); }
  public void enterImportDeclaration(an5Parser.ImportDeclarationContext ctx) { log.DBG("enterImportDeclaration"); }
  public void enterIntegerLiteral(an5Parser.IntegerLiteralContext ctx) { log.DBG("enterIntegerLiteral"); }
  public void enterInterfaceBody(an5Parser.InterfaceBodyContext ctx) { log.DBG("enterInterfaceBody"); }
  public void enterInterfaceBodyDeclaration(an5Parser.InterfaceBodyDeclarationContext ctx) { log.DBG("enterInterfaceBodyDeclaration"); }
  public void enterInterfaceDeclaration(an5Parser.InterfaceDeclarationContext ctx) {
    log.DBG("enterInterfaceDeclaration");
//    symtab.current = symtab.current.addChild();
  }
  public void enterInterfaceMemberDeclaration(an5Parser.InterfaceMemberDeclarationContext ctx) { log.DBG("enterInterfaceMemberDeclaration"); }
  public void enterInterfaceMethodDeclaration(an5Parser.InterfaceMethodDeclarationContext ctx) { log.DBG("enterInterfaceMethodDeclaration"); }
  public void enterInterfaceMethodModifier(an5Parser.InterfaceMethodModifierContext ctx) { log.DBG("enterInterfaceMethodModifier"); }
  public void enterInterfaceSignatureDeclaration(an5Parser.InterfaceSignatureDeclarationContext ctx) { log.DBG("enterInterfaceSignatureDeclaration"); }
  public void enterLastFormalParameter(an5Parser.LastFormalParameterContext ctx) { log.DBG("enterLastFormalParameter"); }
  public void enterLiteral(an5Parser.LiteralContext ctx) { log.DBG("enterLiteral"); }
  public void enterLocalTypeDeclaration(an5Parser.LocalTypeDeclarationContext ctx) { log.DBG("enterLocalTypeDeclaration"); }
  public void enterLocalVariableDeclaration(an5Parser.LocalVariableDeclarationContext ctx) { log.DBG("enterLocalVariableDeclaration"); }
  public void enterMemberDeclaration(an5Parser.MemberDeclarationContext ctx) { log.DBG("enterMemberDeclaration"); }
  public void enterModifier(an5Parser.ModifierContext ctx) { log.DBG("enterModifier"); }
  public void enterNetworkType(an5Parser.NetworkTypeContext ctx) { log.DBG("enterNetworkType"); }
  public void enterPackageDeclaration(an5Parser.PackageDeclarationContext ctx) {
    log.DBG("enterPackageDeclaration");
  }
  public void enterParExpression(an5Parser.ParExpressionContext ctx) { log.DBG("enterParExpression"); }
  public void enterPrimary(an5Parser.PrimaryContext ctx) { log.DBG("enterPrimary"); }
  public void enterPrimitiveType(an5Parser.PrimitiveTypeContext ctx) { log.DBG("enterPrimitiveType"); }
  public void enterQualifiedName(an5Parser.QualifiedNameContext ctx) { log.DBG("enterQualifiedName"); }
  public void enterQualifiedNameList(an5Parser.QualifiedNameListContext ctx) { log.DBG("enterQualifiedNameList"); }
  public void enterSignatureType(an5Parser.SignatureTypeContext ctx) { log.DBG("enterSignatureType"); }
  public void enterStatement(an5Parser.StatementContext ctx) { log.DBG("enterStatement"); }
  public void enterTypeArgument(an5Parser.TypeArgumentContext ctx) { log.DBG("enterTypeArgument"); }
  public void enterTypeDeclaration(an5Parser.TypeDeclarationContext ctx) { log.DBG("enterTypeDeclaration"); }
  public void enterTypeList(an5Parser.TypeListContext ctx) { log.DBG("enterTypeList"); }
  public void enterTypeType(an5Parser.TypeTypeContext ctx) { log.DBG("enterTypeType"); }
  public void enterTypeTypeOrVoid(an5Parser.TypeTypeOrVoidContext ctx) { log.DBG("enterTypeTypeOrVoid"); }
  public void enterVariableDeclarator(an5Parser.VariableDeclaratorContext ctx) { log.DBG("enterVariableDeclarator"); }
  public void enterVariableDeclaratorId(an5Parser.VariableDeclaratorIdContext ctx) { log.DBG("enterVariableDeclaratorId"); }
  public void enterVariableDeclarators(an5Parser.VariableDeclaratorsContext ctx) { log.DBG("enterVariableDeclarators"); }
  public void enterVariableInitializer(an5Parser.VariableInitializerContext ctx) { log.DBG("enterVariableInitializer"); }
  public void enterVariableModifier(an5Parser.VariableModifierContext ctx) { log.DBG("enterVariableModifier"); }
  public void exitAltAnnotationQualifiedName(an5Parser.AltAnnotationQualifiedNameContext ctx) { log.DBG("exitAltAnnotationQualifiedName"); }
  public void exitAnnotation(an5Parser.AnnotationContext ctx) { log.DBG("exitAnnotation"); }
  public void exitAnnotationConstantRest(an5Parser.AnnotationConstantRestContext ctx) { log.DBG("exitAnnotationConstantRest"); }
  public void exitAnnotationMethodOrConstantRest(an5Parser.AnnotationMethodOrConstantRestContext ctx) { log.DBG("exitAnnotationMethodOrConstantRest"); }
  public void exitAnnotationMethodRest(an5Parser.AnnotationMethodRestContext ctx) { log.DBG("exitAnnotationMethodRest"); }
  public void exitAnnotationTypeBody(an5Parser.AnnotationTypeBodyContext ctx) { log.DBG("exitAnnotationTypeBody"); }
  public void exitAnnotationTypeDeclaration(an5Parser.AnnotationTypeDeclarationContext ctx) { log.DBG("exitAnnotationTypeDeclaration"); }
  public void exitAnnotationTypeElementDeclaration(an5Parser.AnnotationTypeElementDeclarationContext ctx) { log.DBG("exitAnnotationTypeElementDeclaration"); }
  public void exitAnnotationTypeElementRest(an5Parser.AnnotationTypeElementRestContext ctx) { log.DBG("exitAnnotationTypeElementRest"); }
  public void exitArguments(an5Parser.ArgumentsContext ctx) { log.DBG("exitArguments"); }
  public void exitArrayInitializer(an5Parser.ArrayInitializerContext ctx) { log.DBG("exitArrayInitializer"); }
  public void exitBlock(an5Parser.BlockContext ctx) {
	log.DBG("exitBlock");
    symtab.current = symtab.current.getParent();
  }
  public void exitBlockStatement(an5Parser.BlockStatementContext ctx) { log.DBG("exitBlockStatement"); }
  public void exitClassBody(an5Parser.ClassBodyContext ctx) { log.DBG("exitClassBody"); }
  public void exitClassBodyDeclaration(an5Parser.ClassBodyDeclarationContext ctx) { log.DBG("exitClassBodyDeclaration"); }
  public void exitClassDeclaration(an5Parser.ClassDeclarationContext ctx) {
    log.DBG("exitClassDeclaration");
    String newClassId = ctx.IDENTIFIER().getText();
    StringBuilder extendsKey = new StringBuilder("object");
    List<String> exposesKeys = new ArrayList<>();
    
    extractTypeTypeKey(ctx.typeType(), extendsKey);
    extractTypeListKeys(ctx.typeList(), exposesKeys);
   
	log.DBG(diags, "Class - '" + newClassId + "' extends - '" + extendsKey + "'");
	
    an5ClassValue newClass = new an5ClassValue(newClassId, symtab.current.forPackage);
    an5TypeValue res = symtab.insert(newClassId, newClass);
    if (res != null) {
      log.ERR(3, "<log.ERR>:AN5:Duplicate Name: [" + res.isA + "]" + res.value + ".");
    }
    else {
      res = symtab.select(extendsKey.toString());
  	  if (res == null) {
  	    newClass.classExtended = new an5UnresolvedClassValue("class", extendsKey.toString(), symtab.current.forPackage);
  	  }
  	  else if (res instanceof an5UnresolvedClassValue) {
  	    an5UnresolvedClassValue fix = (an5UnresolvedClassValue)res;
  	    fix.resolvedTo = res;
  	  }
  	  else if (res instanceof an5ClassValue) {
  	    newClass.classExtended = (an5ClassValue)res;
  	  }
  	  else {
  	    log.ERR(3, "<ERR>:AN5:Class Extension Type Invalid: [" + res.isA + "]" + res.value + ".");
  	  }
    }
  	for (String s: exposesKeys) {
  	  an5InterfaceValue newIf = new an5InterfaceValue(s, symtab.current.forPackage);
      res = symtab.select(s);
      if (res == null) {
    	  newIf.interfacesExtended.add(new an5UnresolvedInterfaceValue("interface", s, symtab.current.forPackage));
      }
      else if (res instanceof an5UnresolvedInterfaceValue) {
    	an5UnresolvedInterfaceValue fix = (an5UnresolvedInterfaceValue)res;
    	fix.resolvedTo = res;
      }
      else if (res instanceof an5InterfaceValue) {
    	newIf.interfacesExtended.add((an5InterfaceValue)res);
      }
      else {
    	log.ERR(3, "<ERR>:AN5:Interface Extension Type Invalid: [" + res.isA + "]" + res.value + ".");
      }
    }
//    symtab.current = symtab.current.getParent();
  }
  public void exitClassOrInterfaceModifier(an5Parser.ClassOrInterfaceModifierContext ctx) { log.DBG("exitClassOrInterfaceModifier"); }
  public void exitClassOrInterfaceType(an5Parser.ClassOrInterfaceTypeContext ctx) { log.DBG("exitClassOrInterfaceType"); }
  public void exitCompilationUnit(an5Parser.CompilationUnitContext ctx) {
	log.DBG("exitCompilationUnit");
  }
  public void exitConstantDeclarator(an5Parser.ConstantDeclaratorContext ctx) { log.DBG("exitConstantDeclarator"); }
  public void exitConstDeclaration(an5Parser.ConstDeclarationContext ctx) { log.DBG("exitConstDeclaration"); }
  public void exitDefaultValue(an5Parser.DefaultValueContext ctx) { log.DBG("exitDefaultValue"); }
  public void exitElementValue(an5Parser.ElementValueContext ctx) { log.DBG("exitElementValue"); }
  public void exitElementValueArrayInitializer(an5Parser.ElementValueArrayInitializerContext ctx) { log.DBG("exitElementValueArrayInitializer"); }
  public void exitElementValuePair(an5Parser.ElementValuePairContext ctx) { log.DBG("exitElementValuePair"); }
  public void exitElementValuePairs(an5Parser.ElementValuePairsContext ctx) { log.DBG("exitElementValuePairs"); }
  public void exitEnumBodyDeclarations(an5Parser.EnumBodyDeclarationsContext ctx) { log.DBG("exitEnumBodyDeclarations"); }
  public void exitEnumConstant(an5Parser.EnumConstantContext ctx) { log.DBG("exitEnumConstant"); }
  public void exitEnumConstants(an5Parser.EnumConstantsContext ctx) { log.DBG("exitEnumConstants"); }
  public void exitEnumDeclaration(an5Parser.EnumDeclarationContext ctx) { log.DBG("exitEnumDeclaration"); }
  public void exitExpression(an5Parser.ExpressionContext ctx) { log.DBG("exitExpression"); }
  public void exitExpressionList(an5Parser.ExpressionListContext ctx) { log.DBG("exitExpressionList"); }
  public void exitFieldDeclaration(an5Parser.FieldDeclarationContext ctx) { log.DBG("exitFieldDeclaration"); }
  public void exitFloatLiteral(an5Parser.FloatLiteralContext ctx) { log.DBG("exitFloatLiteral"); }
  public void exitFormalParameter(an5Parser.FormalParameterContext ctx) { log.DBG("exitFormalParameter"); }
  public void exitFormalParameterList(an5Parser.FormalParameterListContext ctx) { log.DBG("exitFormalParameterList"); }
  public void exitFormalParameters(an5Parser.FormalParametersContext ctx) { log.DBG("exitFormalParameters"); }
  public void exitImportDeclaration(an5Parser.ImportDeclarationContext ctx) { log.DBG("exitImportDeclaration"); }
  public void exitIntegerLiteral(an5Parser.IntegerLiteralContext ctx) { log.DBG("exitIntegerLiteral"); }
  public void exitInterfaceBody(an5Parser.InterfaceBodyContext ctx) { log.DBG("exitInterfaceBody"); }
  public void exitInterfaceBodyDeclaration(an5Parser.InterfaceBodyDeclarationContext ctx) { log.DBG("exitInterfaceBodyDeclaration"); }
  public void exitInterfaceDeclaration(an5Parser.InterfaceDeclarationContext ctx) {
    log.DBG("exitInterfaceDeclaration");
    String newIfId = ctx.IDENTIFIER().getText();
    List<String> exposesKeys = new ArrayList<>();
 
    extractTypeListKeys(ctx.typeList(), exposesKeys);
    
    an5InterfaceValue newIf = new an5InterfaceValue(newIfId, symtab.current.forPackage);
    an5TypeValue res = symtab.insert(newIfId, newIf);
    if (res != null) {
      log.ERR(3, "<log.ERR>:AN5:Duplicate Name: [" + res.isA + "]" + res.value + ".");
    }
    else {
      for (String s: exposesKeys) {
        res = symtab.select(s);
    	if (res == null) {
    	  newIf.interfacesExtended.add(new an5UnresolvedInterfaceValue("interface", s, an5Global.basePackage));
    	}
    	else if (res instanceof an5UnresolvedInterfaceValue) {
    	  an5UnresolvedInterfaceValue fix = (an5UnresolvedInterfaceValue)res;
    	  fix.resolvedTo = res;
    	}
    	else if (res instanceof an5InterfaceValue) {
    	  newIf.interfacesExtended.add((an5InterfaceValue)res);
    	}
    	else {
    	  log.ERR(3, "<ERR>:AN5:Interface Extension Type Invalid: [" + res.isA + "]" + res.value + ".");
    	}
      }
    }
//    symtab.current = symtab.current.getParent();
  }
  public void exitInterfaceMemberDeclaration(an5Parser.InterfaceMemberDeclarationContext ctx) { log.DBG("exitInterfaceMemberDeclaration"); }
  public void exitInterfaceMethodDeclaration(an5Parser.InterfaceMethodDeclarationContext ctx) { log.DBG("exitInterfaceMethodDeclaration"); }
  public void exitInterfaceMethodModifier(an5Parser.InterfaceMethodModifierContext ctx) { log.DBG("exitInterfaceMethodModifier"); }
  public void exitInterfaceSignatureDeclaration(an5Parser.InterfaceSignatureDeclarationContext ctx) { log.DBG("exitInterfaceSignatureDeclaration"); }
  public void exitLastFormalParameter(an5Parser.LastFormalParameterContext ctx) { log.DBG("exitLastFormalParameter"); }
  public void exitLiteral(an5Parser.LiteralContext ctx) { log.DBG("exitLiteral"); }
  public void exitLocalTypeDeclaration(an5Parser.LocalTypeDeclarationContext ctx) { log.DBG("exitLocalTypeDeclaration"); }
  public void exitLocalVariableDeclaration(an5Parser.LocalVariableDeclarationContext ctx) { log.DBG("exitLocalVariableDeclaration"); }
  public void exitMemberDeclaration(an5Parser.MemberDeclarationContext ctx) { log.DBG("exitMemberDeclaration"); }
  public void exitModifier(an5Parser.ModifierContext ctx) { log.DBG("exitModifier"); }
  public void exitNetworkType(an5Parser.NetworkTypeContext ctx) { log.DBG("exitNetworkType"); }
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
  public void exitParExpression(an5Parser.ParExpressionContext ctx) { log.DBG("exitParExpression"); }
  public void exitPrimary(an5Parser.PrimaryContext ctx) { log.DBG("exitPrimary"); }
  public void exitPrimitiveType(an5Parser.PrimitiveTypeContext ctx) { log.DBG("exitPrimitiveType"); }
  public void exitQualifiedName(an5Parser.QualifiedNameContext ctx) { log.DBG("exitQualifiedName"); }
  public void exitQualifiedNameList(an5Parser.QualifiedNameListContext ctx) { log.DBG("exitQualifiedNameList"); }
  public void exitSignatureType(an5Parser.SignatureTypeContext ctx) { log.DBG("exitSignatureType"); }
  public void exitStatement(an5Parser.StatementContext ctx) { log.DBG("exitStatement"); }
  public void exitTypeArgument(an5Parser.TypeArgumentContext ctx) { log.DBG("exitTypeArgument"); }
  public void exitTypeDeclaration(an5Parser.TypeDeclarationContext ctx) { log.DBG("exitTypeDeclaration"); }
  public void exitTypeList(an5Parser.TypeListContext ctx) { log.DBG("exitTypeList"); }
  public void exitTypeType(an5Parser.TypeTypeContext ctx) { log.DBG("exitTypeType"); }
  public void exitTypeTypeOrVoid(an5Parser.TypeTypeOrVoidContext ctx) { log.DBG("exitTypeTypeOrVoid"); }
  public void exitVariableDeclarator(an5Parser.VariableDeclaratorContext ctx) { log.DBG("exitVariableDeclarator"); }
  public void exitVariableDeclaratorId(an5Parser.VariableDeclaratorIdContext ctx) { log.DBG("exitVariableDeclaratorId"); }
  public void exitVariableDeclarators(an5Parser.VariableDeclaratorsContext ctx) { log.DBG("exitVariableDeclarators"); }
  public void exitVariableInitializer(an5Parser.VariableInitializerContext ctx) { log.DBG("exitVariableInitializer"); }
  public void exitVariableModifier(an5Parser.VariableModifierContext ctx) { log.DBG("exitVariableModifier"); }
}
