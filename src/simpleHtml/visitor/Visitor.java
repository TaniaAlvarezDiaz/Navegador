package simpleHtml.visitor;

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

public interface Visitor {
	Object visit(Html html, Object param);

	Object visit(Cabecera cabecera, Object param);

	Object visit(Titulo titulo, Object param);

	Object visit(Link link, Object param);

	Object visit(Cuerpo cuerpo, Object param);

	Object visit(H1 h1, Object param);

	Object visit(H2 h2, Object param);

	Object visit(Parrafo parrafo, Object param);

	Object visit(Texto texto, Object param);

	Object visit(Palabra palabra, Object param);

	Object visit(Negrita negrita, Object param);

	Object visit(Cursiva cursiva, Object param);

	Object visit(Underline underline, Object param);

	Object visit(Enlace enlace, Object param);

}
