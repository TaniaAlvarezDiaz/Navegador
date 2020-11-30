package paint;

import java.util.Map.Entry;

import render.FormattedLine;
import render.FormattedPage;
import render.FormattedText;

public class PrintPageTxt implements IPrintPage {

	@Override
	public void printPage(FormattedPage formattedPage) {
		if (formattedPage != null) {
			System.out.println("Título de la página: " + formattedPage.getTitle());

			String ft = "";
			for (FormattedLine l : formattedPage.getLines()) {
				System.out
						.println("(Line align : " + l.getTextAlign() + " | Metrics : " + l.calculateMetrics() + " >> ");

				for (FormattedText t : l.getTexts()) {
					ft = "\t(Format : " + t.getColor() + ", " + t.getFontSize() + ", " + t.getFontStyle()
							+ " | Metrics : " + t.getMetrics();

					// Si tiene atributos, los mostramos
					if (t.getAttributes() != null && !t.getAttributes().isEmpty()) {
						ft += " | Attributes : ";
						for (Entry<String, String> entry : t.getAttributes().entrySet()) {
							ft += "{" + entry.getKey() + " = " + entry.getValue() + "}, ";
						}
						// Quitar la coma y el espacio final
						ft = ft.replaceAll(", $", "");
					}

					ft += " >> " + t.getText() + ")";
					System.out.println(ft);
				}

				System.out.println(")\n");
			}
		} else
			System.out.print("Formato de página inválido.");
	}
}
