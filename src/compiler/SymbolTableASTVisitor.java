package compiler;

import java.util.*;

import compiler.AST.*;
import compiler.exc.*;
import compiler.lib.*;

public class SymbolTableASTVisitor extends BaseASTVisitor<Void, VoidException> {

	private List<Map<String, STentry>> symTable = new ArrayList<>();

	/*
	 * Class Table map every class-name in their own virtualTable Map<ClassName,VirtualTable >
	 */
	private Map<String, Map<String, STentry>> classTable = new HashMap<>();

	private int nestingLevel = 0; 	// current nesting level
	private int decOffset = -2; 	// counter for offset of local declarations at current nesting level
	int stErrors = 0;

	SymbolTableASTVisitor() {
	}

	SymbolTableASTVisitor(boolean debug) {
		super(debug);
	} // enables print for debugging

	private STentry stLookup(String id) {
		int j = nestingLevel;
		STentry entry = null;
		while (j >= 0 && entry == null)
			entry = symTable.get(j--).get(id);
		return entry;
	}

	@Override
	public Void visitNode(ProgLetInNode n) {
		if (print)
			printNode(n);
		Map<String, STentry> hm = new HashMap<>();
		symTable.add(hm);
		for (Node dec : n.declist)
			visit(dec);
		visit(n.exp);
		symTable.remove(0);
		return null;
	}

	@Override
	public Void visitNode(ProgNode n) {
		if (print)
			printNode(n);
		visit(n.exp);
		return null;
	}

	@Override
	public Void visitNode(ArrowTypeNode n) {
		if (print)
			printNode(n);
		for (Node par : n.parlist)
			visit(par);
		return null;
	}

	@Override
	public Void visitNode(FunNode n) {
		if (print)
			printNode(n);
		Map<String, STentry> hm = symTable.get(nestingLevel);
		List<TypeNode> parTypes = new ArrayList<>();

		for (ParNode par : n.parlist)
			parTypes.add(par.getType());
		STentry entry = new STentry(nestingLevel, new ArrowTypeNode(parTypes, n.retType), decOffset--);
		n.setType(new ArrowTypeNode(parTypes, n.retType));
		// inserimento di ID nella symtable
		if (hm.put(n.id, entry) != null) {
			System.out.println("Fun id " + n.id + " at line " + n.getLine() + " already declared");
			stErrors++;
		}
		decOffset--;

		// create new HashMap for symTable
		nestingLevel++;
		Map<String, STentry> hmn = new HashMap<>();
		symTable.add(hmn);
		int prevNLDecOffset = decOffset; 	// stores counter for offset of declarations at previous nesting level
		decOffset = -2;

		int parOffset = 1;

		for (ParNode par : n.parlist) {
			if (par.getType() instanceof ArrowTypeNode) {
				parOffset++;
			}
			if (hmn.put(par.id, new STentry(nestingLevel, par.getType(), parOffset++)) != null) {
				System.out.println("Par id " + par.id + " at line " + n.getLine() + " already declared");
				stErrors++;
			}
		}

		for (Node dec : n.declist)
			visit(dec);
		visit(n.exp);
		// remove current HashMap when exiting scope 
		symTable.remove(nestingLevel--);
		decOffset = prevNLDecOffset; 		// restores counter for offset of declarations at previous nesting level
		return null;
	}

	@Override
	public Void visitNode(MethodNode n) {
		if (print)
			printNode(n);
		STentry entry = null;
		Integer offset = null;
		
		/* 
		 * get the symbol table entry
		 */
		Map<String, STentry> hm = symTable.get(nestingLevel);

		List<TypeNode> parTypes = new ArrayList<>();
		/* 
		 * Method parameters 
		 */
		for (ParNode par : n.parlist) 		
			parTypes.add(par.getType());

		ArrowTypeNode fun = new ArrowTypeNode(parTypes, n.retType);
		MethodTypeNode node = new MethodTypeNode(fun);
		n.setType(node);
		STentry oldEntry = hm.get(n.id);
		if (oldEntry != null) {
			/* 
			 * Method overriding
			 */
			offset = oldEntry.offset;
			n.offset = offset;
			entry = new STentry(nestingLevel, node, offset);
			if (hm.replace(n.id, entry) == null) {
				System.out.println("Error in retrieving method entry for id " + n.id + " at line " + n.getLine() + ".");
			}
		} else { 
			/*
			 *  New entry 
			 */
			n.offset = decOffset;
			entry = new STentry(nestingLevel, node, decOffset++);
			if (hm.put(n.id, entry) != null) {
				System.out.println("Error in declaring method entry for id " + n.id + " at line " + n.getLine() + ".");
			}
		}

		// create new HashMap for SymbolTable
		nestingLevel++;
		Map<String, STentry> hmn = new HashMap<>();
		symTable.add(hmn);

		int prevNLDecOffset = decOffset; 	// stores counter for offset of declarations at previous nesting level
		decOffset = -2;
		int parOffset = 1;

		/* visit parameters */
		for (ParNode par : n.parlist)
			if (hmn.put(par.id, new STentry(nestingLevel, par.getType(), parOffset++)) != null) {
				System.out.println("Par id " + par.id + " at line " + n.getLine() + " already declared");
				stErrors++;
			}
		/* visit declarations */
		for (Node dec : n.declist)
			visit(dec);
		/* visit expression */
		visit(n.exp);

		// remove current HashMap when exiting scope 
		symTable.remove(nestingLevel--);
		decOffset = prevNLDecOffset; 		// restores counter for offset of declarations at previous nesting level
		return null;
	}

