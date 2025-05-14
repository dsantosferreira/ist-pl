package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VNull;

public class ASTPrint implements ASTNode {
    ASTNode value;

    public ASTPrint(ASTNode value) {
        this.value = value;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        System.out.print(this.value.eval(e).toStr());
        return new VNull();
    }
}
