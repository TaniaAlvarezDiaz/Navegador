package simpleCss.visitor;

import simpleCss.ast.AstCss;
import simpleCss.ast.Bloque;
import simpleCss.ast.Css;
import simpleCss.ast.Sentencia;

/**
 * Visitor para buscar un parametro del el AST del CSS
 * 
 * @author Tania Álvarez Díaz
 *
 */
public class BuscaParamEnCssVisitor implements Visitor {
	private String label = null;
	private String nombreBloque = null;

	@Override
	public Object visit(Css css, Object param) {
		for (Bloque b : css.bloques) {
			if (b.nombre.equals(nombreBloque))
				return (String) b.accept(this, param);
		}
		return null;
	}

	@Override
	public Object visit(Bloque bloque, Object param) {
		for (Sentencia s : bloque.sentencias) {
			if (s.clave.equals(label))
				return (String) s.accept(this, param);
		}
		return null;
	}

	@Override
	public Object visit(Sentencia sentencia, Object param) {
		return sentencia.valor;
	}

	/**
	 * Método para buscar en el árbol css el valor de la label del bloque que se
	 * pasa por parámetro
	 * 
	 * @param ast
	 * @param nombreBloque
	 * @param label
	 * @return
	 */
	public String search(AstCss ast, String nombreBloque, String label) {
		if (ast != null) {
			this.nombreBloque = nombreBloque;
			this.label = label;

			if (nombreBloque == null || label == null)
				return null;

			return (String) ast.accept(this, null);
		}
		return null;
	}

}