	public Void visitNode(ClassNode n) {
		if (print)
			printNode(n);
		
		Set<String> names = new HashSet<>();
		// new HashMap for symTable
		Map<String, STentry> hm = symTable.get(0);
		// new virtualTable
		Map<String, STentry> virtualTable = new HashMap<>();
		ClassTypeNode classType = null;

		int initialMethOffset = 0;
		int fieldOffset = -1;

		classType = new ClassTypeNode(new ArrayList<>(), new ArrayList<>());
		/* 
		 * class entry 
		 */
		STentry cEntry = null; 
		
		if (n.superID != null) { 
			/* 
			 * case : extends
			 */
			STentry scEntry = hm.get(n.superID); /* the super class entry */
			if (scEntry != null) {
				/*
				 * superclass found : copy all the content of the type
				 */
				if (classTable.get(n.superID) != null) {
					/*
					 * superclass found : copy all the content of the virtual table
					 */
					ClassTypeNode superType = (ClassTypeNode) scEntry.type;
					List<TypeNode> fields = new ArrayList<TypeNode>(superType.allFields);
					List<ArrowTypeNode> methods = new ArrayList<ArrowTypeNode>(superType.allMethods);
					classType = new ClassTypeNode(fields, methods);
					virtualTable = classTable.get(n.superID);	
					n.superEntry = scEntry;
					n.type = classType;					
					fieldOffset = -classType.allFields.size() - 1;
					initialMethOffset = classType.allMethods.size();
				} else {
					/*
					 * superclass not found in class table
					 */
					System.out.println("Class ID '" + n.superID + "' not found in Class Table at line " + n.getLine());
					stErrors++;
				}
			} else {
				/*
				 * superclass not found in symbol table
				 */
				System.out.println("Class ID '" + n.superID + "' not found in Symbol Table at line " + n.getLine());
				stErrors++;
			}
		}			
		cEntry = new STentry(nestingLevel, classType, decOffset--);

		/*
		 * 1. Add class entry Symbol Table at level 0
		 */
		if (hm.put(n.id, cEntry) != null) {
			/*
			 * class already declared
			 */
			System.out.println("Class id " + n.id + " at line " + n.getLine() + " already declared. Error in Symbol Table.");
			stErrors++;
		}
		/*
		 * 2. Add the class name in Class Table linked to its Virtual Table (just defined)
		 */
		classTable.put(n.id, virtualTable);	
		/*
		 * 3. Add a new level in Symbol Table - is the Virtual Table just defined
		 */
		//symTable.add(virtualTable);
		symTable.add(virtualTable);
		
		nestingLevel++;

		STentry val = null;
		TypeNode type = null;
		Integer offset = null;
		Integer pos = null;
		/*
		 * for each field and method I need to enrich both the Virtual Table and the ClassTypeNode
		 */
		for (FieldNode field : n.fields) {
			/* fields doesn't need to be visited */
			/*
			 * look for previous field declaration
			 */
			if (names.contains(field.id)) {
				/* name already exists for this class -> error */
				System.out.println("Duplicate field name " + field.id + " at line " + field.getLine());
				stErrors++;
			} else {
				/* new field name -> need to declare it */
				names.add(field.id);

				val = virtualTable.get(field.id);
				type = field.getType();
				if (val != null) {
					/*
					 * If a previous declaration exists, it's not an error but i replace the entry
					 * (method override)
					 */
					if (!(type instanceof MethodTypeNode)) {
						/*
						 * Replace the old entry with a new one but at the same position (offset) in the
						 * virtual table
						 */
						offset = val.offset;
						STentry entry = new STentry(nestingLevel, type, offset);
						field.setOffset(offset);
						if (virtualTable.replace(field.id, entry) == null) {
							System.out.println("field id " + field.id + " at line " + n.getLine() + " not found for overriding");
							stErrors++;
						}
						/*
						 * Put the new field type at the same position in the ClassTypeNode
						 */
						pos = -offset - 1;
						classType.allFields.set(pos, type);
					} else {
						/*
						 * but i cannot override a field with a method or viceversa
						 */
						System.out.println("Cannot override a method with a field:  id " + n.id + " at line " + n.getLine() + ".");
						stErrors++;
					}
				} else {
					/*
					 * No other parameters with that id, no override Put a new entry in a new
					 * position (offset) in the virtualTable
					 */
					if (virtualTable.put(field.id, new STentry(nestingLevel, type, fieldOffset)) != null) {
						System.out.println("Field id " + field.id + " at line " + n.getLine() + ". Error in declaration.");
						stErrors++;
					}
					/*
					 * Put the new field type in a new position in the ClassTypeNode
					 */
					field.setOffset(fieldOffset);
					pos = -fieldOffset - 1;
					classType.allFields.add(pos, type);
					fieldOffset--;
				}
			}
		}
		Integer savedOffset = decOffset;
		decOffset = initialMethOffset;
		for (MethodNode method : n.methods) {
			if (names.contains(method.id)) {
				System.out.println("Duplicate method name  " + method.id + " at line " + method.getLine());
				stErrors++;
			} else {
				names.add(method.id);
				/* virtual table is updated inside method visit */
				visit(method);
				/* ClassTypeNode needs to be updated */
				classType.allMethods.add(method.offset, ((MethodTypeNode) method.getType()).fun);
			}
		}
		n.type = classType;
		/* restore the old offset value */
		decOffset = savedOffset;
		/*
		 * Remove current level of Symbol table
		 */
		symTable.remove(nestingLevel--);
		return null;
	}

