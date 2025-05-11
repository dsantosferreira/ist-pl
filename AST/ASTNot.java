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
            throw new InterpreterError("Illegal type for 'not' operatior. Boolean required");
        }
    }
}
