package AST;

import ASTTypes.ASTTRef;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VCell;

public class ASTDeref implements ASTNode {
    ASTNode var;

    public ASTDeref(ASTNode var) {
        this.var = var;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue varVal = this.var.eval(e);

        if (varVal instanceof VCell varCell) {
            return varCell.getVal();
        } else {
            throw new InterpreterError("Dereference operation expected a reference. Got " + varVal.toStr() + " instead");
        }
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType t = var.typecheck(valTypes, idTypes);

        if (t instanceof ASTTRef tRef) {
            return tRef.getType();
        } else
            throw new TypeCheckError("Illegal type for dereference operation: " + t.toStr());
    }
}
