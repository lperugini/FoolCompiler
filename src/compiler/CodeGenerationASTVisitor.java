package compiler;

import compiler.AST.*;
import compiler.lib.*;
import svm.ExecuteVM;
import compiler.exc.*;
import static compiler.lib.FOOLlib.*;
import java.util.*;

public class CodeGenerationASTVisitor extends BaseASTVisitor<String, VoidException> {

	private List<List<String>> dispatchTables = new ArrayList<>();

	CodeGenerationASTVisitor() {
	}

	CodeGenerationASTVisitor(boolean debug) {
		super(false, debug);
	} // enables print for debugging

	@Override
	public String visitNode(ProgLetInNode n) {
		if (print)
			printNode(n);
		String declCode = null;
		for (Node dec : n.declist)
			declCode = nlJoin(declCode, visit(dec));		
		return nlJoin(
				"push 0", 		// fake return address : value 0
				declCode, 		// generate code for declarations (allocation)
				visit(n.exp),
				"halt", 
				getCode());		// put function's stored code
	}

	@Override
	public String visitNode(ProgNode n) {
		if (print)
			printNode(n);
		return nlJoin(
				visit(n.exp),
				"halt");
	}

	@Override
	public String visitNode(FunNode n) {
		if (print)
			printNode(n, n.id);
		
		String declCode = null; 
		String popDecl = null; 
		String popParl = null;
		
		for (DecNode dec : n.declist) {
			if (dec.getType() instanceof ArrowTypeNode) {
				popDecl = nlJoin(
						popDecl,
						"pop",
						"pop"); //ArrowTypeNode needs 2 deallocations  
			} else {
				popDecl = nlJoin(
						popDecl,
						"pop");
			}

			declCode = nlJoin(
					declCode, 
					visit(dec));
		}

		for (ParNode par : n.parlist) {
			if (par.getType() instanceof ArrowTypeNode) {
				popParl = nlJoin(
						popParl, 
						"pop", 
						"pop"); //ArrowTypeNode needs 2 deallocations  
			} else {
				popParl = nlJoin(
						popParl,
						"pop");
			}
		}
		
		String funl = freshFunLabel();
		
		putCode(nlJoin(funl + ":", 
				"cfp", 			// set $fp to $sp value
				"lra", 			// load $ra value
				declCode,		// generate code for local declarations (they use the new $fp!!!)
				visit(n.exp),	// generate code for function body expression
				"stm", 			// set $tm to popped value (function result)
				popDecl, 		// remove local declarations from stack
				"sra", 			// set $ra to popped value
				"pop", 			// remove Access Link from stack
				popParl, 		// remove parameters from stack
				"sfp", 			// set $fp to popped value (Control Link)
				"ltm", 			// load $tm value (function result)
				"lra", 			// load $ra value
				"js" 			// jump to to popped address
		));
		return nlJoin(
				"lfp", 
				"push " + funl);
	}

	@Override
	public String visitNode(MethodNode n) {
		if (print)
			printNode(n);

		String declCode = null;
		String popDecl = null;
		String popParl = null;

		for (Node dec : n.declist) {
			/*
			 * generate code for declarations
			 */
			declCode = nlJoin(
					declCode, 
					visit(dec));		
			/*
			 * generate code to remove declarations
			 */
			popDecl = nlJoin(
					popDecl, 
					"pop");
		}

		/*
		 * generate code to remove parameters
		 */
		for (int i = 0; i < n.parlist.size(); i++)
			popParl = nlJoin(
					popParl,
					"pop");

		String funl = freshFunLabel(); 	/* generate label */
		n.label = funl; 				/* set method label value */

		putCode(nlJoin(
				funl + ":", 	// write method label 
				"cfp", 			// set $fp to $sp value
				"lra", 			// load $ra value
				declCode, 		// generate code for local declarations (they use the new $fp!!!)
				visit(n.exp), 	// generate code for function body expression
				"stm", 			// set $tm to popped value (function result)
				popDecl, 		// remove local declarations from stack
				"sra", 			// set $ra to popped value
				"pop", 			// remove Access Link from stack
				popParl, 		// remove parameters from stack
				"sfp", 			// set $fp to popped value (Control Link)
				"ltm", 			// load $tm value (function result)
				"lra", 			// load $ra value
				"js" 			// jump to to popped address
		));

		return ""; /* return empty code */
	}

