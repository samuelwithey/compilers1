import java.util.List;
import java.util.ArrayList;
import java.util.regex.*;

public class TokenLexer implements Lexer{

    @Override
    public List<Token> lex(String input) throws LexicalException, Task2Exception {
        List<Token> tokenList = new ArrayList<>();
        ArrayList<String> stringTokenList = new ArrayList<>();
        String regex = "\\w+|\\d+|\\{|\\}|==|=|;|\\(|\\)|<=|>=|<|>|,|\\{|\\}|:=|\\+|\\*|\\-";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        Pattern numbers = Pattern.compile("[0-9]*");
        Pattern strings = Pattern.compile("[a-z][\\w]+");
        
        while(m.find()) {
            stringTokenList.add(m.group());
        }
        
        for (String x : stringTokenList) {
            switch (x) {
                case ";":
                    tokenList.add(new T_Semicolon());
                    break;
                case "(":
                    tokenList.add(new T_LeftBracket());
                    break;
                case ")":
                    tokenList.add(new T_RightBracket());
                    break;
                case "=":
                    tokenList.add(new T_EqualDefines());
                    break;
                case "==":
                    tokenList.add(new T_Equal());
                    break;
                case "<":
                    tokenList.add(new T_LessThan());
                    break;
                case ">":
                    tokenList.add(new T_GreaterThan());
                    break;
                case "<=":
                    tokenList.add(new T_LessEq());
                    break;
                case ">=":
                    tokenList.add(new T_GreaterEq());
                    break;
                case ",":
                    tokenList.add(new T_Comma());
                    break;
                case "{":
                    tokenList.add(new T_LeftCurlyBracket());
                    break;
                case "}":
                    tokenList.add(new T_RightCurlyBracket());
                    break;
                case ":=":
                    tokenList.add(new T_Assign());
                    break;
                case "+":
                    tokenList.add(new T_Plus());
                    break;
                case "*":
                    tokenList.add(new T_Times());
                    break;
                case "-":
                    tokenList.add(new T_Minus());
                    break;
                case "/":
                    tokenList.add(new T_Div());
                    break;
                case "def":
                    tokenList.add(new T_Def());
                    break;
                case "skip":
                    tokenList.add(new T_Skip());
                    break;
                case "if":
                    tokenList.add(new T_If());
                    break;
                case "then":
                    tokenList.add(new T_Then());
                    break;
                case "else":
                    tokenList.add(new T_Else());
                    break;
                case "while":
                    tokenList.add(new T_While());
                    break;
                case "do":
                    tokenList.add(new T_Do());
                    break;
                case "repeat":
                    tokenList.add(new T_Repeat());
                    break;
                case "until":
                    tokenList.add(new T_Until());
                    break;
                case "break":
                    tokenList.add(new T_Break());
                    break;
                case "continue":
                    tokenList.add(new T_Continue());
                    break;
                default:
                    Matcher numbersMatch = numbers.matcher(x);
                    Matcher stringMatch = strings.matcher(x);
                    
                    boolean num = numbersMatch.matches();
                    boolean str = stringMatch.matches();
                    if(num) {
                        int number = Integer.parseInt(x);
                        tokenList.add(new T_Integer(number));
                    } else if(str) {
                        tokenList.add(new T_Identifier(x));
                    } else {
                        throw(new LexicalException("Error lexing input"));
                    }
            }
        }
        
        return tokenList;
    }
    
}
