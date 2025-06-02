package AST;

import ASTTypes.ASTTBool;
import ASTTypes.ASTTInt;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VInt;

public class ASTMult implements ASTNode {
    ASTNode lhs, rhs;

    public ASTMult(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = lhs.eval(e);
        if (v1 instanceof VInt) {
            IValue v2 = rhs.eval(e);
            if (v2 instanceof VInt) {
                int i1 = ((VInt) v1).getVal();
                int i2 = ((VInt) v2).getVal();
                return new VInt(i1 * i2);
            }
            throw new InterpreterError("Product operation expected an integer. Got " + v2.toStr() + " instead");
        }
        throw new InterpreterError("Product operation expected an integer. Got " + v1.toStr() + " instead");
    }

    @Override
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType t1 = lhs.typecheck(e);

        if (t1 instanceof ASTTInt) {
            ASTType t2 = rhs.typecheck(e);

            if (t2 instanceof ASTTInt)
                return new ASTTInt();
            else
                throw new TypeCheckError("Illegal type for second operand in multiplication operation " + t2.toStr());
        } else
            throw new TypeCheckError("Illegal type for first operand in multiplication operation " + t1.toStr());
    }
}
