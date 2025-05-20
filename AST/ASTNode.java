package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;

public interface ASTNode {
    IValue eval(Environment<IValue> e) throws InterpreterError;

    String toStr();
}

