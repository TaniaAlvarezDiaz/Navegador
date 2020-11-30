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
 * Clase para probar los analizadores, tanto l�xico como sint�ctico, y los
 * visitors de un fichero CSS
 * 
 * @author Tania �lvarez D�az
 *
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		FileReader filereader = new FileReader("resources/css/EX1.css");

		// Lexico
		System.out.println("/**** INICIO L�XICO ****/");
		Lexicon lex = new Lexicon(filereader);
		listaTokens(lex);
		System.out.println("/**** FIN L�XICO ****/\n");

		if (!lex.errorLexico) {
			// Sint�ctico
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
	 * M�todo para mostrar la lista de tokens le�dos por el analizador l�xico
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
	 * M�todo para ejecutar el Print Visitor
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
	 * M�todo para ejecutar el BuscaParamEnCss Visitor
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
			System.out.println("/**** NO SE HA REALIZADO LA B�SQUEDA ****/\n");
		}
	}

}
