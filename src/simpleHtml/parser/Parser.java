package simpleHtml.parser;

import java.util.ArrayList;
import java.util.List;

import simpleHtml.ast.AstHtml;
import simpleHtml.ast.Cabecera;
import simpleHtml.ast.Cuerpo;
import simpleHtml.ast.Cursiva;
import simpleHtml.ast.Enlace;
import simpleHtml.ast.H1;
import simpleHtml.ast.H2;
import simpleHtml.ast.Html;
import simpleHtml.ast.Link;
import simpleHtml.ast.Negrita;
import simpleHtml.ast.Palabra;
import simpleHtml.ast.Parrafo;
import simpleHtml.ast.SentenciaCuerpo;
import simpleHtml.ast.SentenciaInterna;
import simpleHtml.ast.Texto;
import simpleHtml.ast.Titulo;
import simpleHtml.ast.Underline;

/**
 * Analizador sintáctico de HTML
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
	public AstHtml parse() {
		return html();
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
	 * Método para saber si el token que se pasa por parámetro es el fin de una
	 * sentencia cuerpo
	 * 
	 * @param t
	 * @return
	 */
	private boolean finSentenciaCuerpo(Token t) {
		if (t.token == TokensId.FH1 || t.token == TokensId.FH2 || t.token == TokensId.FP || t.token == TokensId.FA)
			return true;
		return false;
	}

	/**
	 * Parsear el HTML
	 * 
	 * @return
	 */
	private Html html() {
		// Leer token
		Token t = lex.getToken();
		// Se espera HTML
		if (t.token != TokensId.HTML)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '<html>'", t.getLine());
		// Procesar cabecera y cuerpo
		Cabecera cabecera = cabecera();
		Cuerpo cuerpo = cuerpo();
		// Leer token
		t = lex.getToken();
		// Se espera FHTML
		if (t.token != TokensId.FHTML)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '</html>'", t.getLine());
		// Leer token
		t = lex.getToken();
		// Se espera fin de fichero
		if (t.token != TokensId.EOF)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba el final de fichero.", t.getLine());
		return error ? null : new Html(cabecera, cuerpo);
	}

	/**
	 * Parsear la cabecera
	 * 
	 * @return
	 */
	private Cabecera cabecera() {
		Token t = lex.getToken();
		// Se espera HEAD
		if (t.token != TokensId.HEAD)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '<head>'", t.getLine());
		// Procesar titulo
		Titulo titulo = titulo();
		List<Link> links = links();
		// Leer token
		t = lex.getToken();
		// Se espera FHEAD
		if (t.token != TokensId.FHEAD)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '</head>'", t.getLine());
		return error ? null : new Cabecera(titulo, links);
	}

	/**
	 * Parsear el titulo de la cabecera
	 * 
	 * @return
	 */
	private Titulo titulo() {
		Token t = lex.getToken();
		// Se espera TITLE
		if (t.token != TokensId.TITLE)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '<title>'", t.getLine());
		// Obtener el texto
		Texto texto = texto();
		// Leer token
		t = lex.getToken();
		// Se espera FTITLE
		if (t.token != TokensId.FTITLE)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '</title>'", t.getLine());
		return error ? null : new Titulo(texto);
	}

	/**
	 * Parsear los links de la cabecera
	 * 
	 * @return
	 */
	private List<Link> links() {
		List<Link> links = new ArrayList<Link>();
		// Leer token
		Token t = lex.getToken();
		while (t.token == TokensId.LINK) {
			Link link = link();
			if (link != null)
				links.add(link);
			// Leer siguiente token
			t = lex.getToken();
		}
		// Volver al token anterior, necesario para que en el siguiente método el token
		// no vaya leído
		lex.returnLastToken();
		return links;
	}

	/**
	 * Parsear un link de la cabecera
	 * 
	 * @return
	 */
	private Link link() {
		String href = "";
		String rel = "";
		String type = "";
		// Leer token
		Token t = lex.getToken();
		// Se espera HREF
		if (t.token != TokensId.HREF)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba el atributo 'href'", t.getLine());
		// Leer token
		t = lex.getToken();
		// Se espera EQ
		if (t.token != TokensId.EQ)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba '='", t.getLine());
		// Obtener HREF
		href = cadena();
		// Leer token
		t = lex.getToken();
		// Se espera REL
		if (t.token != TokensId.REL)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba el atributo 'rel'", t.getLine());
		// Leer token
		t = lex.getToken();
		// Se espera EQ
		if (t.token != TokensId.EQ)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba '='", t.getLine());
		// Obtener REL
		rel = cadena();
		// Leer token
		t = lex.getToken();
		// Se espera TYPE
		if (t.token != TokensId.TYPE)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba el atributo 'type'", t.getLine());
		// Leer token
		t = lex.getToken();
		// Se espera EQ
		if (t.token != TokensId.EQ)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba '='", t.getLine());
		// Obtener TYPE
		type = cadena();
		// Leer token
		t = lex.getToken();
		// Se espera MAYOR
		if (t.token != TokensId.MAYOR)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba '>'", t.getLine());

		return error ? null : new Link(href, rel, type);
	}

	/**
	 * Parsear la cadena de los atributos de LINK
	 * 
	 * @return
	 */
	private String cadena() {
		// Leer token
		Token t = lex.getToken();
		// Se espera COMILLAS
		if (t.token != TokensId.COMILLAS)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba '\"'", t.getLine());
		// Obtener el texto
		Texto texto = texto();
		// Leer token
		t = lex.getToken();
		// Se espera COMILLAS
		if (t.token != TokensId.COMILLAS)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba '\"'", t.getLine());
		return error ? null : texto.toString();
	}

	/**
	 * Parsear el cuerpo
	 * 
	 * @return
	 */
	private Cuerpo cuerpo() {
		Token t = lex.getToken();
		// Se espera BODY
		if (t.token != TokensId.BODY)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '<body>'", t.getLine());
		// Procesar las sentencias del cuerpo
		List<SentenciaCuerpo> sentenciasCuerpo = sentenciasCuerpo();
		// Leer token
		t = lex.getToken();
		// Se espera HEAD
		if (t.token != TokensId.FBODY)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '</body>'", t.getLine());
		return error ? null : new Cuerpo(sentenciasCuerpo);
	}

	/**
	 * Parsear las sentencias del cuerpo
	 * 
	 * @return
	 */
	private List<SentenciaCuerpo> sentenciasCuerpo() {
		List<SentenciaCuerpo> sentenciasCuerpo = new ArrayList<SentenciaCuerpo>();
		SentenciaCuerpo sentenciaCuerpo;
		// Leer token
		Token t = lex.getToken();
		// Obtener sentencias cuerpo
		while ((sentenciaCuerpo = sentenciaCuerpo(t)) != null) {
			sentenciasCuerpo.add(sentenciaCuerpo);
			// Leer token
			t = lex.getToken();
			// Si se encuentra FBODY, se recupera el token anterior y se finaliza este bucle
			if (t.token == TokensId.FBODY) {
				lex.returnLastToken();
				break;
			}
		}
		return sentenciasCuerpo;
	}

	/**
	 * Parsear una de las sentencias del cuerpo
	 * 
	 * @param t
	 * @return
	 */
	private SentenciaCuerpo sentenciaCuerpo(Token t) {
		switch (t.token) {
		case H1:
			return h1();
		case H2:
			return h2();
		case P:
			return parrafo();
		case A:
			return enlace();
		default:
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba '<h1>', '<h2>', '<p>' o '<a'.",
					t.getLine());
			return null;
		}
	}

	/**
	 * Parsear H1
	 * 
	 * @return
	 */
	private H1 h1() {
		// Parsear sentencias internas
		List<SentenciaInterna> sentenciasInternas = sentenciasInternas();
		// Leer token
		Token t = lex.getToken();
		// Se espera FH1
		if (t.token != TokensId.FH1)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '</h1>'", t.getLine());
		return error ? null : new H1(sentenciasInternas);
	}

	/**
	 * Parsear H2
	 * 
	 * @return
	 */
	private H2 h2() {
		// Parsear sentencias internas
		List<SentenciaInterna> sentenciasInternas = sentenciasInternas();
		// Leer token
		Token t = lex.getToken();
		// Se espera FH2
		if (t.token != TokensId.FH2)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '</h2>'", t.getLine());
		return error ? null : new H2(sentenciasInternas);
	}

	/**
	 * Parsear un parrafo
	 * 
	 * @return
	 */
	private Parrafo parrafo() {
		// Parsear sentencias internas
		List<SentenciaInterna> sentenciasInternas = sentenciasInternas();
		// Leer token
		Token t = lex.getToken();
		// Se espera FP
		if (t.token != TokensId.FP)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '</p>'", t.getLine());
		return error ? null : new Parrafo(sentenciasInternas);
	}

	/**
	 * Parsear las sentencias internas
	 * 
	 * @return
	 */
	private List<SentenciaInterna> sentenciasInternas() {
		List<SentenciaInterna> sentenciasInternas = new ArrayList<SentenciaInterna>();
		SentenciaInterna sentenciaInterna;
		// Leer token
		Token t = lex.getToken();
		// Obtener sentencias cuerpo
		while ((sentenciaInterna = sentenciaInterna(t)) != null) {
			sentenciasInternas.add(sentenciaInterna);
			// Leer token
			t = lex.getToken();
			// Si es fin de sentencia cuerpo, se recupera el token anterior y se finaliza
			// este bucle
			if (finSentenciaCuerpo(t)) {
				lex.returnLastToken();
				break;
			}
		}
		return sentenciasInternas;
	}

	/**
	 * Parsear una de las sentencias internas
	 * 
	 * @param t
	 * @return
	 */
	private SentenciaInterna sentenciaInterna(Token t) {
		switch (t.token) {
		case PALABRA:
			// Recuperar token anterior, necesario para procesar la primera palabra
			// encontrada
			lex.returnLastToken();
			return texto();
		case B:
			return negrita();
		case I:
			return cursiva();
		case U:
			return underline();
		case A:
			return enlace();
		default:
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba texto, '<b>', '<i>', '<u>' o '<a'.",
					t.getLine());
			return null;
		}
	}

	/**
	 * Parsear texto
	 * 
	 * @return
	 */
	private Texto texto() {
		List<Palabra> palabras = new ArrayList<Palabra>();
		// Leer token
		Token t = lex.getToken();
		while (t.token == TokensId.PALABRA) {
			palabras.add(new Palabra(t.lexeme));
			// Leer siguiente token
			t = lex.getToken();
		}
		// Volver al token anterior
		lex.returnLastToken();
		return new Texto(palabras);
	}

	/**
	 * Parsear negrita
	 * 
	 * @return
	 */
	private Negrita negrita() {
		// Parsear texto
		Texto texto = texto();
		// Leer token
		Token t = lex.getToken();
		// Se espera FB
		if (t.token != TokensId.FB)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '</b>'", t.getLine());
		return error ? null : new Negrita(texto);
	}

	/**
	 * Parsear cursiva
	 * 
	 * @return
	 */
	private Cursiva cursiva() {
		// Parsear texto
		Texto texto = texto();
		// Leer token
		Token t = lex.getToken();
		// Se espera FI
		if (t.token != TokensId.FI)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '</i>'", t.getLine());
		return error ? null : new Cursiva(texto);
	}

	/**
	 * Parsear underline
	 * 
	 * @return
	 */
	private Underline underline() {
		// Parsear texto
		Texto texto = texto();
		// Leer token
		Token t = lex.getToken();
		// Se espera FU
		if (t.token != TokensId.FU)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '</u>'", t.getLine());
		return error ? null : new Underline(texto);
	}

	/**
	 * Parsear enlace
	 * 
	 * @return
	 */
	private Enlace enlace() {
		// Leer token
		Token t = lex.getToken();
		// Se espera HREF
		if (t.token != TokensId.HREF)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba el atributo 'href'", t.getLine());
		// Leer token
		t = lex.getToken();
		// Se espera EQ
		if (t.token != TokensId.EQ)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba '='", t.getLine());
		// Obtener HREF
		String href = cadena();
		// Leer token
		t = lex.getToken();
		// Se espera MAYOR
		if (t.token != TokensId.MAYOR)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba '>'", t.getLine());
		// Parsear texto
		Texto texto = texto();
		// Leer token
		t = lex.getToken();
		// Se espera FA
		if (t.token != TokensId.FA)
			errorSintactico("Encontrado '" + t.getLexeme() + "'. Se esperaba la etiqueta '</a>'", t.getLine());
		return error ? null : new Enlace(href, texto);
	}

}
