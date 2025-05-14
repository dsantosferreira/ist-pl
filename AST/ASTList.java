package AST;

import environment.Environment;
import errors.InterpreterError;
import values.IValue;
import values.VList;

public class ASTList implements ASTNode {
    ASTNode head, tail;

    public ASTList(ASTNode head, ASTNode tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue headVal = this.head.eval(e);
        IValue tailVal = this.tail.eval(e);

        // TODO: Check what U = V::L means that V should be int|boolean and L nil|list
        return new VList(headVal, tailVal);
    }
}
