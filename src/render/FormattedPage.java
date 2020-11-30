package render;

import java.util.ArrayList;
import java.util.List;

public class FormattedPage {

	private String title;
	private List<FormattedLine> lines = new ArrayList<FormattedLine>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<FormattedLine> getLines() {
		return lines;
	}

	/**
	 * M�todo para a�adir a la p�gina la l�nea que se pasa por par�metro
	 * 
	 * @param line
	 */
	public void add(FormattedLine line) {
		lines.add(line);
	}

	/**
	 * M�todo para borrar de la p�gina la l�nea que se pasa por par�metro
	 * 
	 * @param line
	 */
	public void remove(FormattedLine line) {
		lines.remove(line);
	}

	/**
	 * M�todo para contar las l�neas que forman la p�gina
	 * 
	 * @return
	 */
	public int countLines() {
		return lines.size();
	}

}
