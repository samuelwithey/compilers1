# Task 1

Introduction. In the lectures we saw how a finite state automaton (FSA) can be represented by a 2-dimensional matrix. In this task you should write a simple FSA that takes as input a matrix and then decides the language given by the matrix. Note that the specification below is wordy, but the task is very simple: my example solution is 11 lines of plain Java code, and it's not importing any libraries.

Task. You are asked to produce a Java program that takes as input a 2-dimensional matrix together with an initial and terminal state (representing an FSA). Your submission should return a Java algorithm instantiating the interface Language (given below) that correctly decides the language given by the input FSA.

Let's make this more precise. As characters we only allow integers. States are also integers. More precisely:

* Alphabet. We assume that the alphabet of any given language is always of the form {0, 1, ..., m} for some fixed integer m. (The m might be different for different languages.)
* States. We assume that the states of any given FSA is always of the form {0, 1, ..., n} for some fixed integer n. (The n might be different for different FSAs.)
* Terminal state. We assume exactly one terminal state, which must be one of {0, 1, ..., n}.

The signature that characterises the input that your submission needs to process is:

    interface Matrix2D {
    	public int initialState ();
    	public int terminalState ();
	public int nextState ( int currentState, int character ); 
    }
	  
You can think of nextState( s, c ) as giving the entry in the 2-dimensional matrix at row s and column c. Your solution to Task 1 must be of the following form:

    class Task1 {
        public static Language create ( Matrix2D matrix2D ) { return ...; } 
    }
	  
In other words, you must submit a working implementation of the class Task1. This means you must replace the ... by actual Java (and add other code). You can think of create as a 'language factory' that, when called, returns an instance of the interface Language. You will have to submit the Java for this instance. In other words, create takes as input a matrix (an instance of the Matrix2D interface) representing an FSA, and returns an object deciding the language given by the matrix. The Language interface is as follows:

interface Language {
    public Boolean decide ( int [] input ) throws Task1Exception; }
	  
The decide method takes as input a 'string', here represented for simplicity as an array of integers (recall the assumption above that the characters making up the alphabet are integers). The requirement is that decide returns true exactly when the input string is in the language of the input to the create method.

If the decide method that you provide encounters an error condition, use Task1Exception to report it. Do not throw any other exceptions. Note that you do not have to throw an exception if you don't want to.

class Task1Exception extends Exception {
    public String msg;
    public Task1Exception ( String _msg ) { msg = _msg; } }
You can find this code here.

You can assume that, all instances of Matrix2D will only contain valid states as entries. In other words: whenever s is a valid state, and c is a valid character, then nextState( s, c ) will return a valid state.

Note that Matrix2D does not explicitly state which integers are states and which integers are characters. This is not necessary for your algorithm to work. You can assume that all tests that I will run against your code only use 'strings' (here of given by an array int []) that contain characters in the relevant alphabet. In other words, the instances of Matrix2D you will be given can handle any 'string' my tests use. There is no need to cater for out-of-alphabet characters.

Note also that you do not have to supply classes instantiating the Matrix2D interface.

Examples. Consider the alphabet {0, 1} and the language of all binary strings over this alphabet. The following instance of Matrix2D is one possible implementation deciding this language.

class BinaryNumbers implements Matrix2D {
    private int [] [] fsaTable = new int [] [] { { 0, 0 } };	
    public int initialState () { return 0; };
    public int terminalState () { return 0; };
    public int nextState ( int currentState, int character ) {
	return fsaTable [ currentState ] [ character ]; } }
	  
Clearly BinaryNumbers implements the FSA given by this picture (red numbers are states, black numbers represent characters from the alphabet).

Image of FSA
As a second example, again over the alphabet {0, 1}, consider the language of all binary strings divisible by 4. The following instance of Matrix2D is one possible implementation deciding this language.

class BinaryNumbersDivisibleBy4 implements Matrix2D {
    private int [] [] fsaTable
        = new int [] [] { { 0, 1 }, { 2, 1 }, { 0, 1 } };
    public int initialState () { return 0; };
    public int terminalState () { return 0; };
    public int nextState ( int currentState, int character ) {
	return fsaTable [ currentState ] [ character ]; } }
	  
