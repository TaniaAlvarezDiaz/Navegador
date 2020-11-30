package simpleHtml.visitor;

import simpleHtml.ast.AstHtml;
import simpleHtml.ast.Cabecera;
import simpleHtml.ast.Cuerpo;
import simpleHtml.ast.Cursiva;
import simpleHtml.ast.Enlace;
import simpleHtml.ast.H1;
import simpleHtml.ast.H2;
import simpleHtml.ast.Html;
import simpleHtml.ast.Link;
import simpleHtml.ast.Negrita;
import simpleHtml.ast.Palabra;
import simpleHtml.ast.Parrafo;
import simpleHtml.ast.Texto;
import simpleHtml.ast.Titulo;
import simpleHtml.ast.Underline;

/**
 * Visitor para buscar el archivo CSS en el AST Html
 * 
 * @author Tania Álvarez Díaz
 *
 */
public class BuscaNombreCssEnHtmlVisitor implements Visitor {

	/**
	 * Método para buscar en el árbol html el nombre del archivo CSS que se debe
	 * cargar
	 * 
	 * @param ast
	 * @return
	 */
	public String searchCss(AstHtml ast) {
		return (String) ast.accept(this, null);
	}

	@Override
	public Object visit(Html html, Object param) {
		// Se busca en la cabecera
		return (String) html.cabecera.accept(this, param);
	}

	@Override
	public Object visit(Cabecera cabecera, Object param) {
		// Se busca en los links
		String l = "";
		for (Link link : cabecera.links) {
			l = (String) link.accept(this, param);
			// Si es !=null entonces se ha encontrado el nombre del CSS
			if (l != null)
				break;
		}
		return l;
	}

	@Override
	public Object visit(Titulo titulo, Object param) {
		// Not neccesary
		return null;
	}

	@Override
	public Object visit(Link link, Object param) {
		// Se comprueba que el tipo de link sea CSS
		if (link.type.contains("css"))
			return link.href;
		return null;
	}

	@Override
	public Object visit(Cuerpo cuerpo, Object param) {
		// Not neccesary
		return null;
	}

	@Override
	public Object visit(H1 h1, Object param) {
		// Not neccesary
		return null;
	}

	@Override
	public Object visit(H2 h2, Object param) {
		// Not neccesary
		return null;
	}

	@Override
	public Object visit(Parrafo parrafo, Object param) {
		// Not neccesary
		return null;
	}

	@Override
	public Object visit(Texto texto, Object param) {
		// Not neccesary
		return null;
	}

	@Override
	public Object visit(Palabra palabra, Object param) {
		// Not neccesary
		return null;
	}

	@Override
	public Object visit(Negrita negrita, Object param) {
		// Not neccesary
		return null;
	}

	@Override
	public Object visit(Cursiva cursiva, Object param) {
		// Not neccesary
		return null;
	}

	@Override
	public Object visit(Underline underline, Object param) {
		// Not neccesary
		return null;
	}

	@Override
	public Object visit(Enlace enlace, Object param) {
		// Not neccesary
		return null;
	}

}
