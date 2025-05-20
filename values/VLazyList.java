package values;

import AST.ASTNode;
import environment.Environment;

public class VLazyList implements IValue {
    Environment<IValue> env;
    ASTNode head, tail;

    public VLazyList(Environment<IValue> env, ASTNode head, ASTNode tail) {
        this.env = env;
        this.head = head;
        this.tail = tail;
    }

    public ASTNode getHead() {
        return this.head;
    }

    public ASTNode getTail() {
        return this.tail;
    }

    public Environment<IValue> getEnv() {
        return env;
    }

    // TODO: Print de lazy lists deve avaliar o corpo?
    @Override
    public String toStr() {
        return "???";
    }
}
