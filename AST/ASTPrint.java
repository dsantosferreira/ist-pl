package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;

public class ASTPrint implements ASTNode {
    ASTNode var;

    public ASTPrint(ASTNode var) {
        this.var = var;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue varValue = this.var.eval(e);
        System.out.print(varValue.toStr());
        return varValue;
    }

    @Override
    public String toStr() {
        return "print(" + this.var.toStr() + "); ";
    }
}
