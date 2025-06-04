package ASTTypes;

import errors.TypeCheckError;

import java.util.Set;

public class ASTTUnion implements ASTType {

    private final TypeBindList ll;

    public ASTTUnion(TypeBindList llp) {
        ll = llp;
    }

    public ASTType getType (String id) throws TypeCheckError {
        return ll.getType(id);
    }

    public int numFields() {
        return ll.length();
    }

    public boolean containsField(String id) {
        return ll.containsKey(id);
    }

    public Set<String> getFields() {
        return ll.getKeySet();
    }

    public String toStr() {
        return "union [ ... ]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass())
            return false;

        ASTTUnion other = (ASTTUnion) obj;
        if (ll.length() != other.ll.length())
            return false;

        for (String field: other.ll.getKeySet()) {
            if (!ll.containsKey(field))
                return false;
        }

        return true;
    }
}