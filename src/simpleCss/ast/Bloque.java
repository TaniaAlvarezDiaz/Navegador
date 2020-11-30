package simpleCss.ast;

import java.util.List;

import simpleCss.visitor.Visitor;

public class Bloque implements AstCss {

	public String nombre;
	public List<Sentencia> sentencias;

	/**
	 * Constructor
	 * 
	 * @param nombre
	 * @param sentencias
	 */
	public Bloque(String nombre, List<Sentencia> sentencias) {
		super();
		this.nombre = nombre;
		this.sentencias = sentencias;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
