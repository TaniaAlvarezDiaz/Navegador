package simpleHtml.ast;

import java.util.List;

import simpleHtml.visitor.Visitor;

public class Cuerpo implements AstHtml {

	public List<SentenciaCuerpo> sentenciasCuerpo;

	/**
	 * Constructor
	 * 
	 * @param sentenciasCuerpo
	 */
	public Cuerpo(List<SentenciaCuerpo> sentenciasCuerpo) {
		super();
		this.sentenciasCuerpo = sentenciasCuerpo;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
