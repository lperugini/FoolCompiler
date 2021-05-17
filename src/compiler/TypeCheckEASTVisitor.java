package compiler;

import compiler.AST.*;
import compiler.exc.*;
import compiler.lib.*;
import static compiler.TypeRels.*;


public class TypeCheckEASTVisitor extends BaseEASTVisitor<TypeNode, TypeException> {

	TypeCheckEASTVisitor() {
		super(true);
	} 

	TypeCheckEASTVisitor(boolean debug) {
		super(true, debug);
	} // enables print for debugging

	// checks that a type object is visitable (not incomplete)
	private TypeNode ckvisit(TypeNode t) throws TypeException {
		visit(t);
		return t;
	}

	@Override
	public TypeNode visitNode(ProgLetInNode n) throws TypeException {
		if (print)
			printNode(n);
		for (Node dec : n.declist)
			try {
				visit(dec);
			} catch (IncomplException e) {
			} catch (TypeException e) {
				System.out.println("Type checking error in a declaration: " + e.text);
			}
		return visit(n.exp);
	}

	@Override
	public TypeNode visitNode(ProgNode n) throws TypeException {
		if (print)
			printNode(n);
		return visit(n.exp);
	}

	@Override
	public TypeNode visitNode(FunNode n) throws TypeException {
		if (print)
			printNode(n, n.id);
		for (Node dec : n.declist) {
			try {
				visit(dec);
			} catch (IncomplException e) {
			} catch (TypeException e) {
				System.out.println("Type checking error in a declaration: " + e.text);
			}
		}
		if (!isSubtype(visit(n.exp), ckvisit(n.retType)))
			throw new TypeException("Wrong return type for function " + n.id, n.getLine());
		return null;
	}

	@Override
	public TypeNode visitNode(MethodNode n) throws TypeException {
		if (print)
			printNode(n, n.id);
		for (Node dec : n.declist)
			try {
				visit(dec);
			} catch (IncomplException e) {
			} catch (TypeException e) {
				System.out.println("Type checking error in a declaration: " + e.text);
			}
		if (!isSubtype(visit(n.exp), ckvisit(n.retType)))
			throw new TypeException("Wrong return type for function " + n.id, n.getLine());
		return null;
	}

	@Override
	public TypeNode visitNode(ClassNode n) throws TypeException {
		Integer pos = null;

		if (print)
			printNode(n, n.id);

		for (Node method : n.methods) {
			try {
				visit(method);
			} catch (IncomplException e) {
			} catch (TypeException e) {
				System.out.println("Type checking error in a declaration: " + e.text);
			}
		}
		if (n.superID != null) {

			superType.put(n.id, n.superID);

			ClassTypeNode type = (ClassTypeNode) n.type;
			ClassTypeNode parentCT = (ClassTypeNode) n.superEntry.type; 

			/* 
			 * improvement : check subtype only for overridden fields and methods
			 */
			for (FieldNode field : n.fields) {
				pos = - field.offset - 1;
				if (pos < parentCT.allFields.size()) {
					if (!(isSubtype(type.allFields.get(pos), parentCT.allFields.get(pos)))) {
						System.out.println("Type checking error in a field declaration: Class " + n.id + " index "
								+ field.offset + " ( pos " + pos + " )");
					}
				}
			}
			for (MethodNode method : n.methods) {
				pos = method.offset;
				if (pos < parentCT.allMethods.size()) {
					if (!(isSubtype(type.allMethods.get(pos), parentCT.allMethods.get(pos)))) {
						System.out.println("Type checking error in a method declaration: Class " + n.id + " index "
								+ method.offset + " ( pos " + pos + " )");
					}
				}
			}

		}
		return null;
	}

	@Override
	public TypeNode visitNode(VarNode n) throws TypeException {
		if (print)
			printNode(n, n.id);
		if (!isSubtype(visit(n.exp), ckvisit(n.getType())))
			throw new TypeException("Incompatible value for variable " + n.id, n.getLine());
		return null;
	}

	@Override
	public TypeNode visitNode(PrintNode n) throws TypeException {
		if (print)
			printNode(n);
		return visit(n.exp);
	}

	@Override
	public TypeNode visitNode(IfNode n) throws TypeException {
		if (print)
			printNode(n);

		if (!(isSubtype(visit(n.cond), new BoolTypeNode())))
			throw new TypeException("Non boolean condition in if", n.getLine());

		TypeNode t = visit(n.th);
		TypeNode e = visit(n.el);
		TypeNode ancestor = lowestCommonAncestor(t, e);

		if (ancestor == null)
			throw new TypeException("Incompatible types in then-else branches", n.getLine());
		else
			return ancestor;
	}

