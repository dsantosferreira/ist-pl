package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VBool;
import values.VInt;

public class ASTDif implements ASTNode {
    ASTNode exp1, exp2;

    public ASTDif(ASTNode e1, ASTNode e2) {
        exp1 = e1;
        exp2 = e2;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = exp1.eval(e);

        if (v1 instanceof VInt) {
            IValue v2 = exp2.eval(e);
            if (v2 instanceof VInt)
                return new VBool(((VInt) v1).getVal() != ((VInt) v2).getVal());
            throw new InterpreterError("Inequality operation expected an integer. Got " + v2.toStr() + " instead");
        } else if (v1 instanceof VBool) {
            IValue v2 = exp2.eval(e);
            if (v2 instanceof VBool)
                return new VBool(((VBool) v1).getVal() != ((VBool) v2).getVal());
            throw new InterpreterError("Inequality operation expected a boolean value. Got " + v2.toStr() + " instead");
        }
        throw new InterpreterError("Inequality operation expected either an integer or a boolean value. Got " + v1.toStr() + " instead");
    }
}
