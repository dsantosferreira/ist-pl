package AST;

import environment.Environment;
import errors.InterpreterError;
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
                throw new InterpreterError("Condition of while loop must be a boolean value");
            }
        }
    }

    @Override
    public String toStr() {
        return "while " + this.test.toStr() + "{ " + this.body.toStr() + "}; ";
    }
}
