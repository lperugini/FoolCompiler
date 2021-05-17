package compiler;

import java.util.*;

import compiler.lib.*;

public class AST {

	public static class ProgLetInNode extends Node {
		final List<DecNode> declist;
		final Node exp;

		ProgLetInNode(List<DecNode> d, Node e) {
			declist = Collections.unmodifiableList(d);
			exp = e;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class ProgNode extends Node {
		final Node exp;

		ProgNode(Node e) {
			exp = e;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class FunNode extends DecNode {
		final String id;
		final TypeNode retType;
		final List<ParNode> parlist;
		final List<DecNode> declist;
		final Node exp;

		FunNode(String i, TypeNode rt, List<ParNode> pl, List<DecNode> dl, Node e) {
			id = i;
			retType = rt;
			parlist = Collections.unmodifiableList(pl);
			declist = Collections.unmodifiableList(dl);
			exp = e;
		}

		void setType(TypeNode t) {
			type = t;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class MethodNode extends DecNode {
		final String id;
		final TypeNode retType;
		final List<ParNode> parlist;
		final List<DecNode> declist;
		final Node exp;
		Integer offset;
		public String label = "";

		MethodNode(String i, TypeNode rt, List<ParNode> pl, List<DecNode> dl, Node e) {
			this.id = i;
			this.retType = rt;
			parlist = Collections.unmodifiableList(pl);
			declist = Collections.unmodifiableList(dl);
			this.exp = e;
		}

		public void setOffset(Integer offset) {
			this.offset = offset;
		}
		
		void setType(TypeNode t) {
			type = t;
		}
		
		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class ParNode extends DecNode {
		final String id;

		ParNode(String i, TypeNode t) {
			id = i;
			type = t;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class FieldNode extends DecNode {
		final String id;
		int offset;

		FieldNode(String id, TypeNode n) {
			this.id = id;
			this.type = n;
		}
		
		public void setOffset(int offset) {
			this.offset = offset;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class VarNode extends DecNode {
		final String id;
		final Node exp;

		VarNode(String i, TypeNode t, Node v) {
			id = i;
			type = t;
			exp = v;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class PrintNode extends Node {
		final Node exp;

		PrintNode(Node e) {
			exp = e;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class IfNode extends Node {
		final Node cond;
		final Node th;
		final Node el;

		IfNode(Node c, Node t, Node e) {
			cond = c;
			th = t;
			el = e;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class EqualNode extends Node {
		final Node left;
		final Node right;

		EqualNode(Node l, Node r) {
			left = l;
			right = r;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class TimesNode extends Node {
		final Node left;
		final Node right;

		TimesNode(Node l, Node r) {
			left = l;
			right = r;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class PlusNode extends Node {
		final Node left;
		final Node right;

		PlusNode(Node l, Node r) {
			left = l;
			right = r;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class CallNode extends Node {
		final String id;
		final List<Node> arglist;
		STentry entry;
		int nl;

		CallNode(String i, List<Node> p) {
			id = i;
			arglist = Collections.unmodifiableList(p);
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class ClassCallNode extends Node {
		final String id1;	// name of the class instance
		final String id2;	// name of the class method invoked
		final List<Node> arglist;

		STentry entry;			// id1 entry
		STentry methodEntry;	// id2 -method- entry
		Integer nl;

		ClassCallNode(String ID1, String ID2, List<Node> argList) {
			this.id1 = ID1;
			this.id2 = ID2;
			this.arglist = argList;
		}

		/*
		 * Method call
		 */
		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class IdNode extends Node {
		final String id;
		STentry entry;
		int nl;

		IdNode(String i) {
			id = i;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class BoolNode extends Node {
		final Boolean val;

		BoolNode(boolean n) {
			val = n;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class IntNode extends Node {
		final Integer val;

		IntNode(Integer n) {
			val = n;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class ArrowTypeNode extends TypeNode {
		final List<TypeNode> parlist;
		final TypeNode ret;

		ArrowTypeNode(List<TypeNode> p, TypeNode r) {
			parlist = Collections.unmodifiableList(p);
			ret = r;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class BoolTypeNode extends TypeNode {

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class IntTypeNode extends TypeNode {

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class GreaterEqualNode extends Node {
		final Node left;
		final Node right;

		GreaterEqualNode(Node left, Node right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class LessEqualNode extends Node {
		final Node left;
		final Node right;

		LessEqualNode(Node left, Node right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class NotNode extends Node {
		final Node val;

		NotNode(Node n) {
			val = n;
		}

		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class MinusNode extends Node {
		final Node left;
		final Node right;

		MinusNode(Node l, Node r) {
			left = l;
			right = r;
		}

		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class OrNode extends Node {
		final Node left;
		final Node right;

		OrNode(Node left, Node right) {
			this.left = left;
			this.right = right;
		}

		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class DivNode extends Node {
		final Node left;
		final Node right;

		DivNode(Node l, Node r) {
			left = l;
			right = r;
		}

		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class AndNode extends Node {
		final Node left;
		final Node right;

		AndNode(Node left, Node right) {
			this.left = left;
			this.right = right;
		}

		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class ClassNode extends DecNode {

		final String id;
		final String superID;
		TypeNode type = null;
		final List<FieldNode> fields;
		final List<MethodNode> methods;
		STentry superEntry = null;

		ClassNode(String id, List<FieldNode> fields, List<MethodNode> methods, TypeNode type, String superID) {
			this.id = id;
			this.fields = fields;
			this.methods = methods;
			this.type = type;
			this.superID = superID;
		}
		
		ClassNode(String id, List<FieldNode> fields, List<MethodNode> methods, String superID) {
			this.id = id;
			this.fields = fields;
			this.methods = methods;
			this.superID = superID;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class NewNode extends Node {

		final String id;
		final List<Node> arglist;
		STentry entry;

		NewNode(String id, List<Node> arglist) {
			this.id = id;
			this.arglist = arglist;
		}

		/*
		 * Represents an object instantiation
		 */

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class EmptyNode extends Node {

		EmptyNode(){
			
		}
		
		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class ClassTypeNode extends TypeNode {

		/*
		 * It's the type of the class - it has all types of all fields and methods,
		 * included the inherited ones (in order of appearance).
		 * 
		 * That's why we call them allFields/allMethods
		 * 
		 */

		final List<TypeNode> allFields;
		final List<ArrowTypeNode> allMethods;

		public ClassTypeNode() {
			this.allFields=new ArrayList<>();
			this.allMethods=new ArrayList<>();
		}
		
		public ClassTypeNode(List<TypeNode> fields, List<ArrowTypeNode> methods) {
			this.allFields = fields;
			this.allMethods = methods;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class MethodTypeNode extends TypeNode {
		/*
		 * MethodTypeNode is like a wrapper allow us to understand if it'a function or a
		 * method
		 * 
		 * Methods needs dispatch table !!
		 * 
		 * - it contains the functional type of the method (ArrowTypeNode)
		 */
		final ArrowTypeNode fun;

		MethodTypeNode(ArrowTypeNode n) {
			this.fun = n;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class RefTypeNode extends TypeNode {
		String id;

		RefTypeNode(String id) {
			this.id = id;
		}

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

	public static class EmptyTypeNode extends TypeNode {

		@Override
		public <S, E extends Exception> S accept(BaseASTVisitor<S, E> visitor) throws E {
			return visitor.visitNode(this);
		}
	}

}