package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VInt;

public class ASTNeg implements ASTNode {

    ASTNode exp;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
		IValue v0 = exp.eval(e);
		if (v0 instanceof VInt) {
			return new VInt(-((VInt)v0).getVal());
		} else {
			throw new InterpreterError("illegal types to neg operator");
		}
    }

	public ASTNeg(ASTNode e)
    {
	exp = e;
    }

	@Override
	public String toStr() {
		return "-" + this.exp.toStr();
	}
}

