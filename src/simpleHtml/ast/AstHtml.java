package simpleHtml.ast;

import simpleHtml.visitor.Visitor;

public interface AstHtml {
	Object accept(Visitor v, Object param);
}
