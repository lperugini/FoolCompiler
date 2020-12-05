// Generated from FOOL.g4 by ANTLR 4.4
package compiler;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link FOOLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface FOOLVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code pars}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPars(@NotNull FOOLParser.ParsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intType}
	 * labeled alternative in {@link FOOLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntType(@NotNull FOOLParser.IntTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code false}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFalse(@NotNull FOOLParser.FalseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code integer}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(@NotNull FOOLParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eq}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEq(@NotNull FOOLParser.EqContext ctx);
	/**
	 * Visit a parse tree produced by {@link FOOLParser#prog}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProg(@NotNull FOOLParser.ProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code plus}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlus(@NotNull FOOLParser.PlusContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolType}
	 * labeled alternative in {@link FOOLParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolType(@NotNull FOOLParser.BoolTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code call}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(@NotNull FOOLParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by the {@code fundec}
	 * labeled alternative in {@link FOOLParser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFundec(@NotNull FOOLParser.FundecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code print}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(@NotNull FOOLParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by the {@code times}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTimes(@NotNull FOOLParser.TimesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code vardec}
	 * labeled alternative in {@link FOOLParser#dec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardec(@NotNull FOOLParser.VardecContext ctx);
	/**
	 * Visit a parse tree produced by the {@code letInProg}
	 * labeled alternative in {@link FOOLParser#progbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetInProg(@NotNull FOOLParser.LetInProgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code true}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrue(@NotNull FOOLParser.TrueContext ctx);
	/**
	 * Visit a parse tree produced by the {@code id}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(@NotNull FOOLParser.IdContext ctx);
	/**
	 * Visit a parse tree produced by the {@code if}
	 * labeled alternative in {@link FOOLParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIf(@NotNull FOOLParser.IfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code noDecProg}
	 * labeled alternative in {@link FOOLParser#progbody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNoDecProg(@NotNull FOOLParser.NoDecProgContext ctx);
}