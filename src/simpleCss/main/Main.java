package simpleCss.main;

import java.io.FileNotFoundException;
import java.io.FileReader;

import simpleCss.ast.AstCss;
import simpleCss.parser.Lexicon;
import simpleCss.parser.Parser;
import simpleCss.parser.Token;
import simpleCss.parser.TokensId;
import simpleCss.visitor.BuscaParamEnCssVisitor;
import simpleCss.visitor.PrintCssAstVisitor;

/**
 * Clase para probar los analizadores, tanto léxico como sintáctico, y los
 * visitors de un fichero CSS
 * 
 * @author Tania Álvarez Díaz
 *
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileReader filereader = new FileReader("resources/css/EX1.css");

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
			AstCss ast = parser.parse();
			System.out.println("/**** FIN PARSER ****/\n");

			// Visitor PrintCssAstVisitor
			excecutePrintCssAstVisitor(ast);

			// Visitor BuscaParamEnCss
			executeBuscaParamEnCssVisitor(ast, "h2", "font-size");
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
	private static void excecutePrintCssAstVisitor(AstCss ast) {
		System.out.println("/**** INICIO PRINT CSS AST VISITOR ****/");
		if (ast != null) {
			PrintCssAstVisitor pCss = new PrintCssAstVisitor();
			String arbol = (String) ast.accept(pCss, null);
			System.out.println(arbol);
			System.out.println("/**** FIN PRINT CSS AST VISITOR ****/\n");
		} else {
			System.out.println("/**** NO SE HA GENERADO EL AST ****/\n");
		}
	}

	/**
	 * Método para ejecutar el BuscaParamEnCss Visitor
	 * 
	 * @param ast
	 * @param nombreBloque
	 * @param label
	 */
	private static void executeBuscaParamEnCssVisitor(AstCss ast, String nombreBloque, String label) {
		System.out.println("/**** INICIO BUSCA PARAM EN CSS VISITOR ****/");
		if (ast != null) {
			BuscaParamEnCssVisitor bCss = new BuscaParamEnCssVisitor();
			String res = bCss.search(ast, nombreBloque, label);
			System.out
					.println("El valor para la etiqueta '" + label + "' del bloque '" + nombreBloque + "' es: " + res);
			System.out.println("/**** FIN BUSCA PARAM EN CSS VISITOR ****/\n");
		} else {
			System.out.println("/**** NO SE HA REALIZADO LA BÚSQUEDA ****/\n");
		}
	}

}
