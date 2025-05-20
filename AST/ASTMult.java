package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VInt;

public class ASTMult implements ASTNode {
        ASTNode lhs, rhs;

        public IValue eval(Environment<IValue> e) throws InterpreterError {
            IValue v1 = lhs.eval(e);
            IValue v2 = rhs.eval(e);
            if (v1 instanceof VInt && v2 instanceof VInt) {
                    int i1 = ((VInt) v1).getVal();
                    int i2 = ((VInt) v2).getVal();
                    return new VInt(i1 * i2);
            } else {
                    throw new InterpreterError("illegal types to * operator");
            }
        }

    public ASTMult(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }

    @Override
    public String toStr() {
        return this.lhs + " * " + this.rhs.toStr();
    }
}
