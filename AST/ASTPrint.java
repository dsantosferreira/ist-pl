package AST;

import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;

public class ASTPrint implements ASTNode {
    ASTNode var;

    public ASTPrint(ASTNode var) {
        this.var = var;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue varValue = this.var.eval(e);
        System.out.print(varValue.toStr());
        return varValue;
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        return var.typecheck(valTypes, idTypes);
    }
}
