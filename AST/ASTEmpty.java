package AST;

import ASTTypes.ASTTUnit;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VEmpty;

public class ASTEmpty implements ASTNode {
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VEmpty();
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        return new ASTTUnit();
    }
}
