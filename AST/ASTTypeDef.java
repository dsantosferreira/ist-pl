package AST;

import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;

import java.util.*;

public class ASTTypeDef implements ASTNode {
    HashMap<String, ASTType> ltd;
    ASTNode body;

    public ASTTypeDef(HashMap<String,ASTType> ltdp, ASTNode b) {
        ltd = ltdp;
        body = b;
    }
    
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        return body.eval(e);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        Environment<ASTType> newIdTypesEnv = idTypes.beginScope();

        for (Map.Entry<String, ASTType> entry: ltd.entrySet()) {
            newIdTypesEnv.assoc(entry.getKey(), entry.getValue());
        }

        return body.typecheck(valTypes, newIdTypesEnv);
    }
}
