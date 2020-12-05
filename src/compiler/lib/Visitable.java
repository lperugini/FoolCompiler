package compiler.lib;

public interface Visitable {

	<S,E extends Exception> S accept(BaseASTVisitor<S,E> visitor) throws E;

}
