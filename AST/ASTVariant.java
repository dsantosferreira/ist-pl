package AST;

import ASTTypes.ASTTUnion;
import ASTTypes.ASTType;
import ASTTypes.TypeBindList;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VOption;

import java.util.HashMap;

public class ASTVariant implements ASTNode {
    private final String id;
    private final ASTNode expr;

    public ASTVariant(String id, ASTNode expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VOption(id, expr.eval(e));
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        HashMap<String, ASTType> typeBinds = new HashMap<>();
        typeBinds.put(id, expr.typecheck(valTypes, idTypes));

        return new ASTTUnion(new TypeBindList(typeBinds));
    }
}
