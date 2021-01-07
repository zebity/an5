/*
  What: Create a new model by running parser on file

  By: John Hartley
*/

import org.antlr.runtime.*;

import java.io.IOException;
import java.io.InputStream;

public class an5Model {
  String name;
  String source;


  public an5Model(String nm, String src) {
    name = nm;
    source = src;
  }

  public void info() {
    System.out.println("Loaded: " + name + "From: " + source + ".");
  }


  public static an5Model Create(InputSteam in) throws IOException {
    an5Lexer lexer = new an5Lexer(new ANTLRInputStream(in));
    an5Parser parser = new an5Parser(new CommonTokenStream(lexer));
    parser.addErrorListener(new BaseErrorListener() {
      @Override
      public void syntaxError(Recognizer<?, ?> recognizer,
                    Object offendingSymbol, int line, int charPositionInLine,
                    String msg, RecognitionException e) {
        throw new IllegalStateException("failed to parse at line " + line +
                    " due to " + msg, e);
      }
    });
    parser.compilationUnit();
    return new an5Model("John", "Test");
  };
}
