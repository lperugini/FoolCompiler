package compiler;

import java.util.*;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import compiler.AST.*;
import compiler.FOOLParser.*;
import compiler.lib.*;
import static compiler.lib.FOOLlib.*;

public class ASTGenerationSTVisitor extends FOOLBaseVisitor<Node> {

	String indent;
	public boolean print;

	ASTGenerationSTVisitor() {
	}

	ASTGenerationSTVisitor(boolean debug) {
		print = debug;
	}

	private void printVarAndProdName(ParserRuleContext ctx) {
		String prefix = "";
		Class<?> ctxClass = ctx.getClass(), parentClass = ctxClass.getSuperclass();
		if (!parentClass.equals(ParserRuleContext.class)) // parentClass is the var context (and not ctxClass itself)
			prefix = lowerizeFirstChar(extractCtxName(parentClass.getName())) + ": production #";
		System.out.println(indent + prefix + lowerizeFirstChar(extractCtxName(ctxClass.getName())));
	}

	@Override
	public Node visit(ParseTree t) {
		if (t == null)
			return null;
		String temp = indent;
		indent = (indent == null) ? "" : indent + "  ";
		Node result = super.visit(t);
		indent = temp;
		return result;
	}

	@Override
	public Node visitProg(ProgContext c) {
		if (print)
			printVarAndProdName(c);
		return visit(c.progbody());
	}

	@Override
	public Node visitLetInProg(LetInProgContext c) {
		if (print)
			printVarAndProdName(c);
		List<DecNode> declist = new ArrayList<>();
		for (CldecContext dec : c.cldec())
			declist.add((DecNode) visit(dec));
		for (DecContext dec : c.dec())
			declist.add((DecNode) visit(dec));
		return new ProgLetInNode(declist, visit(c.exp()));
	}

	@Override
	public Node visitNoDecProg(NoDecProgContext c) {
		if (print)
			printVarAndProdName(c);
		return new ProgNode(visit(c.exp()));
	}

	@Override
	public Node visitTimesDiv(TimesDivContext c) {
		if (print)
			printVarAndProdName(c);
		Node n;
		if (c.TIMES() != null) {
			n = new TimesNode(visit(c.exp(0)), visit(c.exp(1)));
			n.setLine(c.TIMES().getSymbol().getLine()); 
		} else {
			n = new DivNode(visit(c.exp(0)), visit(c.exp(1)));
			n.setLine(c.DIV().getSymbol().getLine()); 
		}
		return n;
	}

	@Override
	public Node visitPlusMinus(PlusMinusContext c) {
		if (print)
			printVarAndProdName(c);
		Node n;
		if (c.PLUS() != null) {
			n = new PlusNode(visit(c.exp(0)), visit(c.exp(1)));
			n.setLine(c.PLUS().getSymbol().getLine());
		} else {
			n = new MinusNode(visit(c.exp(0)), visit(c.exp(1)));
			n.setLine(c.MINUS().getSymbol().getLine());
		}
		return n;
	}

	@Override
	public Node visitComp(CompContext c) {
		if (print)
			printVarAndProdName(c);
		Node n;
		if (c.EQ() != null) {
			n = new EqualNode(visit(c.exp(0)), visit(c.exp(1)));
			n.setLine(c.EQ().getSymbol().getLine());
		} else if (c.LE() != null) {
			n = new LessEqualNode(visit(c.exp(0)), visit(c.exp(1)));
			n.setLine(c.LE().getSymbol().getLine());
		} else {
			n = new GreaterEqualNode(visit(c.exp(0)), visit(c.exp(1)));
			n.setLine(c.GE().getSymbol().getLine());
		}
		return n;
	}

