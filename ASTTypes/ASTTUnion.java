package ASTTypes;

import errors.TypeCheckError;

import java.util.HashMap;
import java.util.Map;
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

    public Set<String> getFields() {
        return ll.getKeySet();
    }

    public TypeBindList getTypeBindList() {
        return this.ll;
    }

    public String toStr() {
        StringBuilder str = new StringBuilder("union [ ");
        HashMap<String, ASTType> typebl = this.ll.getMap();

        for (Map.Entry<String, ASTType> entry: typebl.entrySet()) {
            str.append(entry.getKey()).append(":").append(entry.getValue().toStr()).append(", ");
        }

        if (!typebl.isEmpty())
            str.delete(str.length()-2, str.length());
        str.append(" ]");

        return str.toString();
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