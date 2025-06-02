package AST;

import ASTTypes.ASTTBool;
import ASTTypes.ASTTInt;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VBool;
import values.VInt;

public class ASTLteq implements ASTNode {
    ASTNode exp1, exp2;

    public ASTLteq(ASTNode e1, ASTNode e2) {
        exp1 = e1;
        exp2 = e2;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = exp1.eval(e);

        if (v1 instanceof VInt) {
            IValue v2 = exp2.eval(e);
            if (v2 instanceof VInt)
                return new VBool(((VInt) v1).getVal() <= ((VInt) v2).getVal());
            throw new InterpreterError("Lower than or equal operation expected an integer. Got " + v2.toStr() + " instead");
        }
        throw new InterpreterError("Lower than or equal operation expected an integer. Got " + v1.toStr() + " instead");
    }

    @Override
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType t1 = exp1.typecheck(e);

        if (t1 instanceof ASTTInt) {
            ASTType t2 = exp2.typecheck(e);

            if (t2 instanceof ASTTInt)
                return new ASTTBool();
            else
                throw new TypeCheckError("Illegal type for second operand in <= operation " + t2.toStr());
        } else
            throw new TypeCheckError("Illegal type for first operand in <= operation " + t1.toStr());
    }
}
