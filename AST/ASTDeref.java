package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VCell;

public class ASTDeref implements ASTNode {
    ASTNode var;

    public ASTDeref(ASTNode var) {
        this.var = var;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue varVal = this.var.eval(e);

        if (varVal instanceof VCell varCell) {
            return varCell.getVal();
        } else {
            throw new InterpreterError("Dereference operation expected a reference. Got " + varVal.toStr() + " instead");
        }
    }

    @Override
    public String toStr() {
        return "!" + this.var.toStr();
    }
}
