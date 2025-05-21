package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VBool;

public class ASTNot implements ASTNode {
    ASTNode exp;

    public ASTNot(ASTNode e) {exp = e;}

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v = exp.eval(e);
        if (v instanceof VBool) {
            return new VBool(!((VBool) v).getVal());
        } else {
            throw new InterpreterError("Boolean negation operation expected a boolean value. Got " + v.toStr() + " instead");
        }
    }

    @Override
    public String toStr() {
        return "~" + this.exp.toStr();
    }
}
