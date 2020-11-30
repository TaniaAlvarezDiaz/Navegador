package simpleHtml.ast;

import simpleHtml.visitor.Visitor;

public class Html implements AstHtml {

	public Cabecera cabecera;
	public Cuerpo cuerpo;

	/**
	 * Constructor
	 * 
	 * @param cabecera
	 * @param cuerpo
	 */
	public Html(Cabecera cabecera, Cuerpo cuerpo) {
		super();
		this.cabecera = cabecera;
		this.cuerpo = cuerpo;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
