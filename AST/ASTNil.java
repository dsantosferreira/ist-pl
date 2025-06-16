package AST;

import ASTTypes.ASTTAny;
import ASTTypes.ASTTList;
import ASTTypes.ASTTUnit;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VNil;

public class ASTNil implements ASTNode {
    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VNil();
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        return new ASTTList(new ASTTAny());
    }
}
