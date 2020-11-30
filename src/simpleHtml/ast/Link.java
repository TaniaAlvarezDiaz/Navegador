package simpleHtml.ast;

import simpleHtml.visitor.Visitor;

public class Link implements AstHtml {

	public String href;
	public String rel;
	public String type;

	/**
	 * Constructor
	 * 
	 * @param href
	 * @param rel
	 * @param type
	 */
	public Link(String href, String rel, String type) {
		super();
		this.href = href;
		this.rel = rel;
		this.type = type;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
