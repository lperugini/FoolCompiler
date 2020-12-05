// Generated from FOOL.g4 by ANTLR 4.4
package compiler;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FOOLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PLUS=1, MINUS=2, TIMES=3, LPAR=4, RPAR=5, CLPAR=6, CRPAR=7, SEMIC=8, COLON=9, 
		COMMA=10, EQ=11, ASS=12, TRUE=13, FALSE=14, IF=15, THEN=16, ELSE=17, PRINT=18, 
		LET=19, IN=20, VAR=21, FUN=22, INT=23, BOOL=24, NUM=25, ID=26, WHITESP=27, 
		COMMENT=28, ERR=29;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"'\\u0000'", "'\\u0001'", "'\\u0002'", "'\\u0003'", "'\\u0004'", "'\\u0005'", 
		"'\\u0006'", "'\\u0007'", "'\b'", "'\t'", "'\n'", "'\\u000B'", "'\f'", 
		"'\r'", "'\\u000E'", "'\\u000F'", "'\\u0010'", "'\\u0011'", "'\\u0012'", 
		"'\\u0013'", "'\\u0014'", "'\\u0015'", "'\\u0016'", "'\\u0017'", "'\\u0018'", 
		"'\\u0019'", "'\\u001A'", "'\\u001B'", "'\\u001C'", "'\\u001D'"
	};
	public static final String[] ruleNames = {
		"PLUS", "MINUS", "TIMES", "LPAR", "RPAR", "CLPAR", "CRPAR", "SEMIC", "COLON", 
		"COMMA", "EQ", "ASS", "TRUE", "FALSE", "IF", "THEN", "ELSE", "PRINT", 
		"LET", "IN", "VAR", "FUN", "INT", "BOOL", "NUM", "ID", "WHITESP", "COMMENT", 
		"ERR"
	};


	public int lexicalErrors=0;


	public FOOLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FOOL.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 28: ERR_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void ERR_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:  System.out.println("Invalid char "+getText()+" at line "+getLine()); lexicalErrors++;  break;
		}
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\37\u00b7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\3\2\3\2\3\3\3"+
		"\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3"+
		"\f\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3"+
		"\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3"+
		"\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3"+
		"\31\3\31\3\32\3\32\3\32\7\32\u0090\n\32\f\32\16\32\u0093\13\32\5\32\u0095"+
		"\n\32\3\33\3\33\7\33\u0099\n\33\f\33\16\33\u009c\13\33\3\34\6\34\u009f"+
		"\n\34\r\34\16\34\u00a0\3\34\3\34\3\35\3\35\3\35\3\35\7\35\u00a9\n\35\f"+
		"\35\16\35\u00ac\13\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36"+
		"\3\u00aa\2\37\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34"+
		"\67\359\36;\37\3\2\5\4\2C\\c|\5\2\62;C\\c|\5\2\13\f\17\17\"\"\u00bb\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2"+
		"\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2"+
		"\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2"+
		"\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2"+
		"\2\2\3=\3\2\2\2\5?\3\2\2\2\7A\3\2\2\2\tC\3\2\2\2\13E\3\2\2\2\rG\3\2\2"+
		"\2\17I\3\2\2\2\21K\3\2\2\2\23M\3\2\2\2\25O\3\2\2\2\27Q\3\2\2\2\31T\3\2"+
		"\2\2\33V\3\2\2\2\35[\3\2\2\2\37a\3\2\2\2!d\3\2\2\2#i\3\2\2\2%n\3\2\2\2"+
		"\'t\3\2\2\2)x\3\2\2\2+{\3\2\2\2-\177\3\2\2\2/\u0083\3\2\2\2\61\u0087\3"+
		"\2\2\2\63\u0094\3\2\2\2\65\u0096\3\2\2\2\67\u009e\3\2\2\29\u00a4\3\2\2"+
		"\2;\u00b2\3\2\2\2=>\7-\2\2>\4\3\2\2\2?@\7/\2\2@\6\3\2\2\2AB\7,\2\2B\b"+
		"\3\2\2\2CD\7*\2\2D\n\3\2\2\2EF\7+\2\2F\f\3\2\2\2GH\7}\2\2H\16\3\2\2\2"+
		"IJ\7\177\2\2J\20\3\2\2\2KL\7=\2\2L\22\3\2\2\2MN\7<\2\2N\24\3\2\2\2OP\7"+
		".\2\2P\26\3\2\2\2QR\7?\2\2RS\7?\2\2S\30\3\2\2\2TU\7?\2\2U\32\3\2\2\2V"+
		"W\7v\2\2WX\7t\2\2XY\7w\2\2YZ\7g\2\2Z\34\3\2\2\2[\\\7h\2\2\\]\7c\2\2]^"+
		"\7n\2\2^_\7u\2\2_`\7g\2\2`\36\3\2\2\2ab\7k\2\2bc\7h\2\2c \3\2\2\2de\7"+
		"v\2\2ef\7j\2\2fg\7g\2\2gh\7p\2\2h\"\3\2\2\2ij\7g\2\2jk\7n\2\2kl\7u\2\2"+
		"lm\7g\2\2m$\3\2\2\2no\7r\2\2op\7t\2\2pq\7k\2\2qr\7p\2\2rs\7v\2\2s&\3\2"+
		"\2\2tu\7n\2\2uv\7g\2\2vw\7v\2\2w(\3\2\2\2xy\7k\2\2yz\7p\2\2z*\3\2\2\2"+
		"{|\7x\2\2|}\7c\2\2}~\7t\2\2~,\3\2\2\2\177\u0080\7h\2\2\u0080\u0081\7w"+
		"\2\2\u0081\u0082\7p\2\2\u0082.\3\2\2\2\u0083\u0084\7k\2\2\u0084\u0085"+
		"\7p\2\2\u0085\u0086\7v\2\2\u0086\60\3\2\2\2\u0087\u0088\7d\2\2\u0088\u0089"+
		"\7q\2\2\u0089\u008a\7q\2\2\u008a\u008b\7n\2\2\u008b\62\3\2\2\2\u008c\u0095"+
		"\7\62\2\2\u008d\u0091\4\63;\2\u008e\u0090\4\62;\2\u008f\u008e\3\2\2\2"+
		"\u0090\u0093\3\2\2\2\u0091\u008f\3\2\2\2\u0091\u0092\3\2\2\2\u0092\u0095"+
		"\3\2\2\2\u0093\u0091\3\2\2\2\u0094\u008c\3\2\2\2\u0094\u008d\3\2\2\2\u0095"+
		"\64\3\2\2\2\u0096\u009a\t\2\2\2\u0097\u0099\t\3\2\2\u0098\u0097\3\2\2"+
		"\2\u0099\u009c\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u009b\3\2\2\2\u009b\66"+
		"\3\2\2\2\u009c\u009a\3\2\2\2\u009d\u009f\t\4\2\2\u009e\u009d\3\2\2\2\u009f"+
		"\u00a0\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a2\3\2"+
		"\2\2\u00a2\u00a3\b\34\2\2\u00a38\3\2\2\2\u00a4\u00a5\7\61\2\2\u00a5\u00a6"+
		"\7,\2\2\u00a6\u00aa\3\2\2\2\u00a7\u00a9\13\2\2\2\u00a8\u00a7\3\2\2\2\u00a9"+
		"\u00ac\3\2\2\2\u00aa\u00ab\3\2\2\2\u00aa\u00a8\3\2\2\2\u00ab\u00ad\3\2"+
		"\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00ae\7,\2\2\u00ae\u00af\7\61\2\2\u00af"+
		"\u00b0\3\2\2\2\u00b0\u00b1\b\35\2\2\u00b1:\3\2\2\2\u00b2\u00b3\13\2\2"+
		"\2\u00b3\u00b4\b\36\3\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\b\36\2\2\u00b6"+
		"<\3\2\2\2\b\2\u0091\u0094\u009a\u00a0\u00aa\4\2\3\2\3\36\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}