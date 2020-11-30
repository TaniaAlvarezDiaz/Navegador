package simpleCss.visitor;

import simpleCss.ast.Bloque;
import simpleCss.ast.Css;
import simpleCss.ast.Sentencia;

/**
 * Visitor para pintar por consola el AST del CSS
 * 
 * @author Tania Álvarez Díaz
 *
 */
public class PrintCssAstVisitor implements Visitor {
	private String stringCss = "   ";

	@Override
	public Object visit(Css css, Object param) {
		String bloque = "";
		for (Bloque b : css.bloques) {
			bloque += (String) b.accept(this, stringCss);
		}
		return "(CSS bloques\n" + bloque + ")";
	}

	@Override
	public Object visit(Bloque bloque, Object param) {
		String res = "";
		for (Sentencia s : bloque.sentencias) {
			res += (String) s.accept(this, (String) param + stringCss);
		}
		return (String) param + "(" + bloque.nombre + "\n" + res + (String) param + ")\n";
	}

	@Override
	public Object visit(Sentencia sentencia, Object param) {
		return (String) param + sentencia.clave + " --> " + sentencia.valor + "\n";
	}

}
