package AST;

import ASTTypes.ASTTArrow;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VClosure;

public class ASTFn implements ASTNode {
    private final String var;
    private final ASTType varType;
    private final ASTNode body;

    public ASTFn(String var, ASTType varType, ASTNode body) {
        this.var = var;
        this.varType = varType;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VClosure(e, this.var, this.body);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        Environment<ASTType> newEnv = e.beginScope();
        newEnv.assoc(var, varType);
        return new ASTTArrow(varType, body.typecheck(newEnv));
    }
}
