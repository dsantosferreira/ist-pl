package ASTTypes;

import errors.TypeCheckError;

public class ASTTStruct implements ASTType {
    private final TypeBindList ll;

    public ASTTStruct(TypeBindList llp) {
        ll = llp;
    }

    public ASTType getType(String id) throws TypeCheckError {
        return ll.getType(id);
    }
    
    public String toStr() {
        return "struct { ... }";
    }
}