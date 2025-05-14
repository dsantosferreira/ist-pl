package AST;

import environment.Bind;
import environment.Environment;
import errors.InterpreterError;
import values.IValue;

import java.util.List;

public class ASTLet implements ASTNode {
    List<Bind> decls;
    ASTNode body;


    /* TODO: Verificar se isto deveria ser possível (mesmo raciocínio em if/while)
        let f = fn x => {
            let x = 0;
            x
        };
        f(1);;
        returns 0
    */
    public IValue eval(Environment<IValue> e) throws InterpreterError {
	    Environment<IValue> en = e.beginScope();

        for (Bind bind: decls) {
            en.assoc(bind.getId(), bind.getExp().eval(en));
        }

        return body.eval(en);
    }

    public ASTLet(List<Bind> decls, ASTNode b) {
        this.decls = decls;
        this.body = b;
    }
}
