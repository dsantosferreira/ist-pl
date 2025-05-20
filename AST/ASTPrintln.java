package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;

public class ASTPrintln implements ASTNode {
    ASTNode var;

    public ASTPrintln(ASTNode var) {
        this.var = var;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue varValue = this.var.eval(e);
        System.out.println(varValue.toStr());
        return varValue;
    }
}
