package simpleHtml.parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Analizador léxico de HTML
 * 
 * @author Tania Álvarez Díaz
 *
 */
public class Lexicon {

	public boolean errorLexico = false;

	// Gestión de tokens
	List<Token> tokens = new ArrayList<Token>();
	int i = 0; // Último token entregado en getToken()
	// Gestión de lectura del fichero
	FileReader filereader;
	boolean charBuffUsed = false;
	char charBuff;
	int line = 1; // indica la línea del fichero fuente

	HashSet<Character> charText = new HashSet<Character>();

	public Lexicon(FileReader f) {
		filereader = f;
		String lex;
		loadSet();
		try {
			char valor = (char) 0;
			while (valor != (char) -1) {
				valor = nextChar();
				switch ((char) valor) {
				case '<':
					valor = nextChar();
					// Etiquetas de cierre
					if ((char) valor == '/') {
						valor = nextChar();
						switch ((char) valor) {
						case 'h':
							lex = getLexeme("</h", '>');
							if (lex.equals("</html>"))
								tokens.add(new Token(TokensId.FHTML, lex, line));
							else if (lex.equals("</head>"))
								tokens.add(new Token(TokensId.FHEAD, lex, line));
							else if (lex.equals("</h1>"))
								tokens.add(new Token(TokensId.FH1, lex, line));
							else if (lex.equals("</h2>"))
								tokens.add(new Token(TokensId.FH2, lex, line));
							else
								errorLexico(lex);
							break;
						case 't':
							lex = getLexeme("</t", '>');
							if (lex.equals("</title>"))
								tokens.add(new Token(TokensId.FTITLE, lex, line));
							break;
						case 'b':
							lex = getLexeme("</b", '>');
							if (lex.equals("</body>"))
								tokens.add(new Token(TokensId.FBODY, lex, line));
							else if (lex.equals("</b>"))
								tokens.add(new Token(TokensId.FB, lex, line));
							break;
						case 'p':
							lex = getLexeme("</p", '>');
							if (lex.equals("</p>"))
								tokens.add(new Token(TokensId.FP, lex, line));
							break;
						case 'i':
							lex = getLexeme("</i", '>');
							if (lex.equals("</i>"))
								tokens.add(new Token(TokensId.FI, lex, line));
							break;
						case 'u':
							lex = getLexeme("</u", '>');
							if (lex.equals("</u>"))
								tokens.add(new Token(TokensId.FU, lex, line));
							break;
						case 'a':
							lex = getLexeme("</a", '>');
							if (lex.equals("</a>"))
								tokens.add(new Token(TokensId.FA, lex, line));
							break;
						default:
							errorLexico(getLexeme("<" + valor, '>'));
						}
					}
					// Etiquetas de apertura
					else {
						switch ((char) valor) {
						case 'l':
							lex = getLexeme("<l", 'k');
							if (lex.equals("<link"))
								tokens.add(new Token(TokensId.LINK, lex, line));
							else
								errorLexico(lex);
							break;
						case 'h':
							lex = getLexeme("<h", '>');
							if (lex.equals("<html>"))
								tokens.add(new Token(TokensId.HTML, lex, line));
							else if (lex.equals("<head>"))
								tokens.add(new Token(TokensId.HEAD, lex, line));
							else if (lex.equals("<h1>"))
								tokens.add(new Token(TokensId.H1, lex, line));
							else if (lex.equals("<h2>"))
								tokens.add(new Token(TokensId.H2, lex, line));
							else
								errorLexico(lex);
							break;
						case 't':
							lex = getLexeme("<t", '>');
							if (lex.equals("<title>"))
								tokens.add(new Token(TokensId.TITLE, lex, line));
							else
								errorLexico(lex);
							break;
						case 'b':
							lex = getLexeme("<b", '>');
							if (lex.equals("<body>"))
								tokens.add(new Token(TokensId.BODY, lex, line));
							else if (lex.equals("<b>"))
								tokens.add(new Token(TokensId.B, lex, line));
							else
								errorLexico(lex);
							break;
						case 'p':
							lex = getLexeme("<p", '>');
							if (lex.equals("<p>"))
								tokens.add(new Token(TokensId.P, lex, line));
							else
								errorLexico(lex);
							break;
						case 'i':
							lex = getLexeme("<i", '>');
							if (lex.equals("<i>"))
								tokens.add(new Token(TokensId.I, lex, line));
							else
								errorLexico(lex);
							break;
						case 'u':
							lex = getLexeme("<u", '>');
							if (lex.equals("<u>"))
								tokens.add(new Token(TokensId.U, lex, line));
							else
								errorLexico(lex);
							break;
						case '!':
							if (!deleteComment())
								errorLexico("Caracter '!' encontrado en linea " + line);
							break;
						case 'a':
							lex = getLexeme("<a", ' ');
							if (lex.equals("<a "))
								tokens.add(new Token(TokensId.A, lex.trim(), line));
							else
								errorLexico(lex);
							break;
						default:
							errorLexico(getLexeme("<" + valor, '>'));

						}
					}
					break;
				case '>':
					tokens.add(new Token(TokensId.MAYOR, new String(">"), line));
					break;
				case '=':
					tokens.add(new Token(TokensId.EQ, new String("="), line));
					break;
				case '"':
					tokens.add(new Token(TokensId.COMILLAS, new String("\""), line));
					break;
				case '\n':
					line++;
				case '\r':
				case ' ':
				case '\t':
				case (char) -1:
					// Eliminar todos los espacios TokensId.WS
					break;
				default:
					// Texto
					lex = getLexemeTEXT(new String("" + (char) valor));
					// Atributos de Link
					if (lex.equals("href"))
						tokens.add(new Token(TokensId.HREF, lex, line));

					else if (lex.equals("rel"))
						tokens.add(new Token(TokensId.REL, lex, line));
					else if (lex.equals("type"))
						tokens.add(new Token(TokensId.TYPE, lex, line));
					else
						tokens.add(new Token(TokensId.PALABRA, lex, line));
					break;
				}
			}
			filereader.close();
		} catch (IOException e) {
			System.out.println("Error E/S: " + e);
		}

	}

