package AST;

import ASTTypes.ASTTList;
import ASTTypes.ASTTUnit;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
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
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType headType = head.typecheck(valTypes, idTypes);
        ASTType tailType = tail.typecheck(valTypes, idTypes);

        if (tailType instanceof ASTTList tailList) {
            // TODO: same thing as ASTList
            if ((headType.isSubtypeOf(tailList.getElt()) || tailList.getElt().isSubtypeOf(headType)))
                return new ASTTList(headType.getMostGeneral(tailList.getElt()));
            else
                throw new TypeCheckError("Type of head of list must match the tail of its tail. Got head with type " + headType.toStr() + " and tail of type " + tailType.toStr());
        } else
            throw new TypeCheckError("Tail of a list must be a list. Got " + tailType.toStr());
    }
}
