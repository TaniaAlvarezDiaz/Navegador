package simpleCss.ast;

import java.util.List;

import simpleCss.visitor.Visitor;

public class Css implements AstCss {

	public List<Bloque> bloques;

	/**
	 * Constructor
	 * 
	 * @param bloques
	 */
	public Css(List<Bloque> bloques) {
		super();
		this.bloques = bloques;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
