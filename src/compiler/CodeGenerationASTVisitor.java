package compiler;

import compiler.AST.*;
import compiler.lib.*;
import compiler.exc.*;
import static compiler.lib.FOOLlib.*;

public class CodeGenerationASTVisitor extends BaseASTVisitor<String, VoidException> {

  CodeGenerationASTVisitor() {}
  CodeGenerationASTVisitor(boolean debug) {super(false,debug);} //enables print for debugging

	@Override
	public String visitNode(ProgLetInNode n) {
		if (print) printNode(n);
		for (Node dec : n.declist) visit(dec);
		visit(n.exp);
		return null;
		//return nlJoin();
	}

	@Override
	public String visitNode(ProgNode n) {
		if (print) printNode(n);
		visit(n.exp);
		return null;
		//return nlJoin();
	}

	@Override
	public String visitNode(FunNode n) {
		if (print) printNode(n,n.id);
		for (ParNode par : n.parlist) visit(par);
		for (Node dec : n.declist) visit(dec);
		visit(n.exp);
		return null;
		//return nlJoin();
	}

	@Override
	public String visitNode(VarNode n) {
		if (print) printNode(n,n.id);
		visit(n.exp);
		return null;
		//return nlJoin();
	}

	@Override
	public String visitNode(PrintNode n) {
		if (print) printNode(n);
		visit(n.exp);
		return null;
		//return nlJoin();
	}

	@Override
	public String visitNode(IfNode n) {
		if (print) printNode(n);
		visit(n.cond);
		visit(n.th);
		visit(n.el);
		return null;
		//return nlJoin();
	}

	@Override
	public String visitNode(EqualNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
		//return nlJoin();
	}

	@Override
	public String visitNode(TimesNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
		//return nlJoin();
	}

	@Override
	public String visitNode(PlusNode n) {
		if (print) printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
		//return nlJoin();
	}

	@Override
	public String visitNode(CallNode n) {
		if (print) printNode(n,n.id);
		for (Node arg : n.arglist) visit(arg);
		return null;
		//return nlJoin();
	}

	@Override
	public String visitNode(IdNode n) {
		if (print) printNode(n,n.id);
		return null;
		//return nlJoin();
	}

	@Override
	public String visitNode(BoolNode n) {
		if (print) printNode(n,n.val.toString());
		return null;
	}

	@Override
	public String visitNode(IntNode n) {
		if (print) printNode(n,n.val.toString());
		return null;
	}
}

// 	String l1 = freshLabel();


//	String declCode = null;

// generate code for declarations (allocation)


// load address of current frame (containing "id" declaration)
// compute address of "id" declaration
// load value of "id" variable



//	String getAR = null;
//	for (int i = 0; i < n.nl - n.entry.nl; i++) getAR = nlJoin(getAR, "lw");

// by following the static chain (of Access Links)


//	String funl = freshFunLabel();


//	for (int i = n.arglist.size() - 1; i >= 0; i--) argCode = nlJoin(argCode, visit(n.arglist.get(i)));

// load Control Link (pointer to frame of function "id" caller)
// generate code for argument expressions in reversed order

// set $tm to popped value (with the aim of duplicating top of stack)
// load Access Link (pointer to frame of function "id" declaration)
// duplicate top of stack 

// jump to popped address (saving address of subsequent instruction in $ra)
