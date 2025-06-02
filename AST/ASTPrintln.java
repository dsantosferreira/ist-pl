package AST;

import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;

public class ASTPrintln implements ASTNode {
    ASTNode var;

    public ASTPrintln(ASTNode var) {
        this.var = var;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue varValue = this.var.eval(e);
        System.out.println(varValue.toStr());
        return varValue;
    }

    @Override
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        return var.typecheck(e);
    }
}