	// ++
	// ++ Operaciones para el Sintactico
	// ++
	// Reset
	public void reset() {
		this.i = 0;
	}

	// Devolver el último token
	public void returnLastToken() {
		i--;
	}

	// Get Token
	public Token getToken() {
		if (i < tokens.size()) {
			return tokens.get(i++);
		}
		return new Token(TokensId.EOF, "EOF", line);
	}

	// Dado el inicio de una cadena y el caracter final, devuelve el lexema
	// correspondiente
	private String getLexeme(String lexStart, char finChar) throws IOException {
		String lexReturned = lexStart;
		char valor;
		do {
			valor = nextChar();
			lexReturned = lexReturned + ((char) valor);
		} while (((char) valor != finChar) && ((char) valor != -1));
		// returnChar(valor);
		return lexReturned;
	}

	// Devuelve un lexema de texto, el caracter final es un espacio
	private String getLexemeTEXT(String lexStart) throws IOException {
		String lexReturned = lexStart;
		char valor = nextChar();
		while (charText.contains(((char) valor)) && ((char) valor != -1)) {
			lexReturned = lexReturned + ((char) valor);
			valor = nextChar();
		}
		returnChar(valor);
		return lexReturned;
	}

	// Carga el conjunto de caracteres admitidos por el analizador léxico
	private void loadSet() {
		String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZÁÉÍÓÚabcdefghijklmnopqrstuvwxyzáéíóú0123456789.,;:+-*/()[]!?";
		int i = 0;
		Character a = new Character('a');
		while (i < s.length()) {
			a = s.charAt(i);
			charText.add(a);
			i++;
		}
	}

	// Devuelve el siguiente caracter de fuente
	private char nextChar() throws IOException {
		if (charBuffUsed) {
			charBuffUsed = false;
			return charBuff;
		} else {
			int valor = filereader.read();
			return ((char) valor);
		}
	}

	// Devuelve un caracter que se ha leído pero no se ha consumido al buffer
	private void returnChar(char r) {
		charBuffUsed = true;
		charBuff = r;
	}

	// Borra un comentario que está encerrado en <!-- y -->
	private boolean deleteComment() throws IOException {
		boolean r = false;
		char c;
		// El comentario empieza por <!--, se comprueban los --
		for (int i = 0; i < 2; i++) {
			c = nextChar();
			if (c != '-')
				return false;
		}
		do {
			c = nextChar();
			if (c == (char) -1) // Inesperado fin de fichero
				return false;
			if (c == '\n') // debe seguir contando líneas
				line++;
			if (c == '-') {
				c = nextChar();
				if (c == '-') {
					c = nextChar();

					// Casuística de que el comentario termine en ------->
					while (c == '-')
						c = nextChar();

					if (c == '>')
						r = true;
				}
			}
		} while (!r);

		return r;
	}

	// Emite error léxico
	private void errorLexico(String e) {
		errorLexico = true;
		System.out.println("Error léxico en la línea " + line + " : " + e);
	}
}
