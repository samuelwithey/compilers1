import java.util.List;
import java.util.ArrayList;
import static java.util.Arrays.asList;

public class Parse implements Parser{
    private List<Exp> expressionList = new ArrayList<>();
    private List<Block> blocks = new ArrayList<>();
    private int numOfBlocks = 0;
    private Skip placeHolder = new Skip();
    private int rightBrackets = 0;
    private int leftBrackets = 0;
    
    @Override
    public Block parse(List<Token> input) throws SyntaxException, Task3Exception { 
        ParseBlock(input);
        return new Block(expressionList); 
    }
    
    
    public void ParseBlock(List<Token> input) throws SyntaxException, Task3Exception {
        if(!input.isEmpty()) {
            if(input.get(0) instanceof T_LeftCurlyBracket && input.get(1) instanceof T_RightCurlyBracket) {
             throw new SyntaxException("Error");
            } else if(input.get(0) instanceof T_Integer) {
                throw new SyntaxException("Error");
            }
            if(input.get(0).getClass() == T_LeftCurlyBracket.class) {
                leftBrackets++;
                input.remove(0);
                if(!expressionList.isEmpty()) {
                    numOfBlocks++;
                    blocks.add(new Block(expressionList));
                    expressionList = new ArrayList<>();
                } 
                ParseENE(input);
            } else if(input.get(0) instanceof T_RightCurlyBracket) {
                rightBrackets++;
                input.remove(0);
                if(!blocks.isEmpty()) {
                    numOfBlocks--;
                    if(numOfBlocks >= 0 ) {
                        BlockExp block = new BlockExp(new Block(expressionList));
                        expressionList = new ArrayList<>();
                        expressionList = blocks.remove(numOfBlocks).exps;
                        if(expressionList.contains(placeHolder)) {
                            expressionList.remove(placeHolder);
                        } expressionList.add(block);
                    }
                }
                if(input.isEmpty()) {
                    BlockExp block = new BlockExp(new Block(expressionList));
                    expressionList = new ArrayList<>();
                    expressionList.add(block);
                } else {
                    ParseENE(input);
                }
            }
        } else {
            throw new Task3Exception("Error");
        }
        
    }  
    
    private void ParseENE(List<Token> input) throws SyntaxException, Task3Exception {
        if(!input.isEmpty() && rightBrackets - leftBrackets != 0) {
            if(input.get(0) instanceof T_Integer) {
            T_Integer t = (T_Integer) input.remove(0);
            expressionList.add(new IntLiteral(t.n));
            if(!input.isEmpty()) {
                    ParseE(input);
                }
            }  else if(input.get(0) instanceof T_Skip) {
                expressionList.add(new Skip());
                input.remove(0);
                ParseE(input);
            } else if(input.get(0) instanceof T_Semicolon) {
                input.remove(0);
                ParseENE(input);
            } else if(input.get(0) instanceof T_LeftCurlyBracket) {
                if(expressionList.isEmpty()) {
                    numOfBlocks++;
                    expressionList.add(placeHolder);
                    blocks.add(new Block(expressionList));
                    expressionList = new ArrayList<>();
                } 
                ParseBlock(input);
            } else if(input.get(0) instanceof T_RightCurlyBracket) {
                ParseBlock(input);
            }
        } else {
            throw new SyntaxException("Error");
        }
    }
    
    
    public void ParseE(List<Token> input) throws SyntaxException, Task3Exception {
        if(input.get(0) instanceof T_Semicolon) {
            input.remove(0);
            ParseENE(input);
        }
        
        if(input.get(0) instanceof T_RightCurlyBracket) {
            ParseBlock(input);
        }
    }

}