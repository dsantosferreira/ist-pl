package values;

import AST.ASTNode;
import environment.Bind;
import environment.Environment;
import errors.InterpreterError;

import java.util.ArrayList;
import java.util.List;

public class VClosure implements IValue {
    private final Environment<IValue> env;
    private final List<Bind> uninitializedVars;
    private final ASTNode body;

    public VClosure(Environment<IValue> env, List<Bind> uninitializedVars, ASTNode body) {
        this.env = env;
        this.uninitializedVars = uninitializedVars;
        this.body = body;
    }

    // TODO: Clarify what is the string representation of a closure
    @Override
    public String toStr() {
        return "A function";
    }

    public Environment<IValue> getEnv() {
        return this.env;
    }

    public ASTNode getBody() {
        return this.body;
    }

    // Checks if all variables of the function have been initialized
    public boolean isInitialized() {
        return this.uninitializedVars.isEmpty();
    }

    public VClosure initVar(IValue val) throws InterpreterError {
        if (this.isInitialized())
            throw new InterpreterError("Tried to initialize a variable with value " + val.toStr() + " in an initialized function");
        Environment<IValue> newEnv = env.beginScope();
        newEnv.assoc(this.uninitializedVars.getFirst().getId(), val);

        ArrayList<Bind> newVars = new ArrayList<>(this.uninitializedVars);
        newVars.removeFirst();
        return new VClosure(newEnv, newVars, body);
    }
}
