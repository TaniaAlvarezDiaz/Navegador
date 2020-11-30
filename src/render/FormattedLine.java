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
	 * M�todo para a�adir a la l�nea el texto formateado que se pasa por par�metro
	 * 
	 * @param text
	 */
	public void add(FormattedText text) {
		texts.add(text);
	}

	/**
	 * M�todo para borrar de la l�nea el texto formateado que se pasa por par�metro
	 * 
	 * @param text
	 */
	public void remove(FormattedText text) {
		texts.remove(text);
	}

	/**
	 * M�todo para contar los textos que forman la l�nea
	 * 
	 * @return
	 */
	public int countTexts() {
		return texts.size();
	}

	/**
	 * M�todo para calcular las m�tricas
	 */
	public double calculateMetrics() {
		double res = 0;
		for (FormattedText t : texts)
			res += t.getMetrics();
		return res;
	}

}
