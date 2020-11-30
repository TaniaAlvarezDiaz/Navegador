package simpleCss.ast;

import simpleCss.visitor.Visitor;

public interface AstCss {
		Object accept(Visitor v, Object param);
}
