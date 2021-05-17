grammar FOOL;
 
@lexer::members {
public int lexicalErrors=0;
}
   
/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
  
prog : progbody EOF ;
     
progbody : LET ( cldec+ dec* | dec+ ) IN exp SEMIC #letInProg
         | exp SEMIC                               #noDecProg
         ;

cldec  : CLASS ID (EXTENDS ID)? 
              LPAR (ID COLON type (COMMA ID COLON type)* )? RPAR    
              CLPAR
                   methdec*                
              CRPAR ; 
         
methdec : FUN ID COLON type 
              LPAR (ID COLON hotype (COMMA ID COLON hotype)* )? RPAR 
                   (LET dec+ IN)? exp 
              SEMIC ;

dec : VAR ID COLON hotype ASS exp SEMIC #vardec
    | FUN ID COLON type 
          LPAR (ID COLON hotype (COMMA ID COLON hotype)* )? RPAR 
               (LET dec+ IN)? exp 
          SEMIC #fundec
    ;

exp     : exp (TIMES | DIV) exp #timesDiv 
        | exp (PLUS | MINUS) exp #plusMinus
        | exp (EQ | GE | LE) exp #comp 
        | exp (AND | OR) exp #andOr
	    | NOT exp #not
        | LPAR exp RPAR #pars
    	| MINUS? NUM #integer
	    | TRUE #true     
	    | FALSE #false       
	    | NULL #null    
	    | NEW ID LPAR (exp (COMMA exp)* )? RPAR #new
	    | IF exp THEN CLPAR exp CRPAR ELSE CLPAR exp CRPAR #if   
	    | PRINT LPAR exp RPAR #print     
        | ID #id
	    | ID LPAR (exp (COMMA exp)* )? RPAR #call
	    | ID DOT ID LPAR (exp (COMMA exp)* )? RPAR #dotCall              
        ; 
 
               
hotype  : type #baseType
        | arrow #arrowType
        ;

type    : INT #intType
        | BOOL #boolType
 	    | ID #idType                       
 	    ;  
 	  
arrow 	: LPAR (hotype (COMMA hotype)* )? RPAR ARROW type ;          
		  
/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

PLUS  	: '+' ;
MINUS   : '-' ;
TIMES   : '*' ;
DIV 	: '/' ;
LPAR	: '(' ;
RPAR	: ')' ;
CLPAR	: '{' ;
CRPAR	: '}' ;
SEMIC 	: ';' ;
COLON   : ':' ; 
COMMA	: ',' ;
DOT	    : '.' ;
OR	    : '||';
AND	    : '&&';
NOT	    : '!' ;
GE	    : '>=' ;
LE	    : '<=' ;
EQ	    : '==' ;	
ASS	    : '=' ;
TRUE	: 'true' ;
FALSE	: 'false' ;
IF	    : 'if' ;
THEN	: 'then';
ELSE	: 'else' ;
PRINT	: 'print' ;
LET     : 'let' ;	
IN      : 'in' ;	
VAR     : 'var' ;
FUN	    : 'fun' ; 
CLASS	: 'class' ; 
EXTENDS : 'extends' ;	
NEW 	: 'new' ;	
NULL    : 'null' ;	  
INT	    : 'int' ;
BOOL	: 'bool' ;
ARROW   : '->' ; 	
NUM     : '0' | ('1'..'9')('0'..'9')* ; 

ID  	: ('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;

WHITESP  : ( '\t' | ' ' | '\r' | '\n' )+    -> channel(HIDDEN) ;

COMMENT : '/*' .*? '*/' -> channel(HIDDEN) ;
 
ERR   	 : . { System.out.println("Invalid char: "+ getText() +" at line "+getLine()); lexicalErrors++; } -> channel(HIDDEN); 
