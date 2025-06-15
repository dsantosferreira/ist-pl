package AST;

import ASTTypes.ASTTRef;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VCell;

public class ASTBox implements ASTNode {
    ASTNode val;

    public ASTBox(ASTNode val) {
        this.val = val;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VCell(val.eval(e));
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType t = val.typecheck(valTypes, idTypes);
        return new ASTTRef(t);
    }
}
