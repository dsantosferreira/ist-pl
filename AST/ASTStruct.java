package AST;

import ASTTypes.ASTTStruct;
import ASTTypes.ASTType;
import ASTTypes.TypeBindList;
import environment.Bind;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VStruct;

import java.util.HashMap;
import java.util.List;

public class ASTStruct implements ASTNode {
    private final List<Bind> decls;

    public ASTStruct(List<Bind> decls) {
        this.decls = decls;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        HashMap<String, IValue> structVals = new HashMap<>();

        for (Bind decl: decls) {
            structVals.put(decl.getId(), decl.getExp().eval(e));
        }

        return new VStruct(structVals);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> e) throws TypeCheckError {
        HashMap<String, ASTType> typeBinds = new HashMap<>();

        for (Bind decl: decls) {
            typeBinds.put(decl.getId(), decl.getExp().typecheck(e));
        }

        return new ASTTStruct(new TypeBindList(typeBinds));
    }
}
