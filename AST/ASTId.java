package AST;

import ASTTypes.ASTType;
import environment.Environment;
import errors.EnvironmentError;
import values.IValue;

public class ASTId implements ASTNode {
    String id;
    
    public ASTId(String id)	{
        this.id = id;
    }

    public IValue eval(Environment<IValue> env) throws EnvironmentError {
        return env.find(id);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> e) throws EnvironmentError {
        return e.find(id);
    }
}