	@Override
	public TypeNode visitNode(EqualNode n) throws TypeException {
		if (print)
			printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if (! (isSubtype(l, r) || isSubtype(r, l)) )
			throw new TypeException("Incompatible types in equal", n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(TimesNode n) throws TypeException {
		if (print)
			printNode(n);
		if (!(isSubtype(visit(n.left), new IntTypeNode()) && isSubtype(visit(n.right), new IntTypeNode())))
			throw new TypeException("Non integers in multiplication", n.getLine());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(PlusNode n) throws TypeException {
		if (print)
			printNode(n);
		if (!(isSubtype(visit(n.left), new IntTypeNode()) && isSubtype(visit(n.right), new IntTypeNode())))
			throw new TypeException("Non integers in sum", n.getLine());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(NewNode n) throws TypeException {
		if (print)
			printNode(n, n.id);

		ClassTypeNode ctn = (ClassTypeNode) n.entry.type;

		if (!(ctn.allFields.size() == n.arglist.size()))
			throw new TypeException("Wrong number of parameters in the invocation of " + n.id, n.getLine());

		for (int i = 0; i < n.arglist.size(); i++)
			if (!(isSubtype(visit(n.arglist.get(i)), ctn.allFields.get(i))))
				throw new TypeException("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + n.id,
						n.getLine());

		return new RefTypeNode(n.id);
	}

	@Override
	public TypeNode visitNode(CallNode n) throws TypeException {
		if (print)
			printNode(n, n.id);

		TypeNode t = null;

		if (n.entry.type instanceof MethodTypeNode) {
			t = ((MethodTypeNode) n.entry.type).fun;
		} else {
			t = visit(n.entry);
		}

		if (!(t instanceof ArrowTypeNode))
			throw new TypeException("Invocation of a non-function " + n.id, n.getLine());
		ArrowTypeNode at = (ArrowTypeNode) t;
		if (!(at.parlist.size() == n.arglist.size()))
			throw new TypeException("Wrong number of parameters in the invocation of " + n.id, n.getLine());
		for (int i = 0; i < n.arglist.size(); i++)
			if (!(isSubtype(visit(n.arglist.get(i)), at.parlist.get(i))))
				throw new TypeException("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + n.id,
						n.getLine());
		return at.ret;
	}

	@Override
	public TypeNode visitNode(ClassCallNode n) throws TypeException {
		if (print)
			printNode(n, n.id1 + "." + n.id2);

		TypeNode t = ((MethodTypeNode) n.methodEntry.type).fun;

		if (!(t instanceof ArrowTypeNode))
			throw new TypeException("Invocation of a non-function " + n.id2, n.getLine());
		ArrowTypeNode at = (ArrowTypeNode) t;
		if (!(at.parlist.size() == n.arglist.size()))
			throw new TypeException("Wrong number of parameters in the invocation of " + n.id2, n.getLine());
		for (int i = 0; i < n.arglist.size(); i++)
			if (!(isSubtype(visit(n.arglist.get(i)), at.parlist.get(i))))
				throw new TypeException("Wrong type for " + (i + 1) + "-th parameter in the invocation of " + n.id2,
						n.getLine());
		return at.ret;
	}

	@Override
	public TypeNode visitNode(IdNode n) throws TypeException {
		if (print)
			printNode(n, n.id);
		TypeNode t = visit(n.entry);
		if ((n.entry.type instanceof MethodTypeNode) || visit(n.entry) instanceof ClassTypeNode)
			throw new TypeException("Wrong usage of function identifier " + n.id, n.getLine());
		return t;
	}

	@Override
	public TypeNode visitNode(BoolNode n) {
		if (print)
			printNode(n, n.val.toString());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(IntNode n) {
		if (print)
			printNode(n, n.val.toString());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(ArrowTypeNode n) throws TypeException {
		if (print)
			printNode(n);
		for (Node par : n.parlist)
			visit(par);
		visit(n.ret, "->"); 
		return null;
	}

	@Override
	public TypeNode visitNode(MethodTypeNode n) throws TypeException {
		if (print)
			printNode(n);
		visit(n.fun); 
		return null;
	}

	@Override
	public TypeNode visitNode(BoolTypeNode n) {
		if (print)
			printNode(n);
		return null;
	}

	@Override
	public TypeNode visitNode(IntTypeNode n) {
		if (print)
			printNode(n);
		return null;
	}

	@Override
	public TypeNode visitNode(RefTypeNode n) {
		if (print)
			printNode(n);
		return null;
	}

	@Override
	public TypeNode visitSTentry(STentry entry) throws TypeException {
		if (print)
			printSTentry("type");
		return ckvisit(entry.type);
	}

	@Override
	public TypeNode visitNode(DivNode n) throws TypeException {
		if (print)
			printNode(n);
		if (!(isSubtype(visit(n.left), new IntTypeNode()) && isSubtype(visit(n.right), new IntTypeNode())))
			throw new TypeException("Non integers in division", n.getLine());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(MinusNode n) throws TypeException {
		if (print)
			printNode(n);
		if (!(isSubtype(visit(n.left), new IntTypeNode()) && isSubtype(visit(n.right), new IntTypeNode())))
			throw new TypeException("Non integers in subtraction", n.getLine());
		return new IntTypeNode();
	}

	@Override
	public TypeNode visitNode(NotNode n) throws TypeException {
		if (print)
			printNode(n);
		if (!(isSubtype(visit(n.val), new BoolTypeNode())))
			throw new TypeException("Non boolean expression", n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(AndNode n) throws TypeException {
		if (print)
			printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if (!(isSubtype(l, r) || isSubtype(r, l)))
			throw new TypeException("Incompatible types in and", n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(OrNode n) throws TypeException {
		if (print)
			printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if (!(isSubtype(l, r) || isSubtype(r, l)))
			throw new TypeException("Incompatible types in or", n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(GreaterEqualNode n) throws TypeException {
		if (print)
			printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if (!(isSubtype(l, r) || isSubtype(r, l)) || (r instanceof ArrowTypeNode) || (l instanceof ArrowTypeNode))
			throw new TypeException("Incompatible types in greater-equal", n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(LessEqualNode n) throws TypeException {
		if (print)
			printNode(n);
		TypeNode l = visit(n.left);
		TypeNode r = visit(n.right);
		if (!(isSubtype(l, r) || isSubtype(r, l)) || (r instanceof ArrowTypeNode) || (l instanceof ArrowTypeNode))
			throw new TypeException("Incompatible types in less-equal", n.getLine());
		return new BoolTypeNode();
	}

	@Override
	public TypeNode visitNode(EmptyNode n) throws TypeException {
		if (print)
			printNode(n);
		return new EmptyTypeNode();
	}

}