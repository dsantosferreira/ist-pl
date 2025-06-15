package AST;

import ASTTypes.ASTTInt;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VInt;

public class ASTInt implements ASTNode  {
    int v;

    public ASTInt(int v0) {
        v = v0;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
	    return new VInt(v);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        return new ASTTInt();
    }
}
