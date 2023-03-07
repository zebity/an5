/**
 @what Build an5 definitions symbol table via "Listener" class
 
 @author John Hartley - Graphica Software/Dokmai Pty Ltd
*/

package an5;

import an5.an5Parser.ServiceSignatureDeclarationContext;

class an5ModelDefinitionsListener extends an5ParserBaseListener {
  an5Logging log = new an5Logging();
  an5Global global;
  an5SymbolTable symtab;
  an5ModelDefinitionsActions delegate = null;
  String dirPath = null;
  int diags = 5;
  
  an5ModelDefinitionsListener(an5Global glob, String dir) {
	dirPath = new String(dir);
    symtab = new an5SymbolTable(glob);
    global = glob;
    delegate = new an5ModelDefinitionsActions(glob, symtab, dir);
  }
  
  //
  // Delegate...
  //
  public void enterBlock(an5Parser.BlockContext ctx) { delegate.enterBlock(ctx); }
  public void enterCompilationUnit(an5Parser.CompilationUnitContext ctx) { delegate.enterCompilationUnit(ctx); }
  public void exitBlock(an5Parser.BlockContext ctx) { delegate.exitBlock(ctx); }
  public void exitClassDeclaration(an5Parser.ClassDeclarationContext ctx) { delegate.exitClassDeclaration(ctx); }
  public void exitCompilationUnit(an5Parser.CompilationUnitContext ctx) { delegate.exitCompilationUnit(ctx); }
  public void exitFieldDeclaration(an5Parser.FieldDeclarationContext ctx) { delegate.exitFieldDeclaration(ctx); }
  public void exitInterfaceBindingNameTemplate(an5Parser.InterfaceBindingNameTemplateContext ctx) { delegate.exitInterfaceBindingNameTemplate(ctx); }
  public void exitInterfaceDeclaration(an5Parser.InterfaceDeclarationContext ctx) { delegate.exitInterfaceDeclaration(ctx); }
  public void exitInterfaceAttributeDeclaration(an5Parser.InterfaceAttributeDeclarationContext ctx) { delegate.exitInterfaceAttributeDeclaration(ctx); }
  public void exitInterfaceSignatureDeclaration(an5Parser.InterfaceSignatureDeclarationContext ctx) { delegate.exitInterfaceSignatureDeclaration(ctx); }
  public void exitInterfaceVariableDeclaration(an5Parser.InterfaceVariableDeclarationContext ctx) { delegate.exitInterfaceVariableDeclaration(ctx); }
  public void exitPackageDeclaration(an5Parser.PackageDeclarationContext ctx) { delegate.exitPackageDeclaration(ctx); }
  public void exitServiceSignatureDeclaration(an5Parser.ServiceSignatureDeclarationContext ctx) { delegate.exitServiceSignatureDeclaration(ctx); }
  //
  // Log..
  //
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
  public void enterBlockStatement(an5Parser.BlockStatementContext ctx) { log.DBG("enterBlockStatement"); }
  public void enterClassBody(an5Parser.ClassBodyContext ctx) { log.DBG("enterClassBody"); }
  public void enterClassBodyDeclaration(an5Parser.ClassBodyDeclarationContext ctx) { log.DBG("enterClassBodyDeclaration"); }
  public void enterClassDeclaration(an5Parser.ClassDeclarationContext ctx) { log.DBG("enterClassDeclaration"); }
  public void enterClassOrInterfaceModifier(an5Parser.ClassOrInterfaceModifierContext ctx) { log.DBG("enterClassOrInterfaceModifier"); }
  public void enterClassOrInterfaceType(an5Parser.ClassOrInterfaceTypeContext ctx) { log.DBG("enterClassOrInterfaceType"); }
  public void enterConstantDeclarator(an5Parser.ConstantDeclaratorContext ctx) { log.DBG("enterConstantDeclarator"); }
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
  public void enterInterfaceBindingNameTemplate(an5Parser.InterfaceBindingNameTemplateContext ctx) { log.DBG("enterInterfaceBindingNameTemplate"); }
  public void enterInterfaceBody(an5Parser.InterfaceBodyContext ctx) { log.DBG("enterInterfaceBody"); }
  public void enterInterfaceBodyDeclaration(an5Parser.InterfaceBodyDeclarationContext ctx) { log.DBG("enterInterfaceBodyDeclaration"); }
  public void enterInterfaceDeclaration(an5Parser.InterfaceDeclarationContext ctx) { log.DBG("enterInterfaceDeclaration"); }
  public void enterInterfaceMemberDeclaration(an5Parser.InterfaceMemberDeclarationContext ctx) { log.DBG("enterInterfaceMemberDeclaration"); }
  public void enterInterfaceAttributeDeclaration(an5Parser.InterfaceAttributeDeclarationContext ctx) { log.DBG("enterInterfaceAttributeDeclaration"); }
  public void enterInterfaceSignatureDeclaration(an5Parser.InterfaceSignatureDeclarationContext ctx) { log.DBG("enterInterfaceSignatureDeclaration"); }
  public void enterInterfaceVariableDeclaration(an5Parser.InterfaceVariableDeclarationContext ctx) { log.DBG("enterLocalInterfaceVariableDeclaration"); }
  public void enterLastFormalParameter(an5Parser.LastFormalParameterContext ctx) { log.DBG("enterLastFormalParameter"); }
  public void enterLiteral(an5Parser.LiteralContext ctx) { log.DBG("enterLiteral"); }
  public void enterLocalTypeDeclaration(an5Parser.LocalTypeDeclarationContext ctx) { log.DBG("enterLocalTypeDeclaration"); }
  public void enterLocalVariableDeclaration(an5Parser.LocalVariableDeclarationContext ctx) { log.DBG("enterLocalVariableDeclaration"); }
  public void enterMemberDeclaration(an5Parser.MemberDeclarationContext ctx) { log.DBG("enterMemberDeclaration"); }
  public void enterModifier(an5Parser.ModifierContext ctx) { log.DBG("enterModifier"); }
  public void enterNetworkType(an5Parser.NetworkTypeContext ctx) { log.DBG("enterNetworkType"); }
  public void enterPackageDeclaration(an5Parser.PackageDeclarationContext ctx) { log.DBG("enterPackageDeclaration"); }
  public void enterParExpression(an5Parser.ParExpressionContext ctx) { log.DBG("enterParExpression"); }
  public void enterPrimary(an5Parser.PrimaryContext ctx) { log.DBG("enterPrimary"); }
  public void enterPrimitiveType(an5Parser.PrimitiveTypeContext ctx) { log.DBG("enterPrimitiveType"); }
  public void enterQualifiedName(an5Parser.QualifiedNameContext ctx) { log.DBG("enterQualifiedName"); }
  public void enterQualifiedNameList(an5Parser.QualifiedNameListContext ctx) { log.DBG("enterQualifiedNameList"); }
  public void enterSericeSignatureDeclaration(ServiceSignatureDeclarationContext ctx) { log.DBG("enterServiceSignatureDeclaration"); }
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
  public void exitBlockStatement(an5Parser.BlockStatementContext ctx) { log.DBG("exitBlockStatement"); }
  public void exitClassBody(an5Parser.ClassBodyContext ctx) { log.DBG("exitClassBody"); }
  public void exitClassBodyDeclaration(an5Parser.ClassBodyDeclarationContext ctx) { log.DBG("exitClassBodyDeclaration"); }
  public void exitClassOrInterfaceModifier(an5Parser.ClassOrInterfaceModifierContext ctx) { log.DBG("exitClassOrInterfaceModifier"); }
  public void exitClassOrInterfaceType(an5Parser.ClassOrInterfaceTypeContext ctx) { log.DBG("exitClassOrInterfaceType"); }
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
  public void exitFloatLiteral(an5Parser.FloatLiteralContext ctx) { log.DBG("exitFloatLiteral"); }
  public void exitFormalParameter(an5Parser.FormalParameterContext ctx) { log.DBG("exitFormalParameter"); }
  public void exitFormalParameterList(an5Parser.FormalParameterListContext ctx) { log.DBG("exitFormalParameterList"); }
  public void exitFormalParameters(an5Parser.FormalParametersContext ctx) { log.DBG("exitFormalParameters"); }
  public void exitImportDeclaration(an5Parser.ImportDeclarationContext ctx) { log.DBG("exitImportDeclaration"); }
  public void exitIntegerLiteral(an5Parser.IntegerLiteralContext ctx) { log.DBG("exitIntegerLiteral"); }
  public void exitInterfaceBody(an5Parser.InterfaceBodyContext ctx) { log.DBG("exitInterfaceBody"); }
  public void exitInterfaceBodyDeclaration(an5Parser.InterfaceBodyDeclarationContext ctx) { log.DBG("exitInterfaceBodyDeclaration"); }
  public void exitInterfaceMemberDeclaration(an5Parser.InterfaceMemberDeclarationContext ctx) { log.DBG("exitInterfaceMemberDeclaration"); }
  public void exitLastFormalParameter(an5Parser.LastFormalParameterContext ctx) { log.DBG("exitLastFormalParameter"); }
  public void exitLiteral(an5Parser.LiteralContext ctx) { log.DBG("exitLiteral"); }
  public void exitLocalTypeDeclaration(an5Parser.LocalTypeDeclarationContext ctx) { log.DBG("exitLocalTypeDeclaration"); }
  public void exitLocalVariableDeclaration(an5Parser.LocalVariableDeclarationContext ctx) { log.DBG("exitLocalVariableDeclaration"); }
  public void exitMemberDeclaration(an5Parser.MemberDeclarationContext ctx) { log.DBG("exitMemberDeclaration"); }
  public void exitModifier(an5Parser.ModifierContext ctx) { log.DBG("exitModifier"); }
  public void exitNetworkType(an5Parser.NetworkTypeContext ctx) { log.DBG("exitNetworkType"); }
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
