package render;

import java.util.ArrayList;
import java.util.List;

public class FormattedLine {

	private String textAlign;
	private List<FormattedText> texts = new ArrayList<FormattedText>();

	public String getTextAlign() {
		return textAlign;
	}

	public List<FormattedText> getTexts() {
		return texts;
	}

	/**
	 * Constructor
	 * 
	 * @param textAlign
	 */
	public FormattedLine(String textAlign) {
		super();
		this.textAlign = textAlign;
	}

	/**
	 * Método para añadir a la línea el texto formateado que se pasa por parámetro
	 * 
	 * @param text
	 */
	public void add(FormattedText text) {
		texts.add(text);
	}

	/**
	 * Método para borrar de la línea el texto formateado que se pasa por parámetro
	 * 
	 * @param text
	 */
	public void remove(FormattedText text) {
		texts.remove(text);
	}

	/**
	 * Método para contar los textos que forman la línea
	 * 
	 * @return
	 */
	public int countTexts() {
		return texts.size();
	}

	/**
	 * Método para calcular las métricas
	 */
	public double calculateMetrics() {
		double res = 0;
		for (FormattedText t : texts)
			res += t.getMetrics();
		return res;
	}

}
