package simpleHtml;

import java.io.FileReader;

import simpleHtml.ast.AstHtml;
import simpleHtml.parser.Lexicon;
import simpleHtml.parser.Parser;

/**
 * Clase para generar el AST del fichero HTML
 * 
 * @author Tania Álvarez Díaz
 *
 */
public class HtmlAstCreator {
	private static Lexicon lex;
	private static Parser parser;

	public static AstHtml createHtmlAst(FileReader fileHtml) {
		lex = new Lexicon(fileHtml);
		if (!lex.errorLexico) {
			parser = new Parser(lex);
			return parser.parse();
		}
		return null;
	}
}
