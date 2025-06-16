package AST;

import ASTTypes.ASTTBool;
import ASTTypes.ASTTId;
import ASTTypes.ASTTInt;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
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

	@Override
	public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
		ASTType t1 = unfold(lhs.typecheck(valTypes, idTypes), idTypes);

		if (t1 instanceof ASTTInt) {
			ASTType t2 = unfold(rhs.typecheck(valTypes, idTypes), idTypes);

			if (t2 instanceof ASTTInt)
				return t1;
			else
				throw new TypeCheckError("Illegal type for second operand in subtraction operation " + t2.toStr());
		} else
			throw new TypeCheckError("Illegal type for first operand in subtraction operation " + t1.toStr());
	}

	private ASTType unfold(ASTType t, Environment<ASTType> e) {
		while (t instanceof ASTTId tId)
			t = e.find(tId.getId());
		return t;
	}
}