Clearly BinaryNumbersDivisibleBy4 implements the FSA given by this picture (red numbers are states, black numbers represent characters from the alphabet).

Image of FSA
Note that your submission might be tested with input over larger alphabets of integers. You may also encounter automata where initial and terminal state are not identical.

# Task 2


Introduction. In this task you will write a lexer for the simple programming language given here. You are free to write the lexer in any form you want, whether 'by hand' or with lexer generator, as long as it adheres to the requirements specified below. Writing the lexer by hand will give you a better understanding of lexical analysis, while using a tools achieves the goal with many fewer lines of code (my example solution, written using the JFlex lexer generator is around 60 lines of code). Another implementation technique (probably easier) is using Java's regular expression library.

Task. Your lexer takes arbitrary strings (over the given alphabet) as input, and returns either a suitable token list when the input is lexically valid, or a suitable error exception (when the input is lexically invalid). Note that you are not asked to check if the input is syntactically correct w.r.t. to the context free grammar here. You should only check for lexical correctness.

The lexer has to have the following signature.

class LexicalException extends Exception {
    public String msg;
    public LexicalException ( String _msg ) { msg = _msg; } }

class Task2Exception extends Exception {
    public String msg;
    public Task2Exception ( String _msg ) { msg = _msg; } }

interface Lexer {
    public List<Token> lex ( String input ) throws LexicalException, 
                                                   Task2Exception; }
Here List<...> is the list class imported from java.util.List. The lex method takes a string that is to be lexed. The interface Token and all its implementations are given here. You need to use those tokens. The class LexicalException should be thrown whenever input is encountered that does not adhere to the specification. Do not use this exception to indicate any other form of error -- use Task2Exception for any other error. Errors should be reported by exception only.

You will also have to implement a method create that, when called, returns an instance of the interface Lexer. For this please use the following fragment, replacing ... with the appropriate code.

class Task2 {
    public static Lexer create () { ... } }
For your convenience, here are the remaining definitions in one file. If you use a lexer generator to handle this task, I strongly recommend submitting the generator's source files too. I am unable to comprehend and comment on auto-generated lexers without source files: the auto-generated code is too complicated/weird for humans to comprehend directly.

Examples. Here is a lexically valid program in the language.

def f(x,y,z) = { if x == y then { z } else { 0 } }
It gives rise to the following token stream.

T_Def 
T_Identifier ( "f" )
T_LeftBracket
T_Identifier ( "x" )
T_Comma
T_Identifier ( "y" )
T_Comma
T_Identifier ( "z" )
T_RightBracket
T_EqualDefines
T_LeftCurlyBracket
T_If
T_Identifier ( "x" )
T_Equal
T_Identifier ( "y" )
T_Then
T_LeftCurlyBracket
T_Identifier ( "z" )
T_RightCurlyBracket
T_Else
T_LeftCurlyBracket
T_Integer ( 0 )
T_RightCurlyBracket
T_RightCurlyBracket
Note that the following is also a valid (at the lexical level) input.

;;{{{}}{{{ {{}}}} }}}}}}}}10 10 if if then then then else


# Task 3

Introduction. This task is about parsing. You will write a parser for a fragment of the simple programming language given here.

Task. Write a parser for the language specified by the CFG below.

 INT → ... 
 BLOCK → { ENE }
 ENE → E | E; ENE
 E →  INT | BLOCK | skip
The starting symbol is BLOCK. Your parser will take as input a token list, using the definition of tokens from Task 2 (given here). Your parser should return a suitable AST (i.e. an instance of the class Block), provided the input adheres to the syntax given above. Otherwise it should throw a SyntaxException given below. As the parser takes a token list as input, it might be helpful to see the grammar in a form where terminal symbols are token names. Since the grammar above does not parse the full language, not all valid tokens are used for valid programs, e.g. T_Then .

 BLOCK → T_LeftCurlyBracket ENE T_RightCurlyBracket
 ENE → E | E T_Semicolon ENE
 E →  T_Integer | BLOCK | T_Skip
