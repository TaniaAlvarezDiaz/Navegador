package simpleHtml.ast;

import java.util.List;

import simpleHtml.visitor.Visitor;

public class Texto implements SentenciaInterna {

	public List<Palabra> palabras;

	/**
	 * Constructor
	 * 
	 * @param palabras
	 */
	public Texto(List<Palabra> palabras) {
		super();
		this.palabras = palabras;
	}

	@Override
	public String toString() {
		String res = "";
		if (palabras != null) {
			for (Palabra p : palabras)
				res += p.texto + " ";
		}
		return res.trim();
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

}
