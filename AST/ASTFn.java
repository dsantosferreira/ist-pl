package AST;

import ASTTypes.ASTTArrow;
import ASTTypes.ASTTId;
import ASTTypes.ASTTUnit;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VClosure;

public class ASTFn implements ASTNode {
    private final String var;
    private final ASTType varType;
    private final ASTNode body;

    public ASTFn(String var, ASTType varType, ASTNode body) {
        this.var = var;
        this.varType = varType;
        this.body = body;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return new VClosure(e, this.var, this.body);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        Environment<ASTType> newValTypesEnv = valTypes.beginScope();
        Environment<ASTType> newIdTypesEnv = idTypes.beginScope();

        ASTType realType = unfold(varType, idTypes);

        if (var.equals("_")) {
            if (!(realType instanceof ASTTUnit))
                throw new TypeCheckError("Empty variable must have unit type");
        } else
            newValTypesEnv.assoc(var, realType);
        return new ASTTArrow(realType, body.typecheck(newValTypesEnv, newIdTypesEnv));
    }

    private ASTType unfold(ASTType t, Environment<ASTType> e) {
        while (t instanceof ASTTId tId)
            t = e.find(tId.getId());
        return t;
    }
}
