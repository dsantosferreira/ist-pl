package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VBool;

public class ASTAnd implements ASTNode {
    ASTNode exp1, exp2;

    public ASTAnd(ASTNode e1, ASTNode e2) {
        exp1 = e1;
        exp2 = e2;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = exp1.eval(e);

        if (v1 instanceof VBool) {
            IValue v2 = exp2.eval(e);
            if (v2 instanceof VBool)
                return new VBool(((VBool) v1).getVal() && ((VBool) v2).getVal());
            throw new InterpreterError("Logical 'and' operation expected a boolean value. Got " + v2.toStr() + " instead");
        }
        throw new InterpreterError("Logical 'and' operation expected a boolean value. Got " + v1.toStr() + " instead");
    }

    @Override
    public String toStr() {
        return this.exp1.toStr() + " && " + this.exp2.toStr();
    }
}
