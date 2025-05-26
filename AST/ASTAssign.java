package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VCell;

public class ASTAssign implements ASTNode {
    ASTNode l, r;

    public ASTAssign(ASTNode l, ASTNode r) {
        this.l = l;
        this.r = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue rVal = r.eval(e);
        IValue lVal = l.eval(e);

        if (lVal instanceof VCell lCell) {
            lCell.storeVal(rVal);
            return rVal;
        } else {
            throw new InterpreterError("Expected left hand side of assignment to reduce to a reference. Got " + lVal.toStr() + " instead");
        }
    }
}
