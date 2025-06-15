package AST;

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
        // TODO: Check to see if this makes sense. If not, there needs to be a type that is a subtype of any type!
        // TODO: Change this to return a type that represents the most generic type possible
        return new ASTTList(new ASTTUnit());
    }
}
