package AST;

import ASTTypes.ASTTStruct;
import ASTTypes.ASTType;
import environment.Environment;
import errors.InterpreterError;
import errors.TypeCheckError;
import values.IValue;
import values.VStruct;

public class ASTDot implements ASTNode {
    private final ASTNode struct;
    private final String id;

    public ASTDot(ASTNode struct, String id) {
        this.struct = struct;
        this.id = id;
    }

    @Override
    public IValue eval(Environment<IValue> e) throws InterpreterError {
        IValue s = this.struct.eval(e);

        if (s instanceof VStruct sStruct) {
            return sStruct.getVal(id);
        } else
            throw new InterpreterError("Expected a struct to apply field " + this.id);
    }

    @Override
    public ASTType typecheck(Environment<ASTType> valTypes, Environment<ASTType> idTypes) throws TypeCheckError {
        ASTType t = struct.typecheck(valTypes, idTypes);

        if (t instanceof ASTTStruct tStruct) {
            return tStruct.getType(id);
        } else
            throw new TypeCheckError("Expected a struct to apply specifier " + id);
    }
}
