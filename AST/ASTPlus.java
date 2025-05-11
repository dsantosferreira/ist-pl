package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VInt;

public class ASTPlus implements ASTNode {
    ASTNode lhs, rhs;
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = lhs.eval(e);
        if (v1 instanceof VInt) {
                IValue v2 = rhs.eval(e);
                if(v2 instanceof VInt) {
                        int i1 = ((VInt) v1).getVal();
                        int i2 = ((VInt) v2).getVal();
                        return new VInt(i1 + i2);
                }

        }
        throw new InterpreterError("illegal types to + operator");
    }

    public ASTPlus(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }
}