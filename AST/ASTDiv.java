package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VInt;

public class ASTDiv implements ASTNode {
    ASTNode lhs, rhs;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
		IValue v1 = lhs.eval(e);
		if (v1 instanceof VInt) {
			IValue v2 = rhs.eval(e);
			if (v2 instanceof VInt) {
				int i1 = ((VInt) v1).getVal();
				int i2 = ((VInt) v2).getVal();
				return new VInt(i1 / i2);
			}
			throw new InterpreterError("Division operation expected an integer. Got " + v2.toStr() + " instead");
		}
		throw new InterpreterError("Division operation expected an integer. Got " + v1.toStr() + " instead");
    }

	public ASTDiv(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
    }

	@Override
	public String toStr() {
		return this.lhs.toStr() + " / " + this.rhs.toStr();
	}
}
