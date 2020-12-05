// Generated from FOOL.g4 by ANTLR 4.4
package compiler;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FOOLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PLUS=1, MINUS=2, TIMES=3, LPAR=4, RPAR=5, CLPAR=6, CRPAR=7, SEMIC=8, COLON=9, 
		COMMA=10, EQ=11, ASS=12, TRUE=13, FALSE=14, IF=15, THEN=16, ELSE=17, PRINT=18, 
		LET=19, IN=20, VAR=21, FUN=22, INT=23, BOOL=24, NUM=25, ID=26, WHITESP=27, 
		COMMENT=28, ERR=29;
	public static final String[] tokenNames = {
		"<INVALID>", "'+'", "'-'", "'*'", "'('", "')'", "'{'", "'}'", "';'", "':'", 
		"','", "'=='", "'='", "'true'", "'false'", "'if'", "'then'", "'else'", 
		"'print'", "'let'", "'in'", "'var'", "'fun'", "'int'", "'bool'", "NUM", 
		"ID", "WHITESP", "COMMENT", "ERR"
	};
	public static final int
		RULE_prog = 0, RULE_progbody = 1, RULE_dec = 2, RULE_exp = 3, RULE_type = 4;
	public static final String[] ruleNames = {
		"prog", "progbody", "dec", "exp", "type"
	};

	@Override
	public String getGrammarFileName() { return "FOOL.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public FOOLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(FOOLParser.EOF, 0); }
		public ProgbodyContext progbody() {
			return getRuleContext(ProgbodyContext.class,0);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(10); progbody();
			setState(11); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProgbodyContext extends ParserRuleContext {
		public ProgbodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_progbody; }
	 
		public ProgbodyContext() { }
		public void copyFrom(ProgbodyContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class LetInProgContext extends ProgbodyContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<DecContext> dec() {
			return getRuleContexts(DecContext.class);
		}
		public TerminalNode SEMIC() { return getToken(FOOLParser.SEMIC, 0); }
		public TerminalNode LET() { return getToken(FOOLParser.LET, 0); }
		public DecContext dec(int i) {
			return getRuleContext(DecContext.class,i);
		}
		public TerminalNode IN() { return getToken(FOOLParser.IN, 0); }
		public LetInProgContext(ProgbodyContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitLetInProg(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NoDecProgContext extends ProgbodyContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode SEMIC() { return getToken(FOOLParser.SEMIC, 0); }
		public NoDecProgContext(ProgbodyContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitNoDecProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgbodyContext progbody() throws RecognitionException {
		ProgbodyContext _localctx = new ProgbodyContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_progbody);
		int _la;
		try {
			setState(26);
			switch (_input.LA(1)) {
			case LET:
				_localctx = new LetInProgContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(13); match(LET);
				setState(15); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(14); dec();
					}
					}
					setState(17); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==VAR || _la==FUN );
				setState(19); match(IN);
				setState(20); exp(0);
				setState(21); match(SEMIC);
				}
				break;
			case MINUS:
			case LPAR:
			case TRUE:
			case FALSE:
			case IF:
			case PRINT:
			case NUM:
			case ID:
				_localctx = new NoDecProgContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(23); exp(0);
				setState(24); match(SEMIC);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DecContext extends ParserRuleContext {
		public DecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dec; }
	 
		public DecContext() { }
		public void copyFrom(DecContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FundecContext extends DecContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public List<DecContext> dec() {
			return getRuleContexts(DecContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(FOOLParser.COLON); }
		public TerminalNode ID(int i) {
			return getToken(FOOLParser.ID, i);
		}
		public TerminalNode LPAR() { return getToken(FOOLParser.LPAR, 0); }
		public DecContext dec(int i) {
			return getRuleContext(DecContext.class,i);
		}
		public TerminalNode IN() { return getToken(FOOLParser.IN, 0); }
		public TerminalNode COMMA(int i) {
			return getToken(FOOLParser.COMMA, i);
		}
		public List<TerminalNode> ID() { return getTokens(FOOLParser.ID); }
		public TerminalNode SEMIC() { return getToken(FOOLParser.SEMIC, 0); }
		public TerminalNode COLON(int i) {
			return getToken(FOOLParser.COLON, i);
		}
		public List<TerminalNode> COMMA() { return getTokens(FOOLParser.COMMA); }
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TerminalNode RPAR() { return getToken(FOOLParser.RPAR, 0); }
		public TerminalNode FUN() { return getToken(FOOLParser.FUN, 0); }
		public TerminalNode LET() { return getToken(FOOLParser.LET, 0); }
		public FundecContext(DecContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitFundec(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VardecContext extends DecContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode ID() { return getToken(FOOLParser.ID, 0); }
		public TerminalNode VAR() { return getToken(FOOLParser.VAR, 0); }
		public TerminalNode SEMIC() { return getToken(FOOLParser.SEMIC, 0); }
		public TerminalNode COLON() { return getToken(FOOLParser.COLON, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode ASS() { return getToken(FOOLParser.ASS, 0); }
		public VardecContext(DecContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitVardec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DecContext dec() throws RecognitionException {
		DecContext _localctx = new DecContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_dec);
		int _la;
		try {
			setState(69);
			switch (_input.LA(1)) {
			case VAR:
				_localctx = new VardecContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(28); match(VAR);
				setState(29); match(ID);
				setState(30); match(COLON);
				setState(31); type();
				setState(32); match(ASS);
				setState(33); exp(0);
				setState(34); match(SEMIC);
				}
				break;
			case FUN:
				_localctx = new FundecContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(36); match(FUN);
				setState(37); match(ID);
				setState(38); match(COLON);
				setState(39); type();
				setState(40); match(LPAR);
				setState(53);
				_la = _input.LA(1);
				if (_la==ID) {
					{
					setState(41); match(ID);
					setState(42); match(COLON);
					setState(43); type();
					setState(50);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(44); match(COMMA);
						setState(45); match(ID);
						setState(46); match(COLON);
						setState(47); type();
						}
						}
						setState(52);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(55); match(RPAR);
				setState(64);
				_la = _input.LA(1);
				if (_la==LET) {
					{
					setState(56); match(LET);
					setState(58); 
					_errHandler.sync(this);
					_la = _input.LA(1);
					do {
						{
						{
						setState(57); dec();
						}
						}
						setState(60); 
						_errHandler.sync(this);
						_la = _input.LA(1);
					} while ( _la==VAR || _la==FUN );
					setState(62); match(IN);
					}
				}

				setState(66); exp(0);
				setState(67); match(SEMIC);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpContext extends ParserRuleContext {
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
	 
		public ExpContext() { }
		public void copyFrom(ExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CallContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public TerminalNode ID() { return getToken(FOOLParser.ID, 0); }
		public List<TerminalNode> COMMA() { return getTokens(FOOLParser.COMMA); }
		public TerminalNode LPAR() { return getToken(FOOLParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(FOOLParser.RPAR, 0); }
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode COMMA(int i) {
			return getToken(FOOLParser.COMMA, i);
		}
		public CallContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PrintContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode PRINT() { return getToken(FOOLParser.PRINT, 0); }
		public TerminalNode LPAR() { return getToken(FOOLParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(FOOLParser.RPAR, 0); }
		public PrintContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TimesContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public TerminalNode TIMES() { return getToken(FOOLParser.TIMES, 0); }
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TimesContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitTimes(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParsContext extends ExpContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode LPAR() { return getToken(FOOLParser.LPAR, 0); }
		public TerminalNode RPAR() { return getToken(FOOLParser.RPAR, 0); }
		public ParsContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitPars(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TrueContext extends ExpContext {
		public TerminalNode TRUE() { return getToken(FOOLParser.TRUE, 0); }
		public TrueContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitTrue(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FalseContext extends ExpContext {
		public TerminalNode FALSE() { return getToken(FOOLParser.FALSE, 0); }
		public FalseContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitFalse(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntegerContext extends ExpContext {
		public TerminalNode NUM() { return getToken(FOOLParser.NUM, 0); }
		public TerminalNode MINUS() { return getToken(FOOLParser.MINUS, 0); }
		public IntegerContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitInteger(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IdContext extends ExpContext {
		public TerminalNode ID() { return getToken(FOOLParser.ID, 0); }
		public IdContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class EqContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public TerminalNode EQ() { return getToken(FOOLParser.EQ, 0); }
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public EqContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitEq(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IfContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public TerminalNode ELSE() { return getToken(FOOLParser.ELSE, 0); }
		public TerminalNode IF() { return getToken(FOOLParser.IF, 0); }
		public TerminalNode CLPAR(int i) {
			return getToken(FOOLParser.CLPAR, i);
		}
		public List<TerminalNode> CRPAR() { return getTokens(FOOLParser.CRPAR); }
		public TerminalNode CRPAR(int i) {
			return getToken(FOOLParser.CRPAR, i);
		}
		public TerminalNode THEN() { return getToken(FOOLParser.THEN, 0); }
		public List<TerminalNode> CLPAR() { return getTokens(FOOLParser.CLPAR); }
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public IfContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitIf(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PlusContext extends ExpContext {
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public TerminalNode PLUS() { return getToken(FOOLParser.PLUS, 0); }
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public PlusContext(ExpContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitPlus(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		return exp(0);
	}

	private ExpContext exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpContext _localctx = new ExpContext(_ctx, _parentState);
		ExpContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_exp, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				{
				_localctx = new ParsContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(72); match(LPAR);
				setState(73); exp(0);
				setState(74); match(RPAR);
				}
				break;
			case 2:
				{
				_localctx = new IntegerContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(77);
				_la = _input.LA(1);
				if (_la==MINUS) {
					{
					setState(76); match(MINUS);
					}
				}

				setState(79); match(NUM);
				}
				break;
			case 3:
				{
				_localctx = new TrueContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(80); match(TRUE);
				}
				break;
			case 4:
				{
				_localctx = new FalseContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(81); match(FALSE);
				}
				break;
			case 5:
				{
				_localctx = new IfContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(82); match(IF);
				setState(83); exp(0);
				setState(84); match(THEN);
				setState(85); match(CLPAR);
				setState(86); exp(0);
				setState(87); match(CRPAR);
				setState(88); match(ELSE);
				setState(89); match(CLPAR);
				setState(90); exp(0);
				setState(91); match(CRPAR);
				}
				break;
			case 6:
				{
				_localctx = new PrintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(93); match(PRINT);
				setState(94); match(LPAR);
				setState(95); exp(0);
				setState(96); match(RPAR);
				}
				break;
			case 7:
				{
				_localctx = new IdContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(98); match(ID);
				}
				break;
			case 8:
				{
				_localctx = new CallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(99); match(ID);
				setState(100); match(LPAR);
				setState(109);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MINUS) | (1L << LPAR) | (1L << TRUE) | (1L << FALSE) | (1L << IF) | (1L << PRINT) | (1L << NUM) | (1L << ID))) != 0)) {
					{
					setState(101); exp(0);
					setState(106);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(102); match(COMMA);
						setState(103); exp(0);
						}
						}
						setState(108);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(111); match(RPAR);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(125);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(123);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new TimesContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(114);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(115); match(TIMES);
						setState(116); exp(12);
						}
						break;
					case 2:
						{
						_localctx = new PlusContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(117);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(118); match(PLUS);
						setState(119); exp(11);
						}
						break;
					case 3:
						{
						_localctx = new EqContext(new ExpContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(120);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(121); match(EQ);
						setState(122); exp(10);
						}
						break;
					}
					} 
				}
				setState(127);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IntTypeContext extends TypeContext {
		public TerminalNode INT() { return getToken(FOOLParser.INT, 0); }
		public IntTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitIntType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolTypeContext extends TypeContext {
		public TerminalNode BOOL() { return getToken(FOOLParser.BOOL, 0); }
		public BoolTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof FOOLVisitor ) return ((FOOLVisitor<? extends T>)visitor).visitBoolType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_type);
		try {
			setState(130);
			switch (_input.LA(1)) {
			case INT:
				_localctx = new IntTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(128); match(INT);
				}
				break;
			case BOOL:
				_localctx = new BoolTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(129); match(BOOL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 3: return exp_sempred((ExpContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 11);
		case 1: return precpred(_ctx, 10);
		case 2: return precpred(_ctx, 9);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\37\u0087\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\3\3\3\3\6\3\22\n\3\r\3\16"+
		"\3\23\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\35\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\7\4\63\n\4\f\4"+
		"\16\4\66\13\4\5\48\n\4\3\4\3\4\3\4\6\4=\n\4\r\4\16\4>\3\4\3\4\5\4C\n\4"+
		"\3\4\3\4\3\4\5\4H\n\4\3\5\3\5\3\5\3\5\3\5\3\5\5\5P\n\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\7\5k\n\5\f\5\16\5n\13\5\5\5p\n\5\3\5\5\5s\n\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\5\3\5\7\5~\n\5\f\5\16\5\u0081\13\5\3\6\3\6\5\6\u0085"+
		"\n\6\3\6\2\3\b\7\2\4\6\b\n\2\2\u0096\2\f\3\2\2\2\4\34\3\2\2\2\6G\3\2\2"+
		"\2\br\3\2\2\2\n\u0084\3\2\2\2\f\r\5\4\3\2\r\16\7\2\2\3\16\3\3\2\2\2\17"+
		"\21\7\25\2\2\20\22\5\6\4\2\21\20\3\2\2\2\22\23\3\2\2\2\23\21\3\2\2\2\23"+
		"\24\3\2\2\2\24\25\3\2\2\2\25\26\7\26\2\2\26\27\5\b\5\2\27\30\7\n\2\2\30"+
		"\35\3\2\2\2\31\32\5\b\5\2\32\33\7\n\2\2\33\35\3\2\2\2\34\17\3\2\2\2\34"+
		"\31\3\2\2\2\35\5\3\2\2\2\36\37\7\27\2\2\37 \7\34\2\2 !\7\13\2\2!\"\5\n"+
		"\6\2\"#\7\16\2\2#$\5\b\5\2$%\7\n\2\2%H\3\2\2\2&\'\7\30\2\2\'(\7\34\2\2"+
		"()\7\13\2\2)*\5\n\6\2*\67\7\6\2\2+,\7\34\2\2,-\7\13\2\2-\64\5\n\6\2./"+
		"\7\f\2\2/\60\7\34\2\2\60\61\7\13\2\2\61\63\5\n\6\2\62.\3\2\2\2\63\66\3"+
		"\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\658\3\2\2\2\66\64\3\2\2\2\67+\3\2\2"+
		"\2\678\3\2\2\289\3\2\2\29B\7\7\2\2:<\7\25\2\2;=\5\6\4\2<;\3\2\2\2=>\3"+
		"\2\2\2><\3\2\2\2>?\3\2\2\2?@\3\2\2\2@A\7\26\2\2AC\3\2\2\2B:\3\2\2\2BC"+
		"\3\2\2\2CD\3\2\2\2DE\5\b\5\2EF\7\n\2\2FH\3\2\2\2G\36\3\2\2\2G&\3\2\2\2"+
		"H\7\3\2\2\2IJ\b\5\1\2JK\7\6\2\2KL\5\b\5\2LM\7\7\2\2Ms\3\2\2\2NP\7\4\2"+
		"\2ON\3\2\2\2OP\3\2\2\2PQ\3\2\2\2Qs\7\33\2\2Rs\7\17\2\2Ss\7\20\2\2TU\7"+
		"\21\2\2UV\5\b\5\2VW\7\22\2\2WX\7\b\2\2XY\5\b\5\2YZ\7\t\2\2Z[\7\23\2\2"+
		"[\\\7\b\2\2\\]\5\b\5\2]^\7\t\2\2^s\3\2\2\2_`\7\24\2\2`a\7\6\2\2ab\5\b"+
		"\5\2bc\7\7\2\2cs\3\2\2\2ds\7\34\2\2ef\7\34\2\2fo\7\6\2\2gl\5\b\5\2hi\7"+
		"\f\2\2ik\5\b\5\2jh\3\2\2\2kn\3\2\2\2lj\3\2\2\2lm\3\2\2\2mp\3\2\2\2nl\3"+
		"\2\2\2og\3\2\2\2op\3\2\2\2pq\3\2\2\2qs\7\7\2\2rI\3\2\2\2rO\3\2\2\2rR\3"+
		"\2\2\2rS\3\2\2\2rT\3\2\2\2r_\3\2\2\2rd\3\2\2\2re\3\2\2\2s\177\3\2\2\2"+
		"tu\f\r\2\2uv\7\5\2\2v~\5\b\5\16wx\f\f\2\2xy\7\3\2\2y~\5\b\5\rz{\f\13\2"+
		"\2{|\7\r\2\2|~\5\b\5\f}t\3\2\2\2}w\3\2\2\2}z\3\2\2\2~\u0081\3\2\2\2\177"+
		"}\3\2\2\2\177\u0080\3\2\2\2\u0080\t\3\2\2\2\u0081\177\3\2\2\2\u0082\u0085"+
		"\7\31\2\2\u0083\u0085\7\32\2\2\u0084\u0082\3\2\2\2\u0084\u0083\3\2\2\2"+
		"\u0085\13\3\2\2\2\20\23\34\64\67>BGOlor}\177\u0084";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}