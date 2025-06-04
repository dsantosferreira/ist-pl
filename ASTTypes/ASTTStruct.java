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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass())
            return false;

        ASTTStruct other = (ASTTStruct) obj;
        if (ll.length() != other.ll.length())
            return false;

        for (String field: other.ll.getKeySet()) {
            if (!ll.containsKey(field))
                return false;
        }

        return true;
    }
}