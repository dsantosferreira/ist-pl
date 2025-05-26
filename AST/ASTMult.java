package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VInt;

public class ASTMult implements ASTNode {
        ASTNode lhs, rhs;

        public IValue eval(Environment<IValue> e) throws InterpreterError {
            IValue v1 = lhs.eval(e);
            if (v1 instanceof VInt) {
                IValue v2 = rhs.eval(e);
                if (v2 instanceof VInt) {
                    int i1 = ((VInt) v1).getVal();
                    int i2 = ((VInt) v2).getVal();
                    return new VInt(i1 * i2);
                }
                throw new InterpreterError("Product operation expected an integer. Got " + v2.toStr() + " instead");
            }
            throw new InterpreterError("Product operation expected an integer. Got " + v1.toStr() + " instead");
        }

    public ASTMult(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }
}
