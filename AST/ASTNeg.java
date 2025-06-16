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

public class ASTNeg implements ASTNode {
    ASTNode exp;

	public ASTNeg(ASTNode e)
	{
		exp = e;
	}

    public IValue eval(Environment<IValue> e) throws InterpreterError {
		IValue v0 = exp.eval(e);
		if (v0 instanceof VInt) {
			return new VInt(-((VInt)v0).getVal());
		} else {
			throw new InterpreterError("Negation operation expects an integer. Got " + v0.toStr() + " instead");
		}
    }

	@Override
	public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
		ASTType t = unfold(exp.typecheck(valTypes, idTypes), idTypes);

		if (t instanceof ASTTInt) {
			return t;
		} else
			throw new TypeCheckError("Illegal type for negation operation " + t.toStr());
	}

	private ASTType unfold(ASTType t, Environment<ASTType> e) {
		while (t instanceof ASTTId tId)
			t = e.find(tId.getId());
		return t;
	}
}

