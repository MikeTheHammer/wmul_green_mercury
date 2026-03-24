// Generated from GreenMercuryLexer.g4 by ANTLR 4.13.2
package edu.marshall.wmul.green_mercury.antlr;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class GreenMercuryLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		OPEN=1, TEXT=2, SEA_WS=3, CLOSE=4, SLASH_CLOSE=5, EQUALS=6, STRING=7, 
		SLASHNAME=8, NAME=9, S=10;
	public static final int
		INSIDE=1;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "INSIDE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"OPEN", "TEXT", "SEA_WS", "CLOSE", "SLASH_CLOSE", "EQUALS", "STRING", 
			"SLASHNAME", "NAME", "S", "HEXDIGIT", "ALPHA", "DIGIT", "EOLF", "NameChar", 
			"NameStartChar"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, "'/!!'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "OPEN", "TEXT", "SEA_WS", "CLOSE", "SLASH_CLOSE", "EQUALS", "STRING", 
			"SLASHNAME", "NAME", "S"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public GreenMercuryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "GreenMercuryLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\nv\u0006\uffff\uffff\u0006\uffff\uffff\u0002\u0000\u0007"+
		"\u0000\u0002\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007"+
		"\u0003\u0002\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007"+
		"\u0006\u0002\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n"+
		"\u0007\n\u0002\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002"+
		"\u000e\u0007\u000e\u0002\u000f\u0007\u000f\u0001\u0000\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0004\u0001)\b\u0001\u000b"+
		"\u0001\f\u0001*\u0001\u0002\u0001\u0002\u0003\u0002/\b\u0002\u0001\u0002"+
		"\u0004\u00022\b\u0002\u000b\u0002\f\u00023\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001"+
		"\u0006\u0005\u0006E\b\u0006\n\u0006\f\u0006H\t\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0005\u0006M\b\u0006\n\u0006\f\u0006P\t\u0006\u0001"+
		"\u0006\u0003\u0006S\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0005\bZ\b\b\n\b\f\b]\t\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0003\rj\b"+
		"\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003"+
		"\u000er\b\u000e\u0001\u000f\u0003\u000fu\b\u000f\u0001*\u0000\u0010\u0002"+
		"\u0001\u0004\u0002\u0006\u0003\b\u0004\n\u0005\f\u0006\u000e\u0007\u0010"+
		"\b\u0012\t\u0014\n\u0016\u0000\u0018\u0000\u001a\u0000\u001c\u0000\u001e"+
		"\u0000 \u0000\u0002\u0000\u0001\t\u0002\u0000\t\t  \u0002\u0000\"\"<<"+
		"\u0002\u0000\'\'<<\u0003\u0000\t\n\r\r  \u0003\u000009AFaf\u0002\u0000"+
		"AZaz\u0001\u000009\u0003\u0000\u00b7\u00b7\u0300\u036f\u203f\u2040\t\u0000"+
		"::AZ__az\u2070\u218f\u2c00\u2fef\u3001\u8000\ud7ff\u8000\uf900\u8000\ufdcf"+
		"\u8000\ufdf0\u8000\ufffdz\u0000\u0002\u0001\u0000\u0000\u0000\u0000\u0004"+
		"\u0001\u0000\u0000\u0000\u0000\u0006\u0001\u0000\u0000\u0000\u0001\b\u0001"+
		"\u0000\u0000\u0000\u0001\n\u0001\u0000\u0000\u0000\u0001\f\u0001\u0000"+
		"\u0000\u0000\u0001\u000e\u0001\u0000\u0000\u0000\u0001\u0010\u0001\u0000"+
		"\u0000\u0000\u0001\u0012\u0001\u0000\u0000\u0000\u0001\u0014\u0001\u0000"+
		"\u0000\u0000\u0002\"\u0001\u0000\u0000\u0000\u0004(\u0001\u0000\u0000"+
		"\u0000\u00061\u0001\u0000\u0000\u0000\b5\u0001\u0000\u0000\u0000\n:\u0001"+
		"\u0000\u0000\u0000\f@\u0001\u0000\u0000\u0000\u000eR\u0001\u0000\u0000"+
		"\u0000\u0010T\u0001\u0000\u0000\u0000\u0012W\u0001\u0000\u0000\u0000\u0014"+
		"^\u0001\u0000\u0000\u0000\u0016b\u0001\u0000\u0000\u0000\u0018d\u0001"+
		"\u0000\u0000\u0000\u001af\u0001\u0000\u0000\u0000\u001ci\u0001\u0000\u0000"+
		"\u0000\u001eq\u0001\u0000\u0000\u0000 t\u0001\u0000\u0000\u0000\"#\u0005"+
		"!\u0000\u0000#$\u0005!\u0000\u0000$%\u0001\u0000\u0000\u0000%&\u0006\u0000"+
		"\u0000\u0000&\u0003\u0001\u0000\u0000\u0000\')\t\u0000\u0000\u0000(\'"+
		"\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000\u0000*+\u0001\u0000\u0000"+
		"\u0000*(\u0001\u0000\u0000\u0000+\u0005\u0001\u0000\u0000\u0000,2\u0007"+
		"\u0000\u0000\u0000-/\u0005\r\u0000\u0000.-\u0001\u0000\u0000\u0000./\u0001"+
		"\u0000\u0000\u0000/0\u0001\u0000\u0000\u000002\u0005\n\u0000\u00001,\u0001"+
		"\u0000\u0000\u00001.\u0001\u0000\u0000\u000023\u0001\u0000\u0000\u0000"+
		"31\u0001\u0000\u0000\u000034\u0001\u0000\u0000\u00004\u0007\u0001\u0000"+
		"\u0000\u000056\u0005!\u0000\u000067\u0005!\u0000\u000078\u0001\u0000\u0000"+
		"\u000089\u0006\u0003\u0001\u00009\t\u0001\u0000\u0000\u0000:;\u0005/\u0000"+
		"\u0000;<\u0005!\u0000\u0000<=\u0005!\u0000\u0000=>\u0001\u0000\u0000\u0000"+
		">?\u0006\u0004\u0001\u0000?\u000b\u0001\u0000\u0000\u0000@A\u0005=\u0000"+
		"\u0000A\r\u0001\u0000\u0000\u0000BF\u0005\"\u0000\u0000CE\b\u0001\u0000"+
		"\u0000DC\u0001\u0000\u0000\u0000EH\u0001\u0000\u0000\u0000FD\u0001\u0000"+
		"\u0000\u0000FG\u0001\u0000\u0000\u0000GI\u0001\u0000\u0000\u0000HF\u0001"+
		"\u0000\u0000\u0000IS\u0005\"\u0000\u0000JN\u0005\'\u0000\u0000KM\b\u0002"+
		"\u0000\u0000LK\u0001\u0000\u0000\u0000MP\u0001\u0000\u0000\u0000NL\u0001"+
		"\u0000\u0000\u0000NO\u0001\u0000\u0000\u0000OQ\u0001\u0000\u0000\u0000"+
		"PN\u0001\u0000\u0000\u0000QS\u0005\'\u0000\u0000RB\u0001\u0000\u0000\u0000"+
		"RJ\u0001\u0000\u0000\u0000S\u000f\u0001\u0000\u0000\u0000TU\u0005/\u0000"+
		"\u0000UV\u0003\u0012\b\u0000V\u0011\u0001\u0000\u0000\u0000W[\u0003 \u000f"+
		"\u0000XZ\u0003\u001e\u000e\u0000YX\u0001\u0000\u0000\u0000Z]\u0001\u0000"+
		"\u0000\u0000[Y\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\\u0013"+
		"\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000^_\u0007\u0003\u0000"+
		"\u0000_`\u0001\u0000\u0000\u0000`a\u0006\t\u0002\u0000a\u0015\u0001\u0000"+
		"\u0000\u0000bc\u0007\u0004\u0000\u0000c\u0017\u0001\u0000\u0000\u0000"+
		"de\u0007\u0005\u0000\u0000e\u0019\u0001\u0000\u0000\u0000fg\u0007\u0006"+
		"\u0000\u0000g\u001b\u0001\u0000\u0000\u0000hj\u0005\r\u0000\u0000ih\u0001"+
		"\u0000\u0000\u0000ij\u0001\u0000\u0000\u0000jk\u0001\u0000\u0000\u0000"+
		"kl\u0005\n\u0000\u0000l\u001d\u0001\u0000\u0000\u0000mr\u0003 \u000f\u0000"+
		"nr\u0002-.\u0000or\u0003\u001a\f\u0000pr\u0007\u0007\u0000\u0000qm\u0001"+
		"\u0000\u0000\u0000qn\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000\u0000"+
		"qp\u0001\u0000\u0000\u0000r\u001f\u0001\u0000\u0000\u0000su\u0007\b\u0000"+
		"\u0000ts\u0001\u0000\u0000\u0000u!\u0001\u0000\u0000\u0000\r\u0000\u0001"+
		"*.13FNR[iqt\u0003\u0005\u0001\u0000\u0004\u0000\u0000\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}