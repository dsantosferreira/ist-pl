package AST;

import ASTTypes.ASTTBool;
import ASTTypes.ASTTId;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
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
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType t1 = unfold(exp1.typecheck(valTypes, idTypes), idTypes);

        if (t1 instanceof ASTTBool) {
            ASTType t2 = unfold(exp2.typecheck(valTypes, idTypes), idTypes);

            if (t2 instanceof ASTTBool)
                return t1;
            else
                throw new TypeCheckError("Illegal type of second operator in 'and' operation: " + t2.toStr());
        } else {
            throw new TypeCheckError("Illegal type of first operator in 'and' operation: " + t1.toStr());
        }
    }

    private ASTType unfold(ASTType t, Environment<ASTType> e) {
        while (t instanceof ASTTId tId)
            t = e.find(tId.getId());
        return t;
    }
}