	@Override
	public String visitNode(ClassNode n) {
		if (print)
			printNode(n, n.id);
		String dtCode = null;		
		
		/*
		 * Dispatch Table for this class
		 */
		List<String> dispatchTable = new ArrayList<>();

		/*
		 * If class extends from another 
		 * 		-> copy all the content from the D.T. of the superclass
		 * otherwise 
		 * 		-> D.T. remains empty
		 */
		if (n.superEntry != null) { 
			Integer superDToffset = -(n.superEntry.offset) - 2;
			List<String> parentDT = dispatchTables.get(superDToffset);
			/*
			 * copy every entry of the superclass DT into this DT
			 */
			for (String s : parentDT) {
				dispatchTable.add(s);
			}
		}

		/*
		 * Methods need to be visited 
		 * and also added in DT
		 * 		-> each one in position 'offset' 
		 */
		for (MethodNode method : n.methods) {
			visit(method);
			
			String label = method.label;
			int offset = method.offset;
			
			if (offset < dispatchTable.size()) {
				dispatchTable.set(offset, label);
			} else {
				dispatchTable.add(label);
			}
		}

		/*
		 * the DT is ready to be added to the others
		 */
		dispatchTables.add(dispatchTable);

		/*
		 * put on the heap the dispatch table
		 */
		for (String label : dispatchTable) {
			dtCode = nlJoin(
					dtCode,			
					"push " + label,	// load method label - address
					"lhp",				// load hp value
					"sw",				// save in memory at hp position the method address
					"lhp",				// load hp with the aim of increment it 
					"push 1",			// push 1
					"add",				// calculate new hp value
					"shp");				// pop the new value and put it into hp		
		}

		return nlJoin(
				"lhp", 		// put hp value on stack 
				dtCode); 	// create DT on heap
	}

	@Override
	public String visitNode(EmptyNode n) {
		return "push -1"; /* -1 is not a valid address */
	}

	@Override
	public String visitNode(VarNode n) {
		if (print)
			printNode(n, n.id);
		return visit(n.exp);
	}

	@Override
	public String visitNode(PrintNode n) {
		if (print)
			printNode(n);
		return nlJoin(visit(n.exp), "print");
	}

