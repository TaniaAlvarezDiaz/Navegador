package igu;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import render.FormattedLine;
import render.FormattedPage;
import render.FormattedText;
import simpleCss.CssAstCreator;
import simpleCss.ast.AstCss;
import simpleHtml.HtmlAstCreator;
import simpleHtml.ast.AstHtml;
import simpleHtml.visitor.BuscaNombreCssEnHtmlVisitor;
import simpleHtml.visitor.RenderVisitor;

public class Ventana extends JFrame {

	private static final long serialVersionUID = -2916392053171547597L;

	private final static String BASE_RESOURCES_HTML = "resources/html/";
	private final static String BASE_RESOURCES_CSS = "resources/css/";

	private Ventana ventana;
	private JPanel pnPrincipal;
	private JLabel titleCuadroBusqueda;
	private JTextField cuadroBusqueda;
	private JButton btnBuscar;
	private JTextPane textPane;

	public Ventana() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Ventana principal");
		this.setBounds(100, 100, 791, 629);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		// Crear panel principal
		crearPanelPrincipal();

		this.setContentPane(pnPrincipal);

		ventana = this;
	}

	/**
	 * Método para crear el título del cuadro de búsqueda
	 */
	private void crearTituloCuadroBusqueda() {
		titleCuadroBusqueda = new JLabel();
		titleCuadroBusqueda.setFont(new Font("Arial", Font.PLAIN, 14));
		titleCuadroBusqueda.setText("Introduce el nombre del fichero HTML a cargar:");
		titleCuadroBusqueda.setForeground(Color.BLACK);
		titleCuadroBusqueda.setBounds(20, 10, 747, 30);
		titleCuadroBusqueda.setLabelFor(cuadroBusqueda);
	}

	/**
	 * Método para crear el cuadro de búsqueda
	 */
	private void crearCuadroBusqueda() {
		cuadroBusqueda = new JTextField();
		cuadroBusqueda.setToolTipText("Nombre fichero HTML");
		cuadroBusqueda.setFont(new Font("Arial", Font.PLAIN, 12));
		cuadroBusqueda.setForeground(Color.BLACK);
		cuadroBusqueda.setBounds(20, 50, 747, 30);
	}

	/**
	 * Método para crear el botón buscar
	 */
	private void crearBtnBuscar() {
		btnBuscar = new JButton();
		btnBuscar.setMnemonic('B');
		btnBuscar.setFont(new Font("Arial", Font.PLAIN, 16));
		btnBuscar.setText("Buscar");
		btnBuscar.setForeground(Color.BLACK);
		btnBuscar.setBackground(Color.LIGHT_GRAY);
		btnBuscar.setBounds(20, 102, 747, 30);

		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadHtml(cuadroBusqueda.getText());
			}
		});

	}

	/**
	 * Método para crear el panel donde se va a mostrar el HTML
	 * 
	 * @return
	 */
	private JTextPane crearPanelTexto() {
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setFont(new Font("Arial", Font.PLAIN, 12));
		textPane.setBounds(20, 156, 747, 426);
		return textPane;
	}

	/**
	 * Método para crear el panel principal
	 */
	private void crearPanelPrincipal() {

		// Crear el cuadro de busqueda
		crearCuadroBusqueda();

		// Crear el titulo del cuadro de busqueda
		crearTituloCuadroBusqueda();

		// Crear el btnBuscar
		crearBtnBuscar();

		// Crear el panel para el texto
		crearPanelTexto();

		// Crear panel principal
		pnPrincipal = new JPanel();
		pnPrincipal.setBackground(SystemColor.control);
		pnPrincipal.setLayout(null);
		pnPrincipal.add(titleCuadroBusqueda); // pone el boton en el panel, siempre hay que ponerlo al final
		pnPrincipal.add(cuadroBusqueda);
		pnPrincipal.add(btnBuscar);
		pnPrincipal.add(textPane);
	}

	/**
	 * Método para cargar el fichero HTML que se pasa por parámetro
	 * 
	 * @param htmlName
	 */
	private void loadHtml(String htmlName) {
		AstHtml htmlAst = null;
		AstCss cssAst = null;
		AstCss cssDefaultAst = null;

		try {
			// Crear el AST del HTML
			FileReader fHtml = new FileReader(BASE_RESOURCES_HTML + htmlName);
			htmlAst = HtmlAstCreator.createHtmlAst(fHtml);

			// Si se creó correctamente, se crean los AST de los ficheros CSS
			if (htmlAst != null) {
				System.out.println("Árbol AST del HTML creado");

				// Crear AST del CSS por defecto
				cssDefaultAst = crearCssAst("Default.css");

				// Buscar fichero CSS
				BuscaNombreCssEnHtmlVisitor buscaCss = new BuscaNombreCssEnHtmlVisitor();
				String cssName = buscaCss.searchCss(htmlAst);

				// Generar AST del CSS indicado en el HTML
				cssAst = crearCssAst(cssName);

				// Renderizar la página
				if (htmlAst != null && cssDefaultAst != null) {
					RenderVisitor render = new RenderVisitor(cssDefaultAst, cssAst);
					FormattedPage fp = (FormattedPage) htmlAst.accept(render, null);
					if (fp != null) {
						textPane.setText(obtenerFormattedPage(fp));
					}
				}
			}
		} catch (FileNotFoundException e) {
			textPane.setText("Fichero " + htmlName + " no encontrado");
		}
	}

	/**
	 * Método para obtener el AST del fichero CSS que se pasa por parámetro
	 * 
	 * @param cssName
	 * @return
	 * @throws FileNotFoundException
	 */
	private AstCss crearCssAst(String cssName) throws FileNotFoundException {
		FileReader fCss = new FileReader(BASE_RESOURCES_CSS + cssName);
		return CssAstCreator.createCssAst(fCss);
	}

	/**
	 * Método para obtener la página formateada como texto
	 * 
	 * @param formattedPage
	 * @return
	 */
	private String obtenerFormattedPage(FormattedPage formattedPage) {
		String res = "";
		if (formattedPage != null) {

			// Actualizar título de la ventana
			ventana.setTitle(formattedPage.getTitle());

			res += "Título de la página: " + formattedPage.getTitle() + "\n\n";

			String ft = "";
			for (FormattedLine l : formattedPage.getLines()) {
				res += "(Line align : " + l.getTextAlign() + " | Metrics : " + l.calculateMetrics() + " >> ";

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
					res += "\n" + ft;
				}

				res += ")\n";
			}
		}
		return res;
	}

	public static void main(String[] args) {
		Ventana v = new Ventana();
		v.setVisible(true);
	}
}
