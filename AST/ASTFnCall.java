package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VClosure;

public class ASTFnCall implements ASTNode {
    private final ASTNode func, argument;

    public ASTFnCall(ASTNode func, ASTNode argument) {
        this.func = func;
        this.argument = argument;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue funcExp = this.func.eval(e);

        if (funcExp instanceof VClosure funcExpClosure) {
            IValue argumentExp = this.argument.eval(e);
            Environment<IValue> funcEnv = funcExpClosure.getEnv().beginScope();
            funcEnv.assoc(funcExpClosure.getVar(), argumentExp);
            return funcExpClosure.getBody().eval(funcEnv);
        } else {
            throw new InterpreterError("Application operation expected a closure in the left hand side. Got " + funcExp.toStr() + " instead");
        }
    }

    @Override
    public String toStr() {
        return this.func.toStr() + "(" + this.argument.toStr() + ")";
    }
}
