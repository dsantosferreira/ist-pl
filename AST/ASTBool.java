package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VBool;

public class ASTBool {
    boolean b;

    ASTBool(boolean b) {
        this.b = b;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VBool(b);
    }
}
