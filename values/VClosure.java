package values;

import AST.ASTNode;
import environment.Environment;

public class VClosure implements IValue {
    Environment<IValue> env;
    String var;
    ASTNode body;

    public VClosure(Environment<IValue> env, String var, ASTNode body) {
        this.env = env;
        this.var = var;
        this.body = body;
    }

    public Environment<IValue> getEnv() {
        return env;
    }

    public String getVar() {
        return var;
    }

    public ASTNode getBody() {
        return body;
    }

    @Override
    public String toStr() {
        return "lambda@" + this;
    }
}
