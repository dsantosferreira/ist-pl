package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VInt;

public class ASTSub implements ASTNode {

    ASTNode lhs, rhs;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
		IValue v1 = lhs.eval(e);
		IValue v2 = rhs.eval(e);
		if (v1 instanceof VInt && v2 instanceof VInt) {
			return new VInt(((VInt) v1).getVal() - ((VInt) v2).getVal());
		} else {
			throw new InterpreterError("illegal types to + operator");
		}
    }

    public ASTSub(ASTNode l, ASTNode r) {
		lhs = l;
		rhs = r;
    }

}