	@Override
	public Void visitNode(ClassCallNode n) {
		if (print)
			printNode(n);
		STentry entry = stLookup(n.id1);
		STentry methodEntry = null;
		RefTypeNode ref = null;
		
		if (entry.type instanceof RefTypeNode) {
			ref = (RefTypeNode) entry.type;
			methodEntry = classTable.get(ref.id).get(n.id2);
		} else {
			System.out.println("Class id " + n.id1 + " has no entry-type RefTypeNode, at line " + n.getLine());
			stErrors++;
		}
		if ((entry == null) || (methodEntry == null)) {
			System.out.println("Class id " + n.id1 + " or Fun id " + n.id2 + " at line " + n.getLine() + " not declared");
			stErrors++;
		} else {
			n.entry = entry;
			n.methodEntry = methodEntry;
			n.nl = nestingLevel;
		}
		for (Node arg : n.arglist)
			visit(arg);

		return null;
	}

	@Override
	public Void visitNode(VarNode n) {
		if (print)
			printNode(n);
		visit(n.exp);
		Map<String, STentry> hm = symTable.get(nestingLevel);

		STentry entry = new STentry(nestingLevel, n.getType(), decOffset--);

		if (n.getType() instanceof ArrowTypeNode)
			decOffset--;
		
		// insert ID in Symbol Table
		if (hm.put(n.id, entry) != null) {
			System.out.println("Var id " + n.id + " at line " + n.getLine() + " already declared");
			stErrors++;
		}
		return null;
	}

	@Override
	public Void visitNode(PrintNode n) {
		if (print)
			printNode(n);
		visit(n.exp);
		return null;
	}

	@Override
	public Void visitNode(IfNode n) {
		if (print)
			printNode(n);
		visit(n.cond);
		visit(n.th);
		visit(n.el);
		return null;
	}

	@Override
	public Void visitNode(EqualNode n) {
		if (print)
			printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(TimesNode n) {
		if (print)
			printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(PlusNode n) {
		if (print)
			printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(CallNode n) {
		if (print)
			printNode(n);
		STentry entry = stLookup(n.id);
		if (entry == null) {
			System.out.println("Fun id " + n.id + " at line " + n.getLine() + " not declared");
			stErrors++;
		} else {
			n.entry = entry;
			n.nl = nestingLevel;
		}
		for (Node arg : n.arglist)
			visit(arg);
		return null;
	}

	@Override
	public Void visitNode(IdNode n) {
		if (print)
			printNode(n);
		STentry entry = stLookup(n.id);
		if (entry == null) {
			System.out.println("Var or Par id " + n.id + " at line " + n.getLine() + " not declared");
			stErrors++;
		} else {
			n.entry = entry;
			n.nl = nestingLevel;
		}
		return null;
	}

	@Override
	public Void visitNode(BoolNode n) {
		if (print)
			printNode(n, n.val.toString());
		return null;
	}

	@Override
	public Void visitNode(IntNode n) {
		if (print)
			printNode(n, n.val.toString());
		return null;
	}

	@Override
	public Void visitNode(NewNode n) {
		if (print)
			printNode(n);
		if (classTable.get(n.id) == null) {
			System.out.println("Class id " + n.id + " at line " + n.getLine() + " not in class table");
		}
		n.entry = symTable.get(0).get(n.id);
		if (n.entry == null) {
			System.out.println("Class id " + n.id + " at line " + n.getLine() + " not in symbol table level 0");
		}
		for (Node arg : n.arglist)
			visit(arg);
		return null;
	}

	@Override
	public Void visitNode(GreaterEqualNode n) {
		if (print)
			printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(LessEqualNode n) {
		if (print)
			printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(OrNode n) {
		if (print)
			printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(AndNode n) {
		if (print)
			printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(NotNode n) {
		if (print)
			printNode(n);
		visit(n.val);
		return null;
	}

	@Override
	public Void visitNode(MinusNode n) {
		if (print)
			printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(DivNode n) {
		if (print)
			printNode(n);
		visit(n.left);
		visit(n.right);
		return null;
	}

	@Override
	public Void visitNode(EmptyNode n) {
		if (print)
			printNode(n);
		return null;
	}

}
