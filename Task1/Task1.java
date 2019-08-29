class Task1Exception extends Exception {
    public String msg;
    public Task1Exception ( String _msg ) { msg = _msg; 
    } 
}

interface Language {
    public Boolean decide ( int [] input ) throws Task1Exception; 
}

interface Matrix2D {
    public int initialState ();
    public int terminalState ();
    public int nextState ( int currentState, int character ); }


class Task1 {
    public static Language create ( Matrix2D matrix2D ) { 
        return new Task1Language(matrix2D);
    }
}

class Task1Language implements Language {
    
    private Matrix2D fsa;
    
    public Task1Language(Matrix2D matrix2D) {
        this.fsa = matrix2D;
    }

    @Override
    public Boolean decide(int[] input) throws Task1Exception {
        int initialState = fsa.initialState();
        int terminatingState = fsa.terminalState();
        int currentState = initialState;
        int i = 0;
        while(i < input.length) {
            currentState = fsa.nextState(currentState, input[i]);
            i++;
        }
        return(currentState == terminatingState);
    }
} 