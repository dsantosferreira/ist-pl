package AST;

import environment.Bind;
import environment.Environment;
import errors.InterpreterError;
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
    public String toStr() {
        StringBuilder code = new StringBuilder();

        for (Bind bind : decls) {
            code.append("let ").append(bind.getId()).append(" = ").append(bind.getExp().toStr()).append("; ");
        }

        return code.append(this.body.toStr()).toString();
    }
}
