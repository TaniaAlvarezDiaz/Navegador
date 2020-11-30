package simpleHtml.main;

import java.io.FileNotFoundException;
import java.io.FileReader;

import simpleHtml.ast.AstHtml;
import simpleHtml.parser.Lexicon;
import simpleHtml.parser.Parser;
import simpleHtml.parser.Token;
import simpleHtml.parser.TokensId;
import simpleHtml.visitor.BuscaNombreCssEnHtmlVisitor;
import simpleHtml.visitor.PrintHtmlAstVisitor;

/**
 * Clase para probar los analizadores, tanto léxico como sintáctico, y los
 * visitors de un fichero HTML
 * 
 * @author Tania Álvarez Díaz
 *
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileReader filereader = new FileReader("resources/html/EX8.html");

		// Lexico
		System.out.println("/**** INICIO LÉXICO ****/");
		Lexicon lex = new Lexicon(filereader);
		listaTokens(lex);
		System.out.println("/**** FIN LÉXICO ****/\n");

		if (!lex.errorLexico) {
			// Sintáctico
			System.out.println("/**** INICIO PARSER ****/");
			lex.reset();
			Parser parser = new Parser(lex);
			AstHtml ast = parser.parse();
			System.out.println("/**** FIN PARSER ****/\n");

			// Visitor PrintCssAstVisitor
			excecutePrintHtmlVisitor(ast);

			// Visitor BuscaNombreCssEnHtml
			executeBuscaNombreCssEnHtmlVisitor(ast);
		}

	}

	/**
	 * Método para mostrar la lista de tokens leídos por el analizador léxico
	 * 
	 * @param lex
	 */
	private static void listaTokens(Lexicon lex) {
		Token t = lex.getToken();
		while (t.getToken() != TokensId.EOF) {
			System.out.println(t.toString());
			t = lex.getToken();
		}
		System.out.println("\nFin de fichero. \n" + t.toString());
	}

	/**
	 * Método para ejecutar el Print Visitor
	 * 
	 * @param ast
	 */
	private static void excecutePrintHtmlVisitor(AstHtml ast) {
		System.out.println("/**** INICIO PRINT HTML AST VISITOR ****/");
		if (ast != null) {
			PrintHtmlAstVisitor pCss = new PrintHtmlAstVisitor();
			String arbol = (String) ast.accept(pCss, null);
			System.out.println(arbol);
			System.out.println("/**** FIN PRINT HTML AST VISITOR ****/\n");
		} else {
			System.out.println("/**** NO SE HA GENERADO EL AST ****/\n");
		}
	}

	/**
	 * Método para ejecutar el BuscaNombreCssEnHtml Visitor
	 * 
	 * @param ast
	 */
	private static String executeBuscaNombreCssEnHtmlVisitor(AstHtml ast) {
		String res = "";
		System.out.println("/**** INICIO BUSCA NOMBRE CSS EN HTML VISITOR ****/");
		if (ast != null) {
			BuscaNombreCssEnHtmlVisitor bHtml = new BuscaNombreCssEnHtmlVisitor();
			res = bHtml.searchCss(ast);
			System.out.println("El nombre del CSS a cargar es: " + res);
			System.out.println("/**** FIN BUSCA NOMBRE CSS EN HTML VISITOR ****/\n");
		} else {
			System.out.println("/**** NO SE HA REALIZADO LA BÚSQUEDA ****/\n");
		}

		return res;
	}

}
