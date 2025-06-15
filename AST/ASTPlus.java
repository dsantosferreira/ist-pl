package AST;

import ASTTypes.ASTTBool;
import ASTTypes.ASTTInt;
import ASTTypes.ASTTString;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VInt;
import values.VString;

public class ASTPlus implements ASTNode {
    ASTNode lhs, rhs;

    public ASTPlus(ASTNode l, ASTNode r) {
        lhs = l;
        rhs = r;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue v1 = lhs.eval(e);
        IValue v2 = rhs.eval(e);

        if (v1 instanceof VString || v2 instanceof VString)
            return new VString(v1.toStr() + v2.toStr());

        if (v1 instanceof VInt) {
                if(v2 instanceof VInt) {
                        int i1 = ((VInt) v1).getVal();
                        int i2 = ((VInt) v2).getVal();
                        return new VInt(i1 + i2);
                }
            throw new InterpreterError("Addition operation expected an integer. Got " + v2.toStr() + " instead");
        }
        throw new InterpreterError("Addition operation expected an integer. Got " + v1.toStr() + " instead");
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType t1 = lhs.typecheck(valTypes, idTypes);
        ASTType t2 = rhs.typecheck(valTypes, idTypes);

        if (t1 instanceof ASTTString || t2 instanceof  ASTTString)
            return new ASTTString();

        if (t1 instanceof ASTTInt) {
            if (t2 instanceof ASTTInt)
                return t1;
            else
                throw new TypeCheckError("Illegal type for second operand in addition operation " + t2.toStr());
        } else
            throw new TypeCheckError("Illegal type for first operand in addition operation " + t1.toStr());
    }
}