package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VNil;

public class ASTNil implements ASTNode {
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VNil();
    }
}
