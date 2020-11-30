package simpleHtml.ast;

import simpleHtml.visitor.Visitor;

public class Palabra implements AstHtml {

	public String texto;

	/**
	 * Constructor
	 * 
	 * @param texto
	 */
	public Palabra(String texto) {
		super();
		this.texto = texto;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
