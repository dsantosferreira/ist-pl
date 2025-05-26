package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;

public class ASTId implements ASTNode {
    String id;
    
    public ASTId(String id)	{
        this.id = id;
    }

    public IValue eval(Environment<IValue> env)	throws InterpreterError {
        return env.find(id);
    }
}
