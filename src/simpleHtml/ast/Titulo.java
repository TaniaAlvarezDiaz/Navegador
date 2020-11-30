package simpleHtml.ast;

import simpleHtml.visitor.Visitor;

public class Titulo implements AstHtml {

	public Texto texto;

	/**
	 * Constructor
	 * 
	 * @param texto
	 */
	public Titulo(Texto texto) {
		super();
		this.texto = texto;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
