package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VInt;

public class ASTSub implements ASTNode {
    ASTNode lhs, rhs;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
		IValue v1 = lhs.eval(e);
		if (v1 instanceof VInt) {
			IValue v2 = rhs.eval(e);
			if (v2 instanceof VInt) {
				return new VInt(((VInt) v1).getVal() - ((VInt) v2).getVal());
			}
			throw new InterpreterError("Subtraction operation expected an integer. Got " + v2.toStr() + " instead");
		}
		throw new InterpreterError("Subtraction operation expected an integer. Got " + v1.toStr() + " instead");
    }

	public ASTSub(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
    }
}
