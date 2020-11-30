package simpleCss.ast;

import simpleCss.visitor.Visitor;

public class Sentencia implements AstCss {

	public String clave;
	public String valor;

	/**
	 * Constructor
	 * 
	 * @param clave
	 * @param valor
	 */
	public Sentencia(String clave, String valor) {
		super();
		this.clave = clave;
		this.valor = valor;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
