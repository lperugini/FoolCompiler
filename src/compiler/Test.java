package compiler;

import java.io.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import compiler.lib.*;
import compiler.exc.*;
import svm.*;

public class Test {
    public static void main(String[] args) throws Exception {
   			
    	String fileName = "prova.fool";

    	CharStream chars = CharStreams.fromFileName(fileName);
    	FOOLLexer lexer = new FOOLLexer(chars);
    	CommonTokenStream tokens = new CommonTokenStream(lexer);
    	FOOLParser parser = new FOOLParser(tokens);

    	System.out.println("Generating ST via lexer and parser.");
    	ParseTree st = parser.prog();
    	System.out.println("You had "+lexer.lexicalErrors+" lexical errors and "+
    		parser.getNumberOfSyntaxErrors()+" syntax errors.\n");

    	System.out.println("Generating AST.");
    	ASTGenerationSTVisitor visitor = new ASTGenerationSTVisitor(); // use true to visualize the ST
    	Node ast = visitor.visit(st);
    	System.out.println("");

    	System.out.println("Enriching AST via symbol table.");
    	SymbolTableASTVisitor symtableVisitor = new SymbolTableASTVisitor();
    	symtableVisitor.visit(ast);
    	System.out.println("You had "+symtableVisitor.stErrors+" symbol table errors.\n");

    	System.out.println("Visualizing Enriched AST.");
    	new PrintEASTVisitor().visit(ast);
    	System.out.println("");

    	System.out.println("Checking Types.");
    	try {
    		TypeCheckEASTVisitor typeCheckVisitor = new TypeCheckEASTVisitor();
    		TypeNode mainType = typeCheckVisitor.visit(ast);
    		System.out.print("Type of main program expression is: ");
    		new PrintEASTVisitor().visit(mainType);
    	} catch (IncomplException e) {    		
    		System.out.println("Could not determine main program expression type due to errors detected before type checking.");
    	} catch (TypeException e) {
    		System.out.println("Type checking error in main program expression: "+e.text); 
    	}       	
    	System.out.println("You had "+FOOLlib.typeErrors+" type checking errors.\n");

    	int frontEndErrors = lexer.lexicalErrors+parser.getNumberOfSyntaxErrors()+symtableVisitor.stErrors+FOOLlib.typeErrors;
		System.out.println("You had a total of "+frontEndErrors+" front-end errors.\n");
		
		if ( frontEndErrors > 0) System.exit(1);   

    	System.out.println("Generating code.");
    	String code = new CodeGenerationASTVisitor().visit(ast);
    	BufferedWriter out = new BufferedWriter(new FileWriter(fileName+".asm"));
    	out.write(code);
    	out.close();
    	System.out.println("");
//
//    	System.out.println("Assembling generated code.");
//    	CharStream charsASM = CharStreams.fromFileName(fileName+".asm");
//    	SVMLexer lexerASM = new SVMLexer(charsASM);
//    	CommonTokenStream tokensASM = new CommonTokenStream(lexerASM);
//    	SVMParser parserASM = new SVMParser(tokensASM);
//
//    	parserASM.assembly();
//
//    	// needed only for debug
//    	System.out.println("You had: "+lexerASM.lexicalErrors+" lexical errors and "+parserASM.getNumberOfSyntaxErrors()+" syntax errors.\n");
//    	if (lexerASM.lexicalErrors+parserASM.getNumberOfSyntaxErrors()>0) System.exit(1);
//
//    	System.out.println("Running generated code via Stack Virtual Machine.");
//    	ExecuteVM vm = new ExecuteVM(parserASM.code);
//    	vm.cpu();

    }
}

