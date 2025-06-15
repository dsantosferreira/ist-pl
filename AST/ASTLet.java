package AST;

import ASTTypes.ASTType;
import environment.Bind;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;

import java.util.List;

public class ASTLet implements ASTNode {
    private final List<Bind> decls;
    private final List<ASTType> types;
    private final ASTNode body;

    public ASTLet(List<Bind> decls, List<ASTType> types, ASTNode b) {
        this.decls = decls;
        this.types = types;
        this.body = b;
    }

    public IValue eval(Environment<IValue> e) throws InterpreterError {
	    Environment<IValue> en = e.beginScope();

        for (Bind bind: decls) {
            en.assoc(bind.getId(), bind.getExp().eval(en));
        }

        return body.eval(en);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        Environment<ASTType> newValTypesEnv = valTypes.beginScope();

        for (int i = 0; i < decls.size(); i++) {
            if (types.get(i) != null) {
                newValTypesEnv.assoc(decls.get(i).getId(), types.get(i));
                ASTType expType = decls.get(i).getExp().typecheck(newValTypesEnv, idTypes);

                if (!types.get(i).equals(expType))
                    throw new TypeCheckError("Type annotation in variable does not match expression type. Got " + types.get(i).toStr() + " and " + expType.toStr());
            } else {
                newValTypesEnv.assoc(decls.get(i).getId(), decls.get(i).getExp().typecheck(newValTypesEnv, idTypes));
            }
        }

        return body.typecheck(newValTypesEnv, idTypes);
    }
}
