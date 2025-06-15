package AST;

import ASTTypes.ASTTBool;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VBool;

public class ASTWhile implements ASTNode {
    ASTNode test, body;

    public ASTWhile(ASTNode test, ASTNode body) {
        this.test = test;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        while (true) {
            IValue testVal = this.test.eval(e);

            if (testVal instanceof VBool testBool) {
                if (testBool.getVal())
                    this.body.eval(e);
                else
                    return new VBool(false);
            } else {
                throw new InterpreterError("While operation expected a boolean value as its condition. Got " + testVal.toStr() + " instead");
            }
        }
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType condType = test.typecheck(valTypes, idTypes);

        if (condType instanceof ASTTBool) {
            return body.typecheck(valTypes, idTypes);
        } else
            throw new TypeCheckError("Invalid type for while statement condition: " + condType.toStr());
    }
}
