package main;

import java.io.FileNotFoundException;
import java.io.FileReader;

import paint.PrintPageTxt;
import render.FormattedPage;
import simpleCss.CssAstCreator;
import simpleCss.ast.AstCss;
import simpleHtml.HtmlAstCreator;
import simpleHtml.ast.AstHtml;
import simpleHtml.visitor.BuscaNombreCssEnHtmlVisitor;
import simpleHtml.visitor.RenderVisitor;

public class Main {

	private final static String BASE_RESOURCES_HTML = "resources/html/";
	private final static String BASE_RESOURCES_CSS = "resources/css/";

	public static void main(String[] args) {

		AstHtml htmlAst = null;
		AstCss cssAst = null;
		AstCss cssDefaultAst = null;

		String htmlName = "EX8.html";

		try {
			// Crear el AST del HTML
			FileReader fHtml = new FileReader(BASE_RESOURCES_HTML + htmlName);
			htmlAst = HtmlAstCreator.createHtmlAst(fHtml);

			// Si se creó correctamente, se crean los AST de los ficheros CSS
			if (htmlAst != null) {
				System.out.println("Árbol AST del HTML creado");

				// Crear AST del CSS por defecto
				cssDefaultAst = crearCssAst("Default.css");
				if (cssDefaultAst != null)
					System.out.println("Árbol AST del CSS por defecto creado");

				// Buscar fichero CSS
				BuscaNombreCssEnHtmlVisitor buscaCss = new BuscaNombreCssEnHtmlVisitor();
				String cssName = buscaCss.searchCss(htmlAst);

				// Generar AST del CSS indicado en el HTML
				cssAst = crearCssAst(cssName);
				if (cssAst != null)
					System.out.println("Árbol AST del CSS del HTML creado");

				// Renderizar la página
				if (htmlAst != null && cssDefaultAst != null) {
					RenderVisitor render = new RenderVisitor(cssDefaultAst, cssAst);
					FormattedPage fp = (FormattedPage) htmlAst.accept(render, null);
					if (fp != null) {
						// Imprimir la página
						PrintPageTxt pp = new PrintPageTxt();
						pp.printPage(fp);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Fichero " + htmlName + " no encontrado");
		}

	}

	/**
	 * Método para obtener el AST del fichero CSS que se pasa por parámetro
	 * 
	 * @param cssName
	 * @return
	 */
	private static AstCss crearCssAst(String cssName) {
		FileReader fCss;
		try {
			fCss = new FileReader(BASE_RESOURCES_CSS + cssName);
			return CssAstCreator.createCssAst(fCss);
		} catch (FileNotFoundException e) {
			System.out.println("Fichero " + cssName + " no encontrado");
		}
		return null;
	}

}
