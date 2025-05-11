package AST;

import environment.Bind;
import environment.Environment;
import errors.InterpreterError;
import values.IValue;

import java.util.List;

public class ASTLet implements ASTNode {
    List<Bind> decls;
    ASTNode body;

    public IValue eval(Environment<IValue> e) throws InterpreterError {
	    Environment<IValue> en = e.beginScope();
	    /*missing code */
        return null;
    }

    public ASTLet(List<Bind> decls, ASTNode b) {
	this.decls = decls;
	body = b;
    }

}
