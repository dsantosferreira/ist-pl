package AST;

import environment.Bind;
import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VClosure;

import java.util.List;

public class ASTFn implements ASTNode {
    private final List<Bind> vars;
    private final ASTNode body;

    public ASTFn(List<Bind> vars, ASTNode body) {
        this.vars = vars;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VClosure(e, this.vars, this.body);
    }
}
