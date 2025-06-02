package AST;

import ASTTypes.ASTTList;
import ASTTypes.ASTTUnit;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
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
        return new VList(headVal, tailVal);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType headType = head.typecheck(e);
        ASTType tailType = tail.typecheck(e);

        if (tailType instanceof ASTTList tailList) {
            if ((headType.getClass().equals(tailList.getElt().getClass())) || tailList.getElt() instanceof ASTTUnit)
                return new ASTTList(headType);
            else
                throw new TypeCheckError("Type of head of list must match the tail of its tail. Got head with type " + headType.toStr() + " and tail of type " + tailType.toStr());
        } else
            throw new TypeCheckError("Tail of a list must be a list. Got " + tailType.toStr());
    }
}
