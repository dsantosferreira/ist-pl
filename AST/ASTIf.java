package AST;

import ASTTypes.ASTTBool;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
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
            throw new InterpreterError("If expression expected a boolean value in its condition. Got " + testVal.toStr() + " instead");
        }
    }

    @Override
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType condType = test.typecheck(e);

        if (condType instanceof ASTTBool) {
            ASTType tType = btrue.typecheck(e);
            ASTType fType = bfalse.typecheck(e);

            if (tType.equals(fType))
                return tType;
            else
                throw new TypeCheckError("Types of true and false branch of is statement must match. Got: " + tType.toStr() + " and " + fType.toStr());
        } else
            throw new TypeCheckError("Illegal type for condition in if construction: " + condType.toStr());
    }
}
