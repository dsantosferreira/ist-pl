package ASTTypes;

import errors.TypeCheckError;

import java.util.*;

public class TypeBindList  {
    private final HashMap<String,ASTType> lbl;

    public TypeBindList(HashMap<String,ASTType> ll) {
        lbl = ll;
    }

    public HashMap<String, ASTType> getMap() {
        return this.lbl;
    }

    public boolean containsKey(String id) {
        return lbl.containsKey(id);
    }

    public ASTType getType(String id) throws TypeCheckError {
        if (!containsKey(id))
            throw new TypeCheckError("Struct/Union does not contain field with identifier " + id);

        return lbl.get(id);
    }

    public int length() {
        return lbl.size();
    }

    public Set<String> getKeySet() {
        return lbl.keySet();
    }
}