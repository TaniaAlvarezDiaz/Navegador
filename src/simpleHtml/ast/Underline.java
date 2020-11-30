package simpleHtml.ast;

import simpleHtml.visitor.Visitor;

public class Underline implements SentenciaInterna {

	public Texto texto;

	/**
	 * Constructor
	 * 
	 * @param texto
	 */
	public Underline(Texto texto) {
		super();
		this.texto = texto;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
