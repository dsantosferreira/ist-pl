package AST;

import ASTTypes.ASTType;
import environment.Bind;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;

import java.util.List;

public class ASTLet implements ASTNode {
    List<Bind> decls;
    ASTNode body;

    public ASTLet(List<Bind> decls, ASTNode b) {
        this.decls = decls;
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
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        Environment<ASTType> newEnv = e.beginScope();

        for (Bind bind: decls) {
            newEnv.assoc(bind.getId(), bind.getExp().typecheck(newEnv));
        }

        return body.typecheck(newEnv);
    }
}
