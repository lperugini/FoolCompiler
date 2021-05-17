package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import compiler.AST.*;
import compiler.lib.*;

public class TypeRels {

	public static Map<String, String> superType = new HashMap<>();

	public static boolean isSubtype(TypeNode a, TypeNode b) {

		if ((a instanceof ArrowTypeNode) && (b instanceof ArrowTypeNode)) {
			ArrowTypeNode f = (ArrowTypeNode) a;
			ArrowTypeNode s = (ArrowTypeNode) b;
			if (f.parlist.size() != s.parlist.size())
				return false; 												/* same parameters number */
			if (isSubtype(f.ret, s.ret)) { 									/* return type covariance */
				for (int i = 0; i < f.parlist.size(); i++) {
					if (!isSubtype(s.parlist.get(i), f.parlist.get(i))) 	/* parameters contravariance */
						return false;
				}
				return true;
			}
		}
		
		// if 'a' is 'null' and 'b' is a reference to class, then 'a' is sub-type of 'b'
		if ((a instanceof EmptyTypeNode) && (b instanceof RefTypeNode))
			return true;

		// if b is 'null', then it cannot be super-type of anything
		if (b instanceof EmptyTypeNode)
			return false;

		// if both are reference to class, then check sub-typing
		if (a instanceof RefTypeNode && b instanceof RefTypeNode) {
			if (checkSuperType((RefTypeNode) a, (RefTypeNode) b))
				return true;
		}

		return a.getClass().equals(b.getClass()) || ((a instanceof BoolTypeNode) && (b instanceof IntTypeNode));
	}

	private static boolean checkSuperType(RefTypeNode a, RefTypeNode b) {
		String first = a.id;
		String second = b.id;

		/* explore the entire chain of extends */
		/* until the chain is over OR one of the superclass of FIRST is SECOND */
		while (first != null && !(first == second))
			first = superType.get(first);

		return first != null;

	}

	public static TypeNode lowestCommonAncestor(TypeNode a, TypeNode b) {

		/*
		 * if 'a' is a null type, then return 'b'
		 */
		if ((a instanceof EmptyTypeNode))
			return b;

		/*
		 * if 'b' is a null type, then return 'a'
		 */
		if ((b instanceof EmptyTypeNode))
			return a;

		/*
		 * if 'a' is Integer and 'b' is Boolean or vice-versa return Integer Type
		 * 
		 */
		if ((a instanceof IntTypeNode) && (b instanceof BoolTypeNode)
				|| (a instanceof IntTypeNode) && (b instanceof BoolTypeNode)
				|| ((a instanceof IntTypeNode) && (b instanceof IntTypeNode))) {
			return new IntTypeNode();
		}
		/*
		 * if 'a' and 'b' are both Boolean return Boolean Type
		 * 
		 */
		if ((a instanceof BoolTypeNode) && (b instanceof BoolTypeNode)) {
			return new BoolTypeNode();
		}

		/*
		 * If both are ArrowType
		 */
		if (a instanceof ArrowTypeNode && b instanceof ArrowTypeNode) {
			ArrowTypeNode first = (ArrowTypeNode) a;
			ArrowTypeNode second = (ArrowTypeNode) b;

			/*
			 * They must have same number of parameters
			 */
			if (first.parlist.size() != second.parlist.size())
				return null;

			/*
			 * Check if exists a lowestCommonAncestor between the return types
			 */
			TypeNode ancestor = lowestCommonAncestor(first.ret, second.ret);
			if (ancestor == null)
				return null;

			/*
			 * if a lowestCommonAncestor exists
			 */
			List<TypeNode> parList = new ArrayList<TypeNode>();

			for (int i = 0; i < first.parlist.size(); i++) {
				TypeNode aPar = first.parlist.get(i);
				TypeNode bPar = second.parlist.get(i);
				/*
				 * check every i-th parameter in the two nodes take the one that is sub-type of
				 * the other
				 * 
				 * if neither is a sub-type of the other, null is returned
				 */
				if (isSubtype(aPar, bPar))
					parList.add(aPar);
				else if (isSubtype(bPar, aPar))
					parList.add(bPar);
				else
					return null;
			}

			return new ArrowTypeNode(parList, ancestor);
		}

		/*
		 * follow the chain of 'a' super-classes - if 'b' is sub-type of 'a' -> 'a' is
		 * returned - otherwise, check every superclass of 'a' when 'b' is sub-type of
		 * the checked class, return that class
		 * 
		 * if 'b' is not sub-class of any superclass of 'a' - null is returned
		 */
		if (a instanceof RefTypeNode && b instanceof RefTypeNode) {
			while (a != null && !isSubtype(b, a)) {
				a = new RefTypeNode(superType.get(((RefTypeNode) a).id));
			}
			return a;
		}

		/*
		 * Else null is returned
		 */
		return null;
	}

}