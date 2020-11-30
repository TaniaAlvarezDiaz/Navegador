package render;

import java.util.HashMap;
import java.util.Map;

public class FormattedText {

	private Map<String, String> attributes;
	private String text;
	private String color;
	private double fontSize;
	private String fontStyle;
	private double metrics = -1;

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		calculateMetrics();
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public double getFontSize() {
		return fontSize;
	}

	public void setFontSize(double fontSize) {
		this.fontSize = fontSize;
		calculateMetrics();
	}

	public String getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
		calculateMetrics();
	}

	public double getMetrics() {
		if (metrics == -1)
			calculateMetrics();
		return metrics;
	}

	/**
	 * Constructor
	 * 
	 * @param attributes
	 * @param text
	 * @param color
	 * @param fontSize
	 * @param fontStyle
	 */
	public FormattedText(Map<String, String> attributes, String text, String color, double fontSize, String fontStyle) {
		super();
		this.attributes = attributes;
		this.text = text;
		this.color = color;
		this.fontSize = fontSize;
		this.fontStyle = fontStyle;
		calculateMetrics();
	}

	/**
	 * Constructor
	 * 
	 * @param text
	 * @param color
	 * @param fontSize
	 * @param fontStyle
	 */
	public FormattedText(String text, String color, double fontSize, String fontStyle) {
		this(new HashMap<String, String>(), text, color, fontSize, fontStyle);
	}

	/**
	 * Método para calcular las métricas
	 */
	public void calculateMetrics() {
		metrics = text.length() * fontSize;
	}

}
