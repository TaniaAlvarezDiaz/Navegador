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
	 * Método para añadir a la página la línea que se pasa por parámetro
	 * 
	 * @param line
	 */
	public void add(FormattedLine line) {
		lines.add(line);
	}

	/**
	 * Método para borrar de la página la línea que se pasa por parámetro
	 * 
	 * @param line
	 */
	public void remove(FormattedLine line) {
		lines.remove(line);
	}

	/**
	 * Método para contar las líneas que forman la página
	 * 
	 * @return
	 */
	public int countLines() {
		return lines.size();
	}

}
