/*
  What: Create a new model by running parser on file

  By: John Hartley
*/

package an5;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class an5Model {
  String name,
         source,
         dirPath;
  an5Global glob;
  an5Parser parser;
  Object tree = null;
  

  public an5Model(an5Global g, String dir, String src, an5Parser pars) {
    source = src;
    dirPath = dir;
    glob = g;
    parser = pars;
  }

  public static an5Model create(an5Global glob, String src, String dirPath) throws IOException {
	File an5File = new File(src);
	InputStream stream = new FileInputStream(an5File);
    an5Lexer lexer = new an5Lexer(CharStreams.fromStream(stream));
    an5Parser parser = new an5Parser(new CommonTokenStream(lexer));
    an5ModelDefinitionsListener listener = new an5ModelDefinitionsListener(glob, dirPath);
    
//    an5SymbolTable symTab = new an5SymbolTable();
//    parser.addErrorListener(new BaseErrorListener() {
//      @Override
//      public void syntaxError(Recognizer<?, ?> recognizer,
//                    Object offendingSymbol, int line, int charPositionInLine,
//                    String msg, RecognitionException e) {
//        throw new IllegalStateException("failed to parse at line " + line +
//                    " due to " + msg, e);
//      }
//    }
    
    parser.addParseListener(listener);
    return new an5Model(glob, dirPath, src, parser);
  }
  public int compile() {
	int res = 0;
    parser.compilationUnit();
    return res;
  }  
  public void info() {
	System.out.println("Compiled From: " + source + ".");
  }
}
