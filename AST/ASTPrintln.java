package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;

// TODO: Implement output of other values
public class ASTPrintln implements ASTNode {
    ASTNode value;

    public ASTPrintln(ASTNode value) {
        this.value = value;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        System.out.println(this.value.eval(e).toStr());
        return null;
    }
}
