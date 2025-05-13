package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VBool;

public class ASTIf implements ASTNode {
    ASTNode test, btrue, bfalse;

    public ASTIf(ASTNode test, ASTNode btrue, ASTNode bfalse) {
        this.test = test;
        this.btrue = btrue;
        this.bfalse = bfalse;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue testVal = test.eval(e);

        if (testVal instanceof VBool testBool) {
            if (testBool.getVal())
                return btrue.eval(e);
            else
                return bfalse.eval(e);
        } else {
            throw new InterpreterError("Condition of if statement should be a boolean value");
        }
    }
}
