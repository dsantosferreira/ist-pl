package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VLazyList;

public class ASTLazyList implements ASTNode {
    ASTNode head, tail;

    public ASTLazyList(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VLazyList(e, this.head, this.tail);
    }

    @Override
    public String toStr() {
        return this.head.toStr() + ":?" +  this.tail.toStr();
    }
}
