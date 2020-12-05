package compiler;

import compiler.lib.*;

public class STentry implements Visitable {
	int nl;
	TypeNode type;
	int offset;
	public STentry(int n, TypeNode t, int o) { nl = n; type = t; offset=o; }

	@Override
	public <S,E extends Exception> S accept(BaseASTVisitor<S,E> visitor) throws E {
		return ((BaseEASTVisitor<S,E>) visitor).visitSTentry(this);
	}
}
