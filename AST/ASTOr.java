package AST;

import ASTTypes.ASTTBool;
import ASTTypes.ASTTInt;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VBool;

public class ASTOr implements ASTNode {
    ASTNode exp1, exp2;

    public ASTOr(ASTNode e1, ASTNode e2) {
        exp1 = e1;
        exp2 = e2;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = exp1.eval(e);

        if (v1 instanceof VBool) {
            IValue v2 = exp2.eval(e);
            if (v2 instanceof VBool)
                return new VBool(((VBool) v1).getVal() || ((VBool) v2).getVal());
            throw new InterpreterError("Boolean 'or' operation expected a boolean value. Got " + v2.toStr() + " instead");
        }
        throw new InterpreterError("Boolean 'or' operation expected a boolean value. Got " + v1.toStr() + " instead");
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType t1 = exp1.typecheck(valTypes, idTypes);

        if (t1 instanceof ASTTBool) {
            ASTType t2 = exp2.typecheck(valTypes, idTypes);

            if (t2 instanceof ASTTBool)
                return t1;
            else
                throw new TypeCheckError("Illegal type for second operand in boolean or operation " + t2.toStr());
        } else
            throw new TypeCheckError("Illegal type for first operand in boolean or operation " + t1.toStr());
    }
}
