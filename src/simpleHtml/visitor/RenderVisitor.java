package simpleHtml.visitor;

import java.util.Map;

import render.FormattedLine;
import render.FormattedPage;
import render.FormattedText;
import simpleCss.ast.AstCss;
import simpleCss.visitor.BuscaParamEnCssVisitor;
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

public class RenderVisitor implements Visitor {
	private AstCss defaultCss;
	private AstCss astCss;
	private BuscaParamEnCssVisitor bpCss = new BuscaParamEnCssVisitor();
	private FormattedPage formattedPage = new FormattedPage();

	/**
	 * Constructor
	 * 
	 * @param cssDefaultAst
	 * @param cssAst
	 */
	public RenderVisitor(AstCss cssDefaultAst, AstCss cssAst) {
		this.defaultCss = cssDefaultAst;
		this.astCss = cssAst;
	}

	@Override
	public Object visit(Html html, Object param) {
		// Se visita la cabecera para obtener el titulo
		formattedPage.setTitle((String) html.cabecera.accept(this, null));
		// Se visita el cuerpo para obtener la página formateada
		formattedPage = (FormattedPage) html.cuerpo.accept(this, null);
		return formattedPage;
	}

	@Override
	public Object visit(Cabecera cabecera, Object param) {
		// Se busca el título
		FormattedText ft = (FormattedText) cabecera.titulo.accept(this, "head");
		return ft.getText();
	}

	@Override
	public Object visit(Titulo titulo, Object param) {
		return titulo.texto.accept(this, param);
	}

	@Override
	public Object visit(Link link, Object param) {
		// Not neccesary
		return null;
	}

	@Override
	public Object visit(Cuerpo cuerpo, Object param) {
		for (SentenciaCuerpo sentenciaCuerpo : cuerpo.sentenciasCuerpo)
			formattedPage.add((FormattedLine) sentenciaCuerpo.accept(this, null));
		return formattedPage;
	}

	@Override
	public Object visit(H1 h1, Object param) {
		// Buscar text-align, primero en el CSS del HTML y si no se encuentra, en el CSS
		// por defecto (siempre está en este último)
		String text_align = bpCss.search(astCss, "h1", "text-align");
		if (text_align == null)
			text_align = bpCss.search(defaultCss, "h1", "text-align");
		// Crear la linea formateada
		FormattedLine fl = new FormattedLine(text_align);
		// Obtener las sentencias internas
		for (SentenciaInterna s : h1.sentenciasInternas)
			fl.add((FormattedText) s.accept(this, "h1"));
		return fl;
	}

	@Override
	public Object visit(H2 h2, Object param) {
		// Buscar text-align, primero en el CSS del HTML y si no se encuentra, en el CSS
		// por defecto (siempre está en este último)
		String text_align = bpCss.search(astCss, "h2", "text-align");
		if (text_align == null)
			text_align = bpCss.search(defaultCss, "h2", "text-align");
		// Crear la linea formateada
		FormattedLine fl = new FormattedLine(text_align);
		// Obtener las sentencias internas
		for (SentenciaInterna s : h2.sentenciasInternas)
			fl.add((FormattedText) s.accept(this, "h2"));
		return fl;
	}

	@Override
	public Object visit(Parrafo parrafo, Object param) {
		// Buscar text-align, primero en el CSS del HTML y si no se encuentra, en el CSS
		// por defecto (siempre está en este último)
		String text_align = bpCss.search(astCss, "p", "text-align");
		if (text_align == null)
			text_align = bpCss.search(defaultCss, "p", "text-align");
		// Crear la linea formateada
		FormattedLine fl = new FormattedLine(text_align);
		// Obtener las sentencias internas
		for (SentenciaInterna s : parrafo.sentenciasInternas)
			fl.add((FormattedText) s.accept(this, "p"));
		return fl;
	}

	@Override
	public Object visit(Texto texto, Object param) {

		// Obtener el texto
		String text = "";
		for (Palabra p : texto.palabras) {
			text += (String) p.accept(this, null) + " ";
		}
		// Como parámetro viene el identificador de la sentencia en la que nos
		// encontramos
		String identPadre = (String) param;
		// Buscar color, primero en el CSS del HTML y si no se encuentra, en el CSS
		// por defecto (siempre está en este último)
		String color = bpCss.search(astCss, identPadre, "color");
		if (color == null)
			color = bpCss.search(defaultCss, identPadre, "color");
		// Buscar font-size, primero en el CSS del HTML y si no se encuentra, en el CSS
		// por defecto (siempre está en este último)
		String fontSize = bpCss.search(astCss, identPadre, "font-size");
		if (fontSize == null)
			fontSize = bpCss.search(defaultCss, identPadre, "font-size");
		// La unidad de fontSize es PX
		if (fontSize != null)
			fontSize = fontSize.replace("px", "").trim();
		double font_size = fontSize != null ? Double.parseDouble(fontSize) : 0;
		// Buscar font-style, primero en el CSS del HTML y si no se encuentra, en el CSS
		// por defecto (siempre está en este último)
		String font_style = bpCss.search(astCss, identPadre, "font-style");
		if (font_style == null)
			font_style = bpCss.search(defaultCss, identPadre, "font-style");

		return new FormattedText(text.trim(), color, font_size, font_style);
	}

	@Override
	public Object visit(Palabra palabra, Object param) {
		return palabra.texto;
	}

	@Override
	public Object visit(Negrita negrita, Object param) {
		FormattedText ft = (FormattedText) negrita.texto.accept(this, param);
		ft.setFontStyle("bold");
		return ft;
	}

	@Override
	public Object visit(Cursiva cursiva, Object param) {
		FormattedText ft = (FormattedText) cursiva.texto.accept(this, param);
		ft.setFontStyle("italic");
		return ft;
	}

	@Override
	public Object visit(Underline underline, Object param) {
		FormattedText ft = (FormattedText) underline.texto.accept(this, param);
		ft.setFontStyle("underline");
		return ft;
	}

	@Override
	public Object visit(Enlace enlace, Object param) {
		FormattedText ft = (FormattedText) enlace.texto.accept(this, "a");
		// Crear los atributos, en este caso solo tiene "href"
		ft.setAttributes(Map.of("href", enlace.href));
		// Si param == null, el enlace es una sentenciaCuerpo (formattedLine), en otro
		// caso, es una
		// sentenciaInterna (formattedText)
		if (param != null) {
			return ft;
		} else {
			// Buscar text-align, primero en el CSS del HTML y si no se encuentra, en el CSS
			// por defecto (siempre está en este último)
			String text_align = bpCss.search(astCss, "a", "text-align");
			if (text_align == null)
				text_align = bpCss.search(defaultCss, "a", "text-align");
			// Crear la linea formateada
			FormattedLine fl = new FormattedLine(text_align);
			// Añadir a la linea el texto formateado
			fl.add(ft);
			return fl;
		}
	}

}
