package AST;

import environment.Environment;
import errors.InterpreterError;
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
            IValue headVal = vLazyList.getHead().eval(vLazyList.getEnv());
            IValue tailVal = vLazyList.getTail().eval(vLazyList.getEnv());
            newEnv.assoc(id1, headVal);
            newEnv.assoc(id2, tailVal);
            return this.listExpr.eval(newEnv);
        } else {
            throw new InterpreterError("Match operator is expecting a list. Got: " + listVal.toStr() + " instead");
        }
    }

    @Override
    public String toStr() {
        return "match " + this.list.toStr() + "{ nil -> " + this.nilExpr.toStr() + " | " + this.id1 + ":" + this.id2 +
                " -> " + this.listExpr.toStr() + "}; ";
    }
}
