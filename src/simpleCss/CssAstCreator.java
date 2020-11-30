package simpleCss;

import java.io.FileReader;

import simpleCss.ast.AstCss;
import simpleCss.parser.Lexicon;
import simpleCss.parser.Parser;

/**
 * Clase para generar el AST del fichero CSS
 * 
 * @author Tania Álvarez Díaz
 *
 */
public class CssAstCreator {

	private static Lexicon lex;
	private static Parser parser;

	public static AstCss createCssAst(FileReader fileCss) {
		lex = new Lexicon(fileCss);
		if (!lex.errorLexico) {
			parser = new Parser(lex);
			return parser.parse();
		}
		return null;
	}

}
