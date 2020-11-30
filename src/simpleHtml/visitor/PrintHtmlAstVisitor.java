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
import simpleHtml.ast.SentenciaCuerpo;
import simpleHtml.ast.SentenciaInterna;
import simpleHtml.ast.Texto;
import simpleHtml.ast.Titulo;
import simpleHtml.ast.Underline;

/**
 * Visitor para pintar por consola el AST del HTML
 * 
 * @author Tania Álvarez Díaz
 *
 */
public class PrintHtmlAstVisitor implements Visitor {
	private String stringHtml = "   ";

	@Override
	public Object visit(Html html, Object param) {
		String cabecera, cuerpo;
		cabecera = (String) html.cabecera.accept(this, stringHtml);
		cuerpo = (String) html.cuerpo.accept(this, stringHtml);
		return "(HTML\n" + cabecera + "\n" + cuerpo + "\n)";
	}

	@Override
	public Object visit(Cabecera cabecera, Object param) {
		String titulo = (String) cabecera.titulo.accept(this, (String) param + stringHtml);
		String links = "";
		for (Link link : cabecera.links) {
			links += (String) link.accept(this, (String) param + stringHtml) + "\n";
		}
		return stringHtml + "(Head\n" + titulo + "\n" + links + param + ")";
	}

	@Override
	public Object visit(Titulo titulo, Object param) {
		String res = (String) titulo.texto.accept(this, "");
		return (String) param + "(title -> " + res + ")";
	}

	@Override
	public Object visit(Link link, Object param) {
		return (String) param + "(link href = \"" + link.href + "\" rel = \"" + link.rel + "\" type = \"" + link.type
				+ "\")";
	}

	@Override
	public Object visit(Cuerpo cuerpo, Object param) {
		String res = (String) param + "(Body\n";
		for (SentenciaCuerpo sentenciaCuerpo : cuerpo.sentenciasCuerpo)
			res += (String) sentenciaCuerpo.accept(this, (String) param + stringHtml) + "\n";
		res += (String) param + ")";
		return res;
	}

	@Override
	public Object visit(H1 h1, Object param) {
		String res = (String) param + "(H1 ->";
		for (SentenciaInterna s : h1.sentenciasInternas)
			res += " " + (String) s.accept(this, "");
		res += ")";
		return res;
	}

	@Override
	public Object visit(H2 h2, Object param) {
		String res = (String) param + "(H2 ->";
		for (SentenciaInterna s : h2.sentenciasInternas)
			res += " " + (String) s.accept(this, "");
		res += ")";
		return res;
	}

	@Override
	public Object visit(Parrafo parrafo, Object param) {
		String res = (String) param + "(paragraph ->";
		for (SentenciaInterna s : parrafo.sentenciasInternas)
			res += " " + (String) s.accept(this, "");
		res += ")";
		return res;
	}

	@Override
	public Object visit(Texto texto, Object param) {
		String res = (String) param;
		for (Palabra p : texto.palabras) {
			res += (String) p.accept(this, (String) param + stringHtml) + " ";
		}
		return res.trim();
	}

	@Override
	public Object visit(Palabra palabra, Object param) {
		return palabra.texto;
	}

	@Override
	public Object visit(Negrita negrita, Object param) {
		String texto = (String) negrita.texto.accept(this, (String) param + stringHtml);
		return (String) "(bold -> " + texto + ")";
	}

	@Override
	public Object visit(Cursiva cursiva, Object param) {
		String texto = (String) cursiva.texto.accept(this, (String) param + stringHtml);
		return (String) "(italic -> " + texto + ")";
	}

	@Override
	public Object visit(Underline underline, Object param) {
		String texto = (String) underline.texto.accept(this, (String) param + stringHtml);
		return (String) "(underline -> " + texto + ")";
	}

	@Override
	public Object visit(Enlace enlace, Object param) {
		String texto = (String) enlace.texto.accept(this, (String) param + stringHtml);
		return (String) param + "(enlace href = \"" + enlace.href + "\" -> " + texto + ")";
	}

}
