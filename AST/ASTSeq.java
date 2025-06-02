package AST;

import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;

public class ASTSeq implements ASTNode {
    ASTNode s1, s2;

    public ASTSeq(ASTNode s1, ASTNode s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        s1.eval(e);
        return this.s2.eval(e);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        s1.typecheck(e);
        return s2.typecheck(e);
    }
}
