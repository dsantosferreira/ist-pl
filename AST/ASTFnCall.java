package AST;

import ASTTypes.ASTTArrow;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
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
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType argType = argument.typecheck(e);
        ASTType funcType = func.typecheck(e);

        if (funcType instanceof ASTTArrow funcArrow) {
            if (argType.equals(funcArrow.getDom()))
                return funcArrow.getCodom();
            else
                throw new TypeCheckError("Expected an argument of type " + funcArrow.getDom().toStr() + " to apply to function. Got " + argType.toStr());
        } else
            throw new TypeCheckError("Expected a function to apply to argument of type " + argType.toStr());
    }
}
