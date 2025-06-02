package AST;

import ASTTypes.ASTTBool;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VBool;

public class ASTBool implements ASTNode {
    boolean b;

    public ASTBool(boolean b) {
        this.b = b;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VBool(b);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        return new ASTTBool();
    }
}
