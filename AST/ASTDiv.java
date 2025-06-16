package AST;

import ASTTypes.ASTTId;
import ASTTypes.ASTTInt;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VInt;

public class ASTDiv implements ASTNode {
    ASTNode lhs, rhs;

	public ASTDiv(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
	}

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

	@Override
	public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
		ASTType t1 = unfold(lhs.typecheck(valTypes, idTypes), idTypes);
		if (t1 instanceof ASTTInt) {
			ASTType t2 = unfold(rhs.typecheck(valTypes, idTypes), idTypes);
			if (t2 instanceof ASTTInt) {
				return t1;
			}
			throw new TypeCheckError("Illegal type of second operand in division operation operation: " + t2.toStr());

		}
		throw new TypeCheckError("Illegal type of first operand in division operation operation: " + t1.toStr());
	}

	private ASTType unfold(ASTType t, Environment<ASTType> e) {
		while (t instanceof ASTTId tId)
			t = e.find(tId.getId());
		return t;
	}
}
