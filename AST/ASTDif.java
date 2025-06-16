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

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType t1 = unfold(exp1.typecheck(valTypes, idTypes), idTypes);

        if (t1 instanceof ASTTInt) {
            ASTType t2 = unfold(exp2.typecheck(valTypes, idTypes), idTypes);
            if (t2 instanceof ASTTInt)
                return new ASTTBool();
            throw new TypeCheckError("Illegal type of second operand in inequality between two integers: " + t2.toStr());
        } else if (t1 instanceof ASTTBool) {
            ASTType t2 = unfold(exp2.typecheck(valTypes, idTypes), idTypes);
            if (t2 instanceof ASTTBool)
                return t1;
            throw new TypeCheckError("Illegal type of second operand in inequality between two boolean values: " + t2.toStr());
        }
        throw new TypeCheckError("Illegal type of first operand in inequality operation: " + t1.toStr());
    }

    private ASTType unfold(ASTType t, Environment<ASTType> e) {
        while (t instanceof ASTTId tId)
            t = e.find(tId.getId());
        return t;
    }
}
