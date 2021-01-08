/*
  What: Create a new model by running parser on file

  By: John Hartley
*/

import org.antlr.runtime.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.TokenStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class an5Model {
  String name;
  String source;
  Object ast;


  public an5Model(String nm, String src, Object tree) {
    name = nm;
    source = src;
    ast = tree;
  }

  public void info() {
    System.out.println("Loaded: " + name + "From: " + source + ".");
  }


  public static an5Model create(String nm, String src) throws IOException {
	File an5File = new File(src);
	InputStream stream = new FileInputStream(an5File);
    an5Lexer lexer = new an5Lexer((CharStream) new ANTLRInputStream(stream));
    an5Parser parser = new an5Parser((TokenStream) new CommonTokenStream((TokenSource) lexer));
//    parser.addErrorListener(new BaseErrorListener() {
//      @Override
//      public void syntaxError(Recognizer<?, ?> recognizer,
//                    Object offendingSymbol, int line, int charPositionInLine,
//                    String msg, RecognitionException e) {
//        throw new IllegalStateException("failed to parse at line " + line +
//                    " due to " + msg, e);
//      }
//    }
    parser.compilationUnit();
    return new an5Model(nm, src, parser);
  }
}