	@Override
	public Node visitArrow(ArrowContext c) {
		if (print)
			printVarAndProdName(c);

		List<TypeNode> parList = new ArrayList<>();
		if (c.hotype().size() > 0) {
			for (int i = 0; i < c.hotype().size(); i++) {
				TypeNode tn = (TypeNode) visit(c.hotype(i));
				tn.setLine(c.LPAR().getSymbol().getLine());
				parList.add(tn);
			}
		}
		
		Node n = new ArrowTypeNode(parList, (TypeNode) visit(c.type()));
		n.setLine(c.ARROW().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitArrowType(ArrowTypeContext c) {
		if (print)
			printVarAndProdName(c);
		Node n = visit(c.arrow());
		return n;
	}

	@Override
	public Node visitVardec(VardecContext c) {
		if (print)
			printVarAndProdName(c);
		Node n = null;
		Node exp = visit(c.exp());
		
		if (c.ID() != null) {
			n = new VarNode(c.ID().getText(), (TypeNode) visit(c.hotype()), exp);
			n.setLine(c.VAR().getSymbol().getLine());
		}

		return n;
	}

	@Override
	public Node visitFundec(FundecContext c) {
		if (print)
			printVarAndProdName(c);

		List<ParNode> parList = new ArrayList<>();
		for (int i = 1; i < c.ID().size(); i++) {
			TypeNode tn = (TypeNode) visit(c.hotype().get(i - 1));
			ParNode p = new ParNode(c.ID(i).getText(), tn);
			p.setLine(c.ID(i).getSymbol().getLine());
			parList.add(p);
		}
		List<DecNode> decList = new ArrayList<>();
		for (DecContext dec : c.dec())
			decList.add((DecNode) visit(dec));

		FunNode n = null;
		if (c.ID().size() > 0) { 
			n = new FunNode(c.ID(0).getText(), (TypeNode) visit(c.type()), parList, decList, visit(c.exp()));
			n.setType((TypeNode) visit(c.type()));
			n.setLine(c.FUN().getSymbol().getLine());
		}
		return n;
	}

	@Override
	public Node visitCldec(CldecContext c) {
		if (print)
			printVarAndProdName(c);

		String superID = null;
		Integer i = 1, j = 0;
		List<FieldNode> fields = new ArrayList<>();
		List<MethodNode> methods = new ArrayList<>();

		if (c.EXTENDS() != null) {
			superID = c.ID(i++).getText();
		}

		for (; i < c.ID().size(); i++) {
			TypeNode tn = (TypeNode) visit(c.type().get(j++));
			FieldNode p = new FieldNode(c.ID(i).getText(), tn);
			p.setLine(c.ID(i).getSymbol().getLine());
			fields.add(p);
		}
		for (MethdecContext method : c.methdec()) {
			MethodNode m = ((MethodNode) visit(method));
			methods.add(m);
		}

		ClassNode n = new ClassNode(c.ID(0).getText(), fields, methods, superID);
		n.setLine(c.ID(0).getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitIdType(IdTypeContext c) {
		if (print)
			printVarAndProdName(c);
		return new RefTypeNode(c.ID().getText());
	}

	@Override
	public Node visitIntType(IntTypeContext c) {
		if (print)
			printVarAndProdName(c);
		return new IntTypeNode();
	}

	@Override
	public Node visitBoolType(BoolTypeContext c) {
		if (print)
			printVarAndProdName(c);
		return new BoolTypeNode();
	}
	
	@Override
	public Node visitInteger(IntegerContext c) {
		if (print)
			printVarAndProdName(c);
		int v = Integer.parseInt(c.NUM().getText());
		return new IntNode(c.MINUS() == null ? v : -v);
	}

	@Override
	public Node visitTrue(TrueContext c) {
		if (print)
			printVarAndProdName(c);
		return new BoolNode(true);
	}

	@Override
	public Node visitFalse(FalseContext c) {
		if (print)
			printVarAndProdName(c);
		return new BoolNode(false);
	}

	@Override
	public Node visitIf(IfContext c) {
		if (print)
			printVarAndProdName(c);
		Node ifNode = visit(c.exp(0));
		Node thenNode = visit(c.exp(1));
		Node elseNode = visit(c.exp(2));
		Node n = new IfNode(ifNode, thenNode, elseNode);
		n.setLine(c.IF().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitPrint(PrintContext c) {
		if (print)
			printVarAndProdName(c);
		return new PrintNode(visit(c.exp()));
	}

	@Override
	public Node visitPars(ParsContext c) {
		if (print)
			printVarAndProdName(c);
		return visit(c.exp());
	}

	@Override
	public Node visitId(IdContext c) {
		if (print)
			printVarAndProdName(c);
		Node n = new IdNode(c.ID().getText());
		n.setLine(c.ID().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitCall(CallContext c) {
		if (print)
			printVarAndProdName(c);
		List<Node> arglist = new ArrayList<>();
		for (ExpContext arg : c.exp())
			arglist.add(visit(arg));
		Node n = new CallNode(c.ID().getText(), arglist);
		n.setLine(c.ID().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitDotCall(DotCallContext c) {
		if (print)
			printVarAndProdName(c);
		List<Node> arglist = new ArrayList<>();
		for (ExpContext arg : c.exp())
			arglist.add(visit(arg));
		Node n = new ClassCallNode(c.ID(0).getText(), c.ID(1).getText(), arglist);
		n.setLine(c.ID(0).getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitAndOr(AndOrContext c) {
		if (print)
			printVarAndProdName(c);
		Node n;
		if (c.AND() != null) {
			n = new AndNode(visit(c.exp(0)), visit(c.exp(1)));
			n.setLine(c.AND().getSymbol().getLine());
		} else {
			n = new OrNode(visit(c.exp(0)), visit(c.exp(1)));
			n.setLine(c.OR().getSymbol().getLine());
		}
		return n;
	}

	@Override
	public Node visitNot(NotContext c) {
		if (print)
			printVarAndProdName(c);
		Node n = new NotNode(visit(c.exp()));
		n.setLine(c.NOT().getSymbol().getLine());
		return n;
	}

	@Override
	public Node visitMethdec(MethdecContext c) {
		if (print)
			printVarAndProdName(c);

		List<ParNode> parList = new ArrayList<>();
		for (int i = 1; i < c.ID().size(); i++) {
			TypeNode tn = (TypeNode) visit(c.hotype().get(i - 1));
			ParNode p = new ParNode(c.ID(i).getText(), tn);
			p.setLine(c.ID(i).getSymbol().getLine());
			parList.add(p);
		}

		List<DecNode> decList = new ArrayList<>();
		for (DecContext dec : c.dec())
			decList.add((DecNode) visit(dec));

		MethodNode n = null;
		if (c.ID().size() > 0) {
			n = new MethodNode(c.ID(0).getText(), (TypeNode) visit(c.type()), parList, decList, visit(c.exp()));
			n.setType((TypeNode) visit(c.type()));
			n.setLine(c.FUN().getSymbol().getLine());
		}
		return n;
	}

	@Override
	public Node visitNew(NewContext c) {
		if (print)
			printVarAndProdName(c);

		List<Node> arglist = new ArrayList<>();
		for (ExpContext arg : c.exp())
			arglist.add(visit(arg));

		return new NewNode(c.ID().getText(), arglist);
	}

	@Override
	public Node visitNull(NullContext c) {
		if (print)
			printVarAndProdName(c);
		return new EmptyNode();
	}

}
