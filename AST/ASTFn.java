package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VClosure;

public class ASTFn implements ASTNode {
    private final String var;
    private final ASTNode body;

    public ASTFn(String var, ASTNode body) {
        this.var = var;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VClosure(e, this.var, this.body);
    }
}