	@Override
	public String visitNode(IfNode n) {
		if (print)
			printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.cond),
				"push 1", 
				"beq " + l1, 
				visit(n.el),
				"b " + l2, 
				l1 + ":", 
				visit(n.th), 
				l2 + ":");
	}

	@Override
	public String visitNode(EqualNode n) {
		if (print)
			printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"beq " + l1,
				"push 0", 
				"b " + l2, 
				l1 + ":", 
				"push 1", 
				l2 + ":");
	}

	@Override
	public String visitNode(TimesNode n) {
		if (print)
			printNode(n);
		return nlJoin(
				visit(n.left), visit(n.right), "mult");
	}

	@Override
	public String visitNode(PlusNode n) {
		if (print)
			printNode(n);
		return nlJoin(visit(n.left), visit(n.right), "add");
	}

	@Override
	public String visitNode(MinusNode n) {
		if (print)
			printNode(n);
		return nlJoin(visit(n.left), visit(n.right), "sub");
	}

	@Override
	public String visitNode(CallNode n) {
		if (print)
			printNode(n, n.id);
		
		String argCode = null;
		String getAR = null;
		
		for (int i = n.arglist.size() - 1; i >= 0; i--)
			argCode = nlJoin(argCode, visit(n.arglist.get(i)));


		for (int i = 0; i < n.nl - n.entry.nl; i++)
			getAR = nlJoin(getAR, "lw");
		
		if(n.entry.type instanceof MethodTypeNode) {			
			return nlJoin(
					"lfp", 					// load Control Link (pointer to frame of function "id" caller)
					argCode, 				// generate code for argument expressions in reversed order
					"lfp", 					// retrieve address of frame containing "id" declaration
					getAR, 					// by following the static chain (of Access Links)
		            "stm", 					// set $tm to popped value (with the aim of duplicating top of stack)
		            "ltm", 					// load Access Link (pointer to frame of function "id" declaration)
		            "ltm", 					// duplicate top of stack
		            "lw",					// load value on stack from memory
		            "push "+n.entry.offset, // push method offset
		            "add", 					// compute address of "id" declaration
					"lw", 					// load address of "id" function
		            "js"  					// jump to popped address (saving address of subsequent instruction in $ra)
				);
		}
		
		// Higher order code: retrieve 2 values from AR: 
		// 1. address of declaration 
		// 2. address of the function
		return nlJoin(
				"lfp", 							// load Control Link (pointer to frame of function "id" caller)
				argCode, 						// generate code for argument expressions in reversed order
				"lfp", 							// retrieve address of frame containing "id" declaration
				getAR, 							// by following the static chain (of Access Links)
				"stm" ,							// save top of the stack - containing AR 
				"ltm" ,							// load that address
				"push " + n.entry.offset,		// push function declaration offset
				"add",							// calculate function declaration address
				"lw",							// put the value on the stack from memory
				"ltm" ,							// put AR on stack again
				"push " + (n.entry.offset-1),	// push offset-1 - where the label is -
				"add",							// calculate function's label address 
				"lw",							// put the address on stack (label of function's subroutine)
				"js");							// jump to popped address
	}
		
	@Override
	public String visitNode(ClassCallNode n) {
		if (print) printNode(n);
		
		String argCode = null, getAR = null;
		
		for (int i = n.arglist.size() - 1; i >= 0; i--)
			argCode = nlJoin(argCode, visit(n.arglist.get(i)));
		
		for (int i = 0; i < n.nl - n.entry.nl; i++)
			getAR = nlJoin(
					getAR, 
					"lw");
		
		return nlJoin(
				"lfp", 							// load Control Link (pointer to frame of function "id" caller)
				argCode, 						// generate code for argument expressions in reversed order
				"lfp", 							// retrieve address of frame containing "id" declaration
				getAR,							// by following the static chain (of Access Links) 		
				/* 
				 * retrieve ID1 value
				 */
				"push " + n.entry.offset,		// load id1 offset on stack
				"add", 							// compute address
				"lw",							// load value on stack 
				"stm", 							// set $tm to popped value (with the aim of duplicating top of stack)
	            "ltm",							// load Access Link (pointer to frame of function "id" declaration)
	            "ltm", 							// duplicate top of stack
	            "lw",							// load value on stack from memory
	            /* 
	             * retrieve ID2 address
	             */
	            "push " + n.methodEntry.offset, // load id2 offset on stack
				"add", 							// compute address
				"lw",							// load value on stack
				"js"							// jump to sub-routine
				);
	}

	@Override
	public String visitNode(IdNode n) {
		if (print)
			printNode(n, n.id);
		
		String getAR = null;
		
		for (int i = 0; i < n.nl - n.entry.nl; i++)
			getAR = nlJoin(getAR, "lw");
		
		if (n.entry.type instanceof ArrowTypeNode) {
			return nlJoin(
					"lfp", 							// retrieve address of frame containing "id" declaration
					getAR, 							// by following the static chain (of Access Links)
					"push " + n.entry.offset, 		// push offset on stack
					"add", 							// compute address of "id" declaration 
					"lw", 							// load the address of the AR where the function is declared
					"lfp", 							// then retrieve address of frame containing the function
					getAR, 							// by following again the static chain (of Access Links)
					"push " + (n.entry.offset-1), 	// push function offset ( offset-1 )
					"add", 							// compute address of the function
					"lw"							// load value on stack
				);	
		}
		
		return nlJoin(
				"lfp", 								// retrieve address of frame containing "id" declaration
				getAR, 								// by following the static chain (of Access Links)
				"push " + n.entry.offset, 			// push offset on stack 
				"add", 								// compute address of "id" declaration
				"lw"								// load value of "id" variable
		);
	}
	
	@Override
	public String visitNode(NewNode n) {
		if (print)
			printNode(n, n.id);
		
		String argCode = null;
		
		/*
		 * 1. for each field put the value on the stack
		 */
		for (int i = 0; i < n.arglist.size(); i++)
			argCode = nlJoin(argCode, visit(n.arglist.get(i)));
		
		/*
		 * 2. move each value from the stack to the heap and increment hp
		 */
		for (int i = 0; i < n.arglist.size(); i++) {
			argCode = nlJoin(
					argCode,
					"lhp",		// put the value in heap
					"sw",		// save value in memory
					"lhp",		// load hp with the aim of increment it 
					"push 1",	// push 1
					"add",		// calculate new hp value
					"shp"		// pop the new value and put it into hp
					);
		}
		
		/*
		 * 3. Calculate address where to find dispatch pointer
		 */
		int address = ExecuteVM.MEMSIZE + n.entry.offset;
		
		/*
		 * 4. put in memory the dispatch pointer 
		 * 		- found at 'address' 
		 * 
		 * 5. load on stack hp value and then increment it
		 */
		return nlJoin(
				argCode,
				"push " + address,	// load on the stack the address
				"lw", 				// put on the stack the value in 'address' from memory
				"lhp", 				// load on the stack the hp value 
									// - to be intended as dispatch pointer address
				"sw", 				// store at address 'hp' the dispatch pointer 
				"lhp", 				// load on the stack hp value
				"lhp",				// load hp with the aim of increment it 
				"push 1",			// push 1
				"add",				// calculate new hp value
				"shp"				// pop the new value and put it into hp
		);
	}

	@Override
	public String visitNode(BoolNode n) {
		if (print)
			printNode(n, n.val.toString());
		return "push " + (n.val ? 1 : 0);
	}

	@Override
	public String visitNode(IntNode n) {
		if (print)
			printNode(n, n.val.toString());
		return "push " + n.val;
	}

	@Override
	public String visitNode(AndNode n) {
		if (print)
			printNode(n);
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"mult");
	}

	@Override
	public String visitNode(DivNode n) {
		if (print)
			printNode(n);
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"div");
	}

	@Override
	public String visitNode(OrNode n) {
		if (print)
			printNode(n);
		String caseFalse = freshLabel();
		String end = freshLabel();
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"add",
				"push 0",
				"beq " + caseFalse,
				"push 1",
				"b " + end,
				caseFalse + ":",
				"push 0",
				end + ":");
	}

	@Override
	public String visitNode(NotNode n) {
		if (print)
			printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.val),
				"push 0",
				"beq " + l1,
				"push 0",
				"b " + l2,
				l1 + ":",
				"push 1",
				l2 + ":");
	}

	@Override
	public String visitNode(LessEqualNode n) {
		if (print)
			printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.left),
				visit(n.right),
				"bleq " + l1,
				"push 0",
				"b " + l2,
				l1 + ":",
				"push 1",
				l2 + ":");
	}

	@Override
	public String visitNode(GreaterEqualNode n) {
		if (print)
			printNode(n);
		String l1 = freshLabel();
		String l2 = freshLabel();
		return nlJoin(
				visit(n.right),
				visit(n.left),
				"bleq " + l1,
				"push 0",
				"b " + l2,
				l1 + ":",
				"push 1",
				l2 + ":");
	}
}