package simpleCss.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import simpleCss.ast.AstCss;
import simpleCss.ast.Bloque;
import simpleCss.ast.Css;
import simpleCss.ast.Sentencia;

/**
 * Analizador sintáctico de CSS
 * 
 * @author Tania Álvarez Díaz
 *
 */
public class Parser {

	private Lexicon lex;
	private boolean error = false;

	/**
	 * Constructor
	 * 
	 * @param lex
	 */
	public Parser(Lexicon lex) {
		this.lex = lex;
	}

	/**
	 * Método para iniciar el analizador sintáctico
	 * 
	 * @return
	 */
	public AstCss parse() {
		return css();
	}

	/**
	 * Método para mostrar el error sintáctico que se produce
	 * 
	 * @param e
	 * @param line
	 */
	private void errorSintactico(String e, int line) {
		error = true;
		System.out.println("Error Sintáctico : " + e + " en la línea " + line);
	}

	/**
	 * Parsear el CSS
	 * 
	 * @return
	 */
	private Css css() {
		List<Bloque> bloques = bloques();
		// Leer token
		Token t = lex.getToken();
		if (t.token != TokensId.EOF)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba el final de fichero.", t.getLine());
		return error ? null : new Css(bloques);
	}

	/**
	 * Parsear los bloques
	 * 
	 * @return
	 */
	private List<Bloque> bloques() {
		List<Bloque> bloques = new ArrayList<Bloque>();
		String nombreBloque;
		// No se lee token porque lo lee el metodo nombre()
		Token t;
		// El bloque empieza por un nombre
		while ((nombreBloque = nombre()) != null) {
			// Obtener el siguiente token
			t = lex.getToken();
			// Se espera una llave {
			if (t.token != TokensId.LLA)
				errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba '{'.", t.getLine());
			// Llamar a sentencias
			List<Sentencia> sentencias = sentencias();
			// Leer siguiente token
			t = lex.getToken();
			// Se espera una llave de cierre }
			if (t.token != TokensId.LLC)
				errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba '}'.", t.getLine());
			// Si no hubo error, crear bloque
			if (!error)
				bloques.add(new Bloque(nombreBloque, sentencias));
			// Leer siguiente token
			t = lex.getToken();
			// Si es EOF se finaliza este bucle
			if (t.token == TokensId.EOF)
				break;
			// Volver al token anterior
			lex.returnLastToken();
		}

		return bloques;
	}

	/**
	 * Parsear el nombre de un bloque
	 * 
	 * @return
	 */
	private String nombre() {
		Token t = lex.getToken();
		switch (t.token) {
		case H1:
			return "h1";
		case H2:
			return "h2";
		case P:
			return "p";
		case A:
			return "a";
		default:
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba 'h1', 'h2', 'p' o 'a'.", t.getLine());
			return null;
		}
	}

	/**
	 * Parsear las sentencias
	 * 
	 * @return
	 */
	private List<Sentencia> sentencias() {
		List<Sentencia> sentencias = new ArrayList<Sentencia>();
		String clave;
		// Leer token
		Token t = lex.getToken();
		Token tPrimero;
		// Obtener cada sentencia
		while ((clave = clave(t)) != null) {
			tPrimero = t;
			// Leer token
			t = lex.getToken();
			// Se esperan dos puntos
			if (t.token != TokensId.DP)
				errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba ':'.", t.getLine());
			// Se obtiene el valor en función de la clave
			String valor = valor(tPrimero);
			// Obtener token
			t = lex.getToken();
			// Se espera punto y coma
			if (t.token != TokensId.PC)
				errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba ';'.", t.getLine());
			// Si no hubo error crear sentencia
			if (!error)
				sentencias.add(new Sentencia(clave, valor));
			// Leer token
			t = lex.getToken();
			// Si el token es }, es que se terminan las sentencias del bloque, se recupera
			// el token anterior y se finaliza este bucle
			if (t.token == TokensId.LLC) {
				lex.returnLastToken();
				break;
			}
		}
		return sentencias;
	}

	/**
	 * Parsear las claves de una sentencia en función del token leído
	 * 
	 * @param t
	 * @return
	 */
	private String clave(Token t) {
		switch (t.token) {
		case COLOR:
			return "color";
		case FSIZE:
			return "font-size";
		case TALIGN:
			return "text-align";
		case FSTYLE:
			return "font-style";
		default:
			errorSintactico("Encontrado '" + t.getLexeme()
					+ "'. Se esperaba 'color', 'font-size', 'text-align' o 'font-style'.", t.getLine());
			return null;
		}
	}

	/**
	 * Parsear los valores de una sentencia en función de la clave (token que se
	 * pasa por parametro)
	 * 
	 * @param t
	 * @return
	 */
	private String valor(Token t) {
		switch (t.token) {
		case COLOR:
			return color();
		case FSIZE:
			return size();
		case TALIGN:
			return aligment();
		case FSTYLE:
			return fstyle();
		default:
			errorSintactico("Encontrado '" + t.getLexeme() + "'.", t.getLine());
			return null;
		}
	}

	/**
	 * Parsear los colores permitidos
	 * 
	 * @return
	 */
	private String color() {
		Token t = lex.getToken();
		switch (t.token) {
		case BLACK:
			return "black";
		case WHITE:
			return "white";
		case YELLOW:
			return "yellow";
		case BLUE:
			return "blue";
		case GREEN:
			return "green";
		default:
			errorSintactico(
					"Encontrado '" + t.getLexeme() + "'. Se esperaba 'black', 'white', 'yellow', 'blue' o 'green'.",
					t.getLine());
			return null;
		}
	}

	/**
	 * Parsear los tamaños permitidos
	 * 
	 * @return
	 */
	private String size() {
		Token t = lex.getToken();
		// Separar las unidades
		String value = "";
		String unit = "";
		for (char c : t.lexeme.toCharArray()) {
			if (Character.isDigit(c))
				value += c;
			else
				unit += c;
		}
		if (!Pattern.matches("[0-9]+", value) || !unit.equals("px")) {
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba un valor con el patrón '[0-9]+ px'.",
					t.getLine());
			return null;
		}
		return t.lexeme;
	}

	/**
	 * Parsear la alineación permitida
	 * 
	 * @return
	 */
	private String aligment() {
		Token t = lex.getToken();
		switch (t.token) {
		case LEFT:
			return "left";
		case RIGHT:
			return "right";
		case CENTER:
			return "center";
		default:
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba 'left', 'right' o 'center'.", t.getLine());
			return null;
		}
	}

	/**
	 * Parsear los estilos de fuente permitidos
	 * 
	 * @return
	 */
	private String fstyle() {
		Token t = lex.getToken();
		switch (t.token) {
		case NORMAL:
			return "normal";
		case BOLD:
			return "bold";
		case ITALIC:
			return "italic";
		case UNDERLINE:
			return "underline";
		default:
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba 'normal', 'bold', 'italic' o 'underline'.",
					t.getLine());
			return null;
		}
	}

}
