package AST;

import ASTTypes.ASTTRef;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VCell;

public class ASTAssign implements ASTNode {
    ASTNode l, r;

    public ASTAssign(ASTNode l, ASTNode r) {
        this.l = l;
        this.r = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue rVal = r.eval(e);
        IValue lVal = l.eval(e);

        if (lVal instanceof VCell lCell) {
            lCell.storeVal(rVal);
            return rVal;
        } else {
            throw new InterpreterError("Expected left hand side of assignment to reduce to a reference. Got " + lVal.toStr() + " instead");
        }
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType lType = l.typecheck(valTypes, idTypes);

        if (lType instanceof ASTTRef lRef) {
            ASTType rType = r.typecheck(valTypes, idTypes);

            if (lRef.getType().equals(rType)) {
                return lRef.getType();
            } else
                throw new TypeCheckError("Tried to assign value of type " + rType.toStr() + " to variable of type " + lRef.getType().toStr());
        } else
            throw new TypeCheckError("Illegal type for left hand side of assignment operation: " + lType.toStr());
    }
}
