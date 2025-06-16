package AST;

import ASTTypes.ASTTBool;
import ASTTypes.ASTTId;
import ASTTypes.ASTTInt;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
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
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType t = unfold(exp.typecheck(valTypes, idTypes), idTypes);

        if (t instanceof ASTTBool) {
            return t;
        } else
            throw new TypeCheckError("Illegal type for boolean negation operation " + t.toStr());
    }

    private ASTType unfold(ASTType t, Environment<ASTType> e) {
        while (t instanceof ASTTId tId)
            t = e.find(tId.getId());
        return t;
    }
}
