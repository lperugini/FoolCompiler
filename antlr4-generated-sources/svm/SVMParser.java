// Generated from SVM.g4 by ANTLR 4.4
package svm;

import java.util.HashMap;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SVMParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PUSH=1, POP=2, ADD=3, SUB=4, MULT=5, DIV=6, STOREW=7, LOADW=8, BRANCH=9, 
		BRANCHEQ=10, BRANCHLESSEQ=11, JS=12, LOADRA=13, STORERA=14, LOADTM=15, 
		STORETM=16, LOADFP=17, STOREFP=18, COPYFP=19, LOADHP=20, STOREHP=21, PRINT=22, 
		HALT=23, COL=24, LABEL=25, INTEGER=26, WHITESP=27, ERR=28;
	public static final String[] tokenNames = {
		"<INVALID>", "'push'", "'pop'", "'add'", "'sub'", "'mult'", "'div'", "'sw'", 
		"'lw'", "'b'", "'beq'", "'bleq'", "'js'", "'lra'", "'sra'", "'ltm'", "'stm'", 
		"'lfp'", "'sfp'", "'cfp'", "'lhp'", "'shp'", "'print'", "'halt'", "':'", 
		"LABEL", "INTEGER", "WHITESP", "ERR"
	};
	public static final int
		RULE_assembly = 0, RULE_instruction = 1;
	public static final String[] ruleNames = {
		"assembly", "instruction"
	};

	@Override
	public String getGrammarFileName() { return "SVM.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	int[] code = new int[ExecuteVM.CODESIZE];    
	private int i = 0;
		
	private HashMap<String,Integer> labelDef = new HashMap<String,Integer>();
	private HashMap<Integer,String> labelRef = new HashMap<Integer,String>();

	public SVMParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class AssemblyContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(SVMParser.EOF, 0); }
		public InstructionContext instruction(int i) {
			return getRuleContext(InstructionContext.class,i);
		}
		public List<InstructionContext> instruction() {
			return getRuleContexts(InstructionContext.class);
		}
		public AssemblyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assembly; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitAssembly(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssemblyContext assembly() throws RecognitionException {
		AssemblyContext _localctx = new AssemblyContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_assembly);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(7);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PUSH) | (1L << POP) | (1L << ADD) | (1L << SUB) | (1L << MULT) | (1L << DIV) | (1L << STOREW) | (1L << LOADW) | (1L << BRANCH) | (1L << BRANCHEQ) | (1L << BRANCHLESSEQ) | (1L << JS) | (1L << LOADRA) | (1L << STORERA) | (1L << LOADTM) | (1L << STORETM) | (1L << LOADFP) | (1L << STOREFP) | (1L << COPYFP) | (1L << LOADHP) | (1L << STOREHP) | (1L << PRINT) | (1L << HALT) | (1L << LABEL))) != 0)) {
				{
				{
				setState(4); instruction();
				}
				}
				setState(9);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(10); match(EOF);
			 
				for (Integer j: labelRef.keySet())
					code[j]=labelDef.get(labelRef.get(j));	

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

	public static class InstructionContext extends ParserRuleContext {
		public Token n;
		public Token l;
		public TerminalNode INTEGER() { return getToken(SVMParser.INTEGER, 0); }
		public TerminalNode BRANCH() { return getToken(SVMParser.BRANCH, 0); }
		public TerminalNode STOREHP() { return getToken(SVMParser.STOREHP, 0); }
		public TerminalNode LOADRA() { return getToken(SVMParser.LOADRA, 0); }
		public TerminalNode BRANCHLESSEQ() { return getToken(SVMParser.BRANCHLESSEQ, 0); }
		public TerminalNode BRANCHEQ() { return getToken(SVMParser.BRANCHEQ, 0); }
		public TerminalNode STORETM() { return getToken(SVMParser.STORETM, 0); }
		public TerminalNode ADD() { return getToken(SVMParser.ADD, 0); }
		public TerminalNode LOADW() { return getToken(SVMParser.LOADW, 0); }
		public TerminalNode COL() { return getToken(SVMParser.COL, 0); }
		public TerminalNode STOREFP() { return getToken(SVMParser.STOREFP, 0); }
		public TerminalNode DIV() { return getToken(SVMParser.DIV, 0); }
		public TerminalNode PRINT() { return getToken(SVMParser.PRINT, 0); }
		public TerminalNode MULT() { return getToken(SVMParser.MULT, 0); }
		public TerminalNode SUB() { return getToken(SVMParser.SUB, 0); }
		public TerminalNode COPYFP() { return getToken(SVMParser.COPYFP, 0); }
		public TerminalNode STORERA() { return getToken(SVMParser.STORERA, 0); }
		public TerminalNode PUSH() { return getToken(SVMParser.PUSH, 0); }
		public TerminalNode LABEL() { return getToken(SVMParser.LABEL, 0); }
		public TerminalNode POP() { return getToken(SVMParser.POP, 0); }
		public TerminalNode JS() { return getToken(SVMParser.JS, 0); }
		public TerminalNode LOADHP() { return getToken(SVMParser.LOADHP, 0); }
		public TerminalNode STOREW() { return getToken(SVMParser.STOREW, 0); }
		public TerminalNode LOADTM() { return getToken(SVMParser.LOADTM, 0); }
		public TerminalNode LOADFP() { return getToken(SVMParser.LOADFP, 0); }
		public TerminalNode HALT() { return getToken(SVMParser.HALT, 0); }
		public InstructionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instruction; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SVMVisitor ) return ((SVMVisitor<? extends T>)visitor).visitInstruction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstructionContext instruction() throws RecognitionException {
		InstructionContext _localctx = new InstructionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_instruction);
		try {
			setState(69);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(13); match(PUSH);
				setState(14); ((InstructionContext)_localctx).n = match(INTEGER);
				 
				        	code[i++] = PUSH;
				        	code[i++] = Integer.parseInt((((InstructionContext)_localctx).n!=null?((InstructionContext)_localctx).n.getText():null)); 
				        
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(16); match(PUSH);
				setState(17); ((InstructionContext)_localctx).l = match(LABEL);
								
								code[i++] = PUSH;
								labelRef.put(i++, (((InstructionContext)_localctx).l!=null?((InstructionContext)_localctx).l.getText():null));
					  		
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(19); match(POP);
				 code[i++] = POP; 
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(21); match(ADD);
				 code[i++] = ADD; 
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(23); match(SUB);
				 code[i++] = SUB; 
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(25); match(MULT);
				 code[i++] = MULT; 
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(27); match(DIV);
				 code[i++] = DIV; 
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(29); match(STOREW);
				 code[i++] = STOREW; 
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(31); match(LOADW);
				 code[i++] = LOADW; 
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(33); ((InstructionContext)_localctx).l = match(LABEL);
				setState(34); match(COL);

				  			labelDef.put((((InstructionContext)_localctx).l!=null?((InstructionContext)_localctx).l.getText():null),i);
					  	
				}
				break;
			case 11:
				enterOuterAlt(_localctx, 11);
				{
				setState(36); match(BRANCH);
				setState(37); ((InstructionContext)_localctx).l = match(LABEL);

					  		code[i++] = BRANCH;
					  		labelRef.put(i++, (((InstructionContext)_localctx).l!=null?((InstructionContext)_localctx).l.getText():null));
					  	
				}
				break;
			case 12:
				enterOuterAlt(_localctx, 12);
				{
				setState(39); match(BRANCHEQ);
				setState(40); ((InstructionContext)_localctx).l = match(LABEL);

					  		code[i++] = BRANCHEQ;
					  		labelRef.put(i++, (((InstructionContext)_localctx).l!=null?((InstructionContext)_localctx).l.getText():null));
					  	
				}
				break;
			case 13:
				enterOuterAlt(_localctx, 13);
				{
				setState(42); match(BRANCHLESSEQ);
				setState(43); ((InstructionContext)_localctx).l = match(LABEL);

					  		code[i++] = BRANCHLESSEQ;
					  		labelRef.put(i++, (((InstructionContext)_localctx).l!=null?((InstructionContext)_localctx).l.getText():null));
					  	
				}
				break;
			case 14:
				enterOuterAlt(_localctx, 14);
				{
				setState(45); match(JS);
				 code[i++] = JS; 
				}
				break;
			case 15:
				enterOuterAlt(_localctx, 15);
				{
				setState(47); match(LOADRA);
				 code[i++] = LOADRA; 
				}
				break;
			case 16:
				enterOuterAlt(_localctx, 16);
				{
				setState(49); match(STORERA);
				 code[i++] = STORERA; 
				}
				break;
			case 17:
				enterOuterAlt(_localctx, 17);
				{
				setState(51); match(LOADTM);
				 code[i++] = LOADTM; 
				}
				break;
			case 18:
				enterOuterAlt(_localctx, 18);
				{
				setState(53); match(STORETM);
				 code[i++] = STORETM; 
				}
				break;
			case 19:
				enterOuterAlt(_localctx, 19);
				{
				setState(55); match(LOADFP);
				 code[i++] = LOADFP; 
				}
				break;
			case 20:
				enterOuterAlt(_localctx, 20);
				{
				setState(57); match(STOREFP);
				 code[i++] = STOREFP; 
				}
				break;
			case 21:
				enterOuterAlt(_localctx, 21);
				{
				setState(59); match(COPYFP);
				 code[i++] = COPYFP; 
				}
				break;
			case 22:
				enterOuterAlt(_localctx, 22);
				{
				setState(61); match(LOADHP);
				 code[i++] = LOADHP; 
				}
				break;
			case 23:
				enterOuterAlt(_localctx, 23);
				{
				setState(63); match(STOREHP);
				 code[i++] = STOREHP; 
				}
				break;
			case 24:
				enterOuterAlt(_localctx, 24);
				{
				setState(65); match(PRINT);
				 code[i++] = PRINT; 
				}
				break;
			case 25:
				enterOuterAlt(_localctx, 25);
				{
				setState(67); match(HALT);
				 code[i++] = HALT; 
				}
				break;
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

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\36J\4\2\t\2\4\3\t"+
		"\3\3\2\7\2\b\n\2\f\2\16\2\13\13\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3H\n\3"+
		"\3\3\2\2\4\2\4\2\2`\2\t\3\2\2\2\4G\3\2\2\2\6\b\5\4\3\2\7\6\3\2\2\2\b\13"+
		"\3\2\2\2\t\7\3\2\2\2\t\n\3\2\2\2\n\f\3\2\2\2\13\t\3\2\2\2\f\r\7\2\2\3"+
		"\r\16\b\2\1\2\16\3\3\2\2\2\17\20\7\3\2\2\20\21\7\34\2\2\21H\b\3\1\2\22"+
		"\23\7\3\2\2\23\24\7\33\2\2\24H\b\3\1\2\25\26\7\4\2\2\26H\b\3\1\2\27\30"+
		"\7\5\2\2\30H\b\3\1\2\31\32\7\6\2\2\32H\b\3\1\2\33\34\7\7\2\2\34H\b\3\1"+
		"\2\35\36\7\b\2\2\36H\b\3\1\2\37 \7\t\2\2 H\b\3\1\2!\"\7\n\2\2\"H\b\3\1"+
		"\2#$\7\33\2\2$%\7\32\2\2%H\b\3\1\2&\'\7\13\2\2\'(\7\33\2\2(H\b\3\1\2)"+
		"*\7\f\2\2*+\7\33\2\2+H\b\3\1\2,-\7\r\2\2-.\7\33\2\2.H\b\3\1\2/\60\7\16"+
		"\2\2\60H\b\3\1\2\61\62\7\17\2\2\62H\b\3\1\2\63\64\7\20\2\2\64H\b\3\1\2"+
		"\65\66\7\21\2\2\66H\b\3\1\2\678\7\22\2\28H\b\3\1\29:\7\23\2\2:H\b\3\1"+
		"\2;<\7\24\2\2<H\b\3\1\2=>\7\25\2\2>H\b\3\1\2?@\7\26\2\2@H\b\3\1\2AB\7"+
		"\27\2\2BH\b\3\1\2CD\7\30\2\2DH\b\3\1\2EF\7\31\2\2FH\b\3\1\2G\17\3\2\2"+
		"\2G\22\3\2\2\2G\25\3\2\2\2G\27\3\2\2\2G\31\3\2\2\2G\33\3\2\2\2G\35\3\2"+
		"\2\2G\37\3\2\2\2G!\3\2\2\2G#\3\2\2\2G&\3\2\2\2G)\3\2\2\2G,\3\2\2\2G/\3"+
		"\2\2\2G\61\3\2\2\2G\63\3\2\2\2G\65\3\2\2\2G\67\3\2\2\2G9\3\2\2\2G;\3\2"+
		"\2\2G=\3\2\2\2G?\3\2\2\2GA\3\2\2\2GC\3\2\2\2GE\3\2\2\2H\5\3\2\2\2\4\t"+
		"G";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}