package simpleHtml.ast;

import java.util.List;

import simpleHtml.visitor.Visitor;

public class Cabecera implements AstHtml {

	public Titulo titulo;
	public List<Link> links;

	/**
	 * Constructor
	 * 
	 * @param titulo
	 * @param links
	 */
	public Cabecera(Titulo titulo, List<Link> links) {
		super();
		this.titulo = titulo;
		this.links = links;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
