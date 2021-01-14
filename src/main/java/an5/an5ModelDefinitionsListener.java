/*
 What: Build an5 definitions symbol table via "Listener" class
*/

package an5;

class an5ModelDefinitionsListener extends an5ParserBaseListener {
  int verbosity = 1;
  void DBG(String msg) {
	switch (verbosity) {
	  case 3:
	  case 2:
	  case 1: System.out.println(msg);
	}
  }
  public void enterAltAnnotationQualifiedName(an5Parser.AltAnnotationQualifiedNameContext ctx) { DBG("enterAltAnnotationQualifiedName"); }
  public void enterAnnotation(an5Parser.AnnotationContext ctx) { DBG("enterAnnotation"); }
  public void enterAnnotationConstantRest(an5Parser.AnnotationConstantRestContext ctx) { DBG("enterAnnotationConstantRest"); }
  public void enterAnnotationMethodOrConstantRest(an5Parser.AnnotationMethodOrConstantRestContext ctx) { DBG("enterAnnotationMethodOrConstantRest"); }
  public void enterAnnotationMethodRest(an5Parser.AnnotationMethodRestContext ctx) { DBG("enterAnnotationMethodRest"); }
  public void enterAnnotationTypeBody(an5Parser.AnnotationTypeBodyContext ctx) { DBG("enterAnnotationTypeBody"); }
  public void enterAnnotationTypeDeclaration(an5Parser.AnnotationTypeDeclarationContext ctx) { DBG("enterAnnotationTypeDeclaration"); }
  public void enterAnnotationTypeElementDeclaration(an5Parser.AnnotationTypeElementDeclarationContext ctx) { DBG("enterAnnotationTypeElementDeclaration"); }
  public void enterAnnotationTypeElementRest(an5Parser.AnnotationTypeElementRestContext ctx) { DBG("enterAnnotationTypeElementRest"); }
  public void enterArguments(an5Parser.ArgumentsContext ctx) { DBG("enterArguments"); }
  public void enterArrayInitializer(an5Parser.ArrayInitializerContext ctx) { DBG("enterArrayInitializer"); }
  public void enterBlock(an5Parser.BlockContext ctx) { DBG("enterBlock"); }
  public void enterBlockStatement(an5Parser.BlockStatementContext ctx) { DBG("enterBlockStatement"); }
  public void enterClassBody(an5Parser.ClassBodyContext ctx) { DBG("enterClassBody"); }
  public void enterClassBodyDeclaration(an5Parser.ClassBodyDeclarationContext ctx) { DBG("enterClassBodyDeclaration"); }
  public void enterClassDeclaration(an5Parser.ClassDeclarationContext ctx) { DBG("enterClassDeclaration"); }
  public void enterClassOrInterfaceModifier(an5Parser.ClassOrInterfaceModifierContext ctx) { DBG("enterClassOrInterfaceModifier"); }
  public void enterClassOrInterfaceType(an5Parser.ClassOrInterfaceTypeContext ctx) { DBG("enterClassOrInterfaceType"); }
  public void enterCompilationUnit(an5Parser.CompilationUnitContext ctx) { DBG("enterCompilationUnit"); }
  public void enterConstantDeclarator(an5Parser.ConstantDeclaratorContext ctx) { DBG("enterConstantDeclarator"); }
  public void enterConstDeclaration(an5Parser.ConstDeclarationContext ctx) { DBG("enterConstDeclaration"); }
  public void enterDefaultValue(an5Parser.DefaultValueContext ctx) { DBG("enterDefaultValue"); }
  public void enterElementValue(an5Parser.ElementValueContext ctx) { DBG("enterElementValue"); }
  public void enterElementValueArrayInitializer(an5Parser.ElementValueArrayInitializerContext ctx) { DBG("enterElementValueArrayInitializer"); }
  public void enterElementValuePair(an5Parser.ElementValuePairContext ctx) { DBG("enterElementValuePair"); }
  public void enterElementValuePairs(an5Parser.ElementValuePairsContext ctx) { DBG("enterElementValuePairs"); }
  public void enterEnumBodyDeclarations(an5Parser.EnumBodyDeclarationsContext ctx) { DBG("enterEnumBodyDeclarations"); }
  public void enterEnumConstant(an5Parser.EnumConstantContext ctx) { DBG("enterEnumConstant"); }
  public void enterEnumConstants(an5Parser.EnumConstantsContext ctx) { DBG("enterEnumConstants"); }
  public void enterEnumDeclaration(an5Parser.EnumDeclarationContext ctx) { DBG("enterEnumDeclaration"); }
  public void enterExpression(an5Parser.ExpressionContext ctx) { DBG("enterExpression"); }
  public void enterExpressionList(an5Parser.ExpressionListContext ctx) { DBG("enterExpressionList"); }
  public void enterFieldDeclaration(an5Parser.FieldDeclarationContext ctx) { DBG("enterFieldDeclaration"); }
  public void enterFloatLiteral(an5Parser.FloatLiteralContext ctx) { DBG("enterFloatLiteral"); }
  public void enterFormalParameter(an5Parser.FormalParameterContext ctx) { DBG("enterFormalParameter"); }
  public void enterFormalParameterList(an5Parser.FormalParameterListContext ctx) { DBG("enterFormalParameterList"); }
  public void enterFormalParameters(an5Parser.FormalParametersContext ctx) { DBG("enterFormalParameters"); }
  public void enterImportDeclaration(an5Parser.ImportDeclarationContext ctx) { DBG("enterImportDeclaration"); }
  public void enterIntegerLiteral(an5Parser.IntegerLiteralContext ctx) { DBG("enterIntegerLiteral"); }
  public void enterInterfaceBody(an5Parser.InterfaceBodyContext ctx) { DBG("enterInterfaceBody"); }
  public void enterInterfaceBodyDeclaration(an5Parser.InterfaceBodyDeclarationContext ctx) { DBG("enterInterfaceBodyDeclaration"); }
  public void enterInterfaceDeclaration(an5Parser.InterfaceDeclarationContext ctx) { DBG("enterInterfaceDeclaration"); }
  public void enterInterfaceMemberDeclaration(an5Parser.InterfaceMemberDeclarationContext ctx) { DBG("enterInterfaceMemberDeclaration"); }
  public void enterInterfaceMethodDeclaration(an5Parser.InterfaceMethodDeclarationContext ctx) { DBG("enterInterfaceMethodDeclaration"); }
  public void enterInterfaceMethodModifier(an5Parser.InterfaceMethodModifierContext ctx) { DBG("enterInterfaceMethodModifier"); }
  public void enterInterfaceSignatureDeclaration(an5Parser.InterfaceSignatureDeclarationContext ctx) { DBG("enterInterfaceSignatureDeclaration"); }
  public void enterLastFormalParameter(an5Parser.LastFormalParameterContext ctx) { DBG("enterLastFormalParameter"); }
  public void enterLiteral(an5Parser.LiteralContext ctx) { DBG("enterLiteral"); }
  public void enterLocalTypeDeclaration(an5Parser.LocalTypeDeclarationContext ctx) { DBG("enterLocalTypeDeclaration"); }
  public void enterLocalVariableDeclaration(an5Parser.LocalVariableDeclarationContext ctx) { DBG("enterLocalVariableDeclaration"); }
  public void enterMemberDeclaration(an5Parser.MemberDeclarationContext ctx) { DBG("enterMemberDeclaration"); }
  public void enterModifier(an5Parser.ModifierContext ctx) { DBG("enterModifier"); }
  public void enterNetworkType(an5Parser.NetworkTypeContext ctx) { DBG("enterNetworkType"); }
  public void enterPackageDeclaration(an5Parser.PackageDeclarationContext ctx) { DBG("enterPackageDeclaration"); }
  public void enterParExpression(an5Parser.ParExpressionContext ctx) { DBG("enterParExpression"); }
  public void enterPrimary(an5Parser.PrimaryContext ctx) { DBG("enterPrimary"); }
  public void enterPrimitiveType(an5Parser.PrimitiveTypeContext ctx) { DBG("enterPrimitiveType"); }
  public void enterQualifiedName(an5Parser.QualifiedNameContext ctx) { DBG("enterQualifiedName"); }
  public void enterQualifiedNameList(an5Parser.QualifiedNameListContext ctx) { DBG("enterQualifiedNameList"); }
  public void enterSignatureType(an5Parser.SignatureTypeContext ctx) { DBG("enterSignatureType"); }
  public void enterStatement(an5Parser.StatementContext ctx) { DBG("enterStatement"); }
  public void enterTypeArgument(an5Parser.TypeArgumentContext ctx) { DBG("enterTypeArgument"); }
  public void enterTypeDeclaration(an5Parser.TypeDeclarationContext ctx) { DBG("enterTypeDeclaration"); }
  public void enterTypeList(an5Parser.TypeListContext ctx) { DBG("enterTypeList"); }
  public void enterTypeType(an5Parser.TypeTypeContext ctx) { DBG("enterTypeType"); }
  public void enterTypeTypeOrVoid(an5Parser.TypeTypeOrVoidContext ctx) { DBG("enterTypeTypeOrVoid"); }
  public void enterVariableDeclarator(an5Parser.VariableDeclaratorContext ctx) { DBG("enterVariableDeclarator"); }
  public void enterVariableDeclaratorId(an5Parser.VariableDeclaratorIdContext ctx) { DBG("enterVariableDeclaratorId"); }
  public void enterVariableDeclarators(an5Parser.VariableDeclaratorsContext ctx) { DBG("enterVariableDeclarators"); }
  public void enterVariableInitializer(an5Parser.VariableInitializerContext ctx) { DBG("enterVariableInitializer"); }
  public void enterVariableModifier(an5Parser.VariableModifierContext ctx) { DBG("enterVariableModifier"); }
  public void exitAltAnnotationQualifiedName(an5Parser.AltAnnotationQualifiedNameContext ctx) { DBG("exitAltAnnotationQualifiedName"); }
  public void exitAnnotation(an5Parser.AnnotationContext ctx) { DBG("exitAnnotation"); }
  public void exitAnnotationConstantRest(an5Parser.AnnotationConstantRestContext ctx) { DBG("exitAnnotationConstantRest"); }
  public void exitAnnotationMethodOrConstantRest(an5Parser.AnnotationMethodOrConstantRestContext ctx) { DBG("exitAnnotationMethodOrConstantRest"); }
  public void exitAnnotationMethodRest(an5Parser.AnnotationMethodRestContext ctx) { DBG("exitAnnotationMethodRest"); }
  public void exitAnnotationTypeBody(an5Parser.AnnotationTypeBodyContext ctx) { DBG("exitAnnotationTypeBody"); }
  public void exitAnnotationTypeDeclaration(an5Parser.AnnotationTypeDeclarationContext ctx) { DBG("exitAnnotationTypeDeclaration"); }
  public void exitAnnotationTypeElementDeclaration(an5Parser.AnnotationTypeElementDeclarationContext ctx) { DBG("exitAnnotationTypeElementDeclaration"); }
  public void exitAnnotationTypeElementRest(an5Parser.AnnotationTypeElementRestContext ctx) { DBG("exitAnnotationTypeElementRest"); }
  public void exitArguments(an5Parser.ArgumentsContext ctx) { DBG("exitArguments"); }
  public void exitArrayInitializer(an5Parser.ArrayInitializerContext ctx) { DBG("exitArrayInitializer"); }
  public void exitBlock(an5Parser.BlockContext ctx) { DBG("exitBlock"); }
  public void exitBlockStatement(an5Parser.BlockStatementContext ctx) { DBG("exitBlockStatement"); }
  public void exitClassBody(an5Parser.ClassBodyContext ctx) { DBG("exitClassBody"); }
  public void exitClassBodyDeclaration(an5Parser.ClassBodyDeclarationContext ctx) { DBG("exitClassBodyDeclaration"); }
  public void exitClassDeclaration(an5Parser.ClassDeclarationContext ctx) { DBG("exitClassDeclaration"); }
  public void exitClassOrInterfaceModifier(an5Parser.ClassOrInterfaceModifierContext ctx) { DBG("exitClassOrInterfaceModifier"); }
  public void exitClassOrInterfaceType(an5Parser.ClassOrInterfaceTypeContext ctx) { DBG("exitClassOrInterfaceType"); }
  public void exitCompilationUnit(an5Parser.CompilationUnitContext ctx) { DBG("exitCompilationUnit"); }
  public void exitConstantDeclarator(an5Parser.ConstantDeclaratorContext ctx) { DBG("exitConstantDeclarator"); }
  public void exitConstDeclaration(an5Parser.ConstDeclarationContext ctx) { DBG("exitConstDeclaration"); }
  public void exitDefaultValue(an5Parser.DefaultValueContext ctx) { DBG("exitDefaultValue"); }
  public void exitElementValue(an5Parser.ElementValueContext ctx) { DBG("exitElementValue"); }
  public void exitElementValueArrayInitializer(an5Parser.ElementValueArrayInitializerContext ctx) { DBG("exitElementValueArrayInitializer"); }
  public void exitElementValuePair(an5Parser.ElementValuePairContext ctx) { DBG("exitElementValuePair"); }
  public void exitElementValuePairs(an5Parser.ElementValuePairsContext ctx) { DBG("exitElementValuePairs"); }
  public void exitEnumBodyDeclarations(an5Parser.EnumBodyDeclarationsContext ctx) { DBG("exitEnumBodyDeclarations"); }
  public void exitEnumConstant(an5Parser.EnumConstantContext ctx) { DBG("exitEnumConstant"); }
  public void exitEnumConstants(an5Parser.EnumConstantsContext ctx) { DBG("exitEnumConstants"); }
  public void exitEnumDeclaration(an5Parser.EnumDeclarationContext ctx) { DBG("exitEnumDeclaration"); }
  public void exitExpression(an5Parser.ExpressionContext ctx) { DBG("exitExpression"); }
  public void exitExpressionList(an5Parser.ExpressionListContext ctx) { DBG("exitExpressionList"); }
  public void exitFieldDeclaration(an5Parser.FieldDeclarationContext ctx) { DBG("exitFieldDeclaration"); }
  public void exitFloatLiteral(an5Parser.FloatLiteralContext ctx) { DBG("exitFloatLiteral"); }
  public void exitFormalParameter(an5Parser.FormalParameterContext ctx) { DBG("exitFormalParameter"); }
  public void exitFormalParameterList(an5Parser.FormalParameterListContext ctx) { DBG("exitFormalParameterList"); }
  public void exitFormalParameters(an5Parser.FormalParametersContext ctx) { DBG("exitFormalParameters"); }
  public void exitImportDeclaration(an5Parser.ImportDeclarationContext ctx) { DBG("exitImportDeclaration"); }
  public void exitIntegerLiteral(an5Parser.IntegerLiteralContext ctx) { DBG("exitIntegerLiteral"); }
  public void exitInterfaceBody(an5Parser.InterfaceBodyContext ctx) { DBG("exitInterfaceBody"); }
  public void exitInterfaceBodyDeclaration(an5Parser.InterfaceBodyDeclarationContext ctx) { DBG("exitInterfaceBodyDeclaration"); }
  public void exitInterfaceDeclaration(an5Parser.InterfaceDeclarationContext ctx) { DBG("exitInterfaceDeclaration"); }
  public void exitInterfaceMemberDeclaration(an5Parser.InterfaceMemberDeclarationContext ctx) { DBG("exitInterfaceMemberDeclaration"); }
  public void exitInterfaceMethodDeclaration(an5Parser.InterfaceMethodDeclarationContext ctx) { DBG("exitInterfaceMethodDeclaration"); }
  public void exitInterfaceMethodModifier(an5Parser.InterfaceMethodModifierContext ctx) { DBG("exitInterfaceMethodModifier"); }
  public void exitInterfaceSignatureDeclaration(an5Parser.InterfaceSignatureDeclarationContext ctx) { DBG("exitInterfaceSignatureDeclaration"); }
  public void exitLastFormalParameter(an5Parser.LastFormalParameterContext ctx) { DBG("exitLastFormalParameter"); }
  public void exitLiteral(an5Parser.LiteralContext ctx) { DBG("exitLiteral"); }
  public void exitLocalTypeDeclaration(an5Parser.LocalTypeDeclarationContext ctx) { DBG("exitLocalTypeDeclaration"); }
  public void exitLocalVariableDeclaration(an5Parser.LocalVariableDeclarationContext ctx) { DBG("exitLocalVariableDeclaration"); }
  public void exitMemberDeclaration(an5Parser.MemberDeclarationContext ctx) { DBG("exitMemberDeclaration"); }
  public void exitModifier(an5Parser.ModifierContext ctx) { DBG("exitModifier"); }
  public void exitNetworkType(an5Parser.NetworkTypeContext ctx) { DBG("exitNetworkType"); }
  public void exitPackageDeclaration(an5Parser.PackageDeclarationContext ctx) { DBG("exitPackageDeclaration"); }
  public void exitParExpression(an5Parser.ParExpressionContext ctx) { DBG("exitParExpression"); }
  public void exitPrimary(an5Parser.PrimaryContext ctx) { DBG("exitPrimary"); }
  public void exitPrimitiveType(an5Parser.PrimitiveTypeContext ctx) { DBG("exitPrimitiveType"); }
  public void exitQualifiedName(an5Parser.QualifiedNameContext ctx) { DBG("exitQualifiedName"); }
  public void exitQualifiedNameList(an5Parser.QualifiedNameListContext ctx) { DBG("exitQualifiedNameList"); }
  public void exitSignatureType(an5Parser.SignatureTypeContext ctx) { DBG("exitSignatureType"); }
  public void exitStatement(an5Parser.StatementContext ctx) { DBG("exitStatement"); }
  public void exitTypeArgument(an5Parser.TypeArgumentContext ctx) { DBG("exitTypeArgument"); }
  public void exitTypeDeclaration(an5Parser.TypeDeclarationContext ctx) { DBG("exitTypeDeclaration"); }
  public void exitTypeList(an5Parser.TypeListContext ctx) { DBG("exitTypeList"); }
  public void exitTypeType(an5Parser.TypeTypeContext ctx) { DBG("exitTypeType"); }
  public void exitTypeTypeOrVoid(an5Parser.TypeTypeOrVoidContext ctx) { DBG("exitTypeTypeOrVoid"); }
  public void exitVariableDeclarator(an5Parser.VariableDeclaratorContext ctx) { DBG("exitVariableDeclarator"); }
  public void exitVariableDeclaratorId(an5Parser.VariableDeclaratorIdContext ctx) { DBG("exitVariableDeclaratorId"); }
  public void exitVariableDeclarators(an5Parser.VariableDeclaratorsContext ctx) { DBG("exitVariableDeclarators"); }
  public void exitVariableInitializer(an5Parser.VariableInitializerContext ctx) { DBG("exitVariableInitializer"); }
  public void exitVariableModifier(an5Parser.VariableModifierContext ctx) { DBG("exitVariableModifier"); }
}
