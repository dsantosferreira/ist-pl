package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VCell;

public class ASTBox implements ASTNode {
    ASTNode val;

    public ASTBox(ASTNode val) {
        this.val = val;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VCell(val.eval(e));
    }

    @Override
    public String toStr() {
        return "box(" + this.val.toStr() + ")";
    }
}
