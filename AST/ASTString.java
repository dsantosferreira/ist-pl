package AST;

import ASTTypes.ASTTString;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VString;

public class ASTString implements ASTNode {
    private final String val;

    public ASTString(String val) {
        this.val = val.substring(1, val.length()-1);
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VString(val);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        return new ASTTString();
    }
}
