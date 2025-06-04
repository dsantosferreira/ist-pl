package AST;

import ASTTypes.ASTTList;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VLazyList;
import values.VList;
import values.VNil;

public class ASTMatch implements ASTNode {
    ASTNode list, nilExpr, listExpr;
    String id1, id2;

    public ASTMatch(ASTNode list, ASTNode nilExpr, String id1, String id2, ASTNode listExpr) {
        this.list = list;
        this.nilExpr = nilExpr;
        this.id1 = id1;
        this.id2 = id2;
        this.listExpr = listExpr;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue listVal = this.list.eval(e);

        if (listVal instanceof VNil) {
            return this.nilExpr.eval(e);
        } else if (listVal instanceof VList vList) {
            Environment<IValue> newEnv = e.beginScope();
            newEnv.assoc(id1, vList.getHead());
            newEnv.assoc(id2, vList.getTail());
            return this.listExpr.eval(newEnv);
        } else if (listVal instanceof VLazyList vLazyList) {
            Environment<IValue> newEnv = e.beginScope();
            newEnv.assoc(id1, vLazyList.getHead().eval(vLazyList.getEnv()));
            newEnv.assoc(id2, vLazyList.getTail().eval(vLazyList.getEnv()));
            return this.listExpr.eval(newEnv);
        } else {
            throw new InterpreterError("Match operator expected a list or a nil value. Got " + listVal.toStr() + " instead");
        }
    }

    // TODO: need to handle case where list is totally empty. Empty list should have the most generic type possible!
    @Override
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        ASTType listType = list.typecheck(e);

        if (listType instanceof ASTTList listT) {
            ASTType nilType = nilExpr.typecheck(e);

            Environment<ASTType> newEnv = e.beginScope();
            newEnv.assoc(id1, listT.getElt());
            newEnv.assoc(id2, listT);

            ASTType listExprType = listExpr.typecheck(newEnv);

            if (nilType.equals(listExprType))
                return nilType;
            else
                throw new TypeCheckError("Types of both cases of match construct must be the same. Got: " + nilType.toStr() + " and " + listExprType.toStr());
        } else
            throw new TypeCheckError("Invalid type for match construct. Expected list, got: " + listType.toStr());
    }
}
