package simpleCss.visitor;

import simpleCss.ast.Bloque;
import simpleCss.ast.Css;
import simpleCss.ast.Sentencia;

public interface Visitor {
	Object visit(Css css, Object param);

	Object visit(Bloque bloque, Object param);

	Object visit(Sentencia sentencia, Object param);
}
