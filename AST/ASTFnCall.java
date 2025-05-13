package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VClosure;

public class ASTFnCall implements ASTNode {
    private final ASTNode l, r;

    public ASTFnCall(ASTNode l, ASTNode r) {
        this.l = l;
        this.r = r;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue lExp = l.eval(e);
        IValue rExp = r.eval(e);

        if (lExp instanceof VClosure lClosure) {
            lClosure = lClosure.initVar(rExp);

            if (lClosure.isInitialized())
                return lClosure.getBody().eval(lClosure.getEnv());
            else
                return lClosure;
        } else {
            throw new InterpreterError("Function call has too many arguments!");
        }
    }
}