Note that you are not asked to write a parser for the full language given here, only for the fragment above. You are free to write the parser in any form you want, whether 'by hand' or with a parser generator like CUPS, Yacc etc. As the grammar fragment is so simple, I recommend writing a top-down parser 'by hand'. As explained in the lectures, a top-down parser is naturally written using recursion rather than loops. If you find yourself using loop constructs, I recommend to rethink your approach. If you use a parser generator to handle this task, I strongly recommend submitting the generator's source files too. I am unable to comprehend and comment on auto-generated parsers without source files: the auto-generated code is too complicated/weird for humans to comprehend directly.

Your parser has to implement the interface Parser given next.

    interface Parser {
    	public Block parse ( List < Token > input ) throws SyntaxException, Task3Exception; 
    }

    class SyntaxException extends Exception {
    	public String msg;
    	public SyntaxException ( String _msg ) { msg = _msg; } 
    }

    class Task3Exception extends Exception {
    	public String msg;
	public Task3Exception ( String _msg ) { msg = _msg; } 
    }

Here List<...> is the list class imported from java.util.List. The AST class Block is given here. You need to use those classes and not modify them at all. The class SyntaxException should be thrown whenever input is encountered that does not adhere to the syntactic specification. Do not use this exception to indicate any other form of error -- use Task3Exception for any other error. Errors should be reported by exception only.

You will also have to implement a method create that, when called, returns an instance of the interface Parser. For this please use the following fragment, replacing ... with the appropriate code.

class Task3 {
    public static Parser create () { ... } }
For your convenience, here are the remaining definitions in one file.

# Language Syntax
Lexical description. The lexical units of the language are integers, special notation, identifiers, keywords, and white space. Any input string that contains only those components is lexically valid.

Keywords. def, if, then, else, skip, while, do, repeat, until, break and continue. Note that keywords are case sensitive, and do not contain upper-case letters.
Integers. Integers are non-empty strings of digits 0-9.
Identifiers. Identifiers are non-empty strings consisting of letters (lower or upper case), digits, and the underscore character. The first character in an identifier must be a lower-case letter.
White space. White space consists of any sequence of the characters: blank (ascii 32), \n (newline, ascii 10), \f (form feed, ascii 12), \r (carriage return, ascii 13), \t (tab, ascii 9). Whitespace always separates tokens: whatever (non whitespace) is to the left of a whitespace must be part of a different token than whatever is on the right of the whitespace. Note that the opposite direction is not necessarily true: two distinct tokens are not always separated by whitespace, for example the string (()) consists of 4 tokens, likewise the string 65x consists of two tokens, T_Integer(65) followed by token T_Identifier("x"), and the string 65if; should be lexed into T_Integer(65) T_If T_Semicolon.
Special notation. The special syntactic symbols (e.g., parentheses, assignment operator, etc.) are as follows.
	; ( ) = == < > <= >= , { } := + * - /
      
Like white space, special notation always separates tokens.
Disambiguation. The rules above are ambiguous. To disambiguate, use the following two policies.
Operate a 'longest match' policy to disambiguate: if the beginning of a string can be lexed in several ways, choose the tokensisation where the initial token removes the most from the beginning of the string.
If there are more than one longest match, give preference to keywords.
For example the string deff should be lexed into a single identifier, not the token def followed by the identifier f. Similarly, === must be == followed by =, not the other way round or three occurences of =.

Syntax description. Here is the language syntax, given by the following context free grammar with initial non-terminal PROG, where ε stands for the empty production.

 PROG → DEC | DEC PROG 
 DEC → def ID (VARDEC) = BLOCK
 VARDEC →  ε | VARDECNE 
 VARDECNE → ID | VARDECNE, ID 
 ID → ... (identifiers)
 INT → ... (Integers)
 BLOCK → { ENE }
 ENE → E | E; ENE
 E →  INT 
   | ID 
   | if E COMP E then BLOCK else BLOCK
   | (E BINOP E)
   | skip
   | BLOCK
   | while E COMP E do BLOCK 
   | repeat BLOCK until E COMP E 
   | ID := E
   | ID (ARGS)
   | break
   | continue
 ARGS → ε | ARGSNE
 ARGSNE → E | ARGSNE, E
 COMP → == | < | > | <= | >=
 BINOP → + | - | * | / 
