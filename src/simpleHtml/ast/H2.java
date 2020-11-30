package simpleHtml.ast;

import java.util.List;

import simpleHtml.visitor.Visitor;

public class H2 implements SentenciaCuerpo {

	public List<SentenciaInterna> sentenciasInternas;

	/**
	 * Constructor
	 * 
	 * @param sentenciasInternas
	 */
	public H2(List<SentenciaInterna> sentenciasInternas) {
		super();
		this.sentenciasInternas = sentenciasInternas;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
