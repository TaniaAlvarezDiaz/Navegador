package simpleHtml.ast;

import simpleHtml.visitor.Visitor;

public class Enlace implements SentenciaCuerpo, SentenciaInterna {

	public String href;
	public Texto texto;

	/**
	 * Constructor
	 * 
	 * @param href
	 * @param texto
	 */
	public Enlace(String href, Texto texto) {
		super();
		this.href = href;
		this.texto = texto;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
