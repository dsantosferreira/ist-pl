package ASTTypes;

import environment.Environment;
import errors.IncompatibleTypes;

public class ASTTList implements ASTType {
    private final ASTType elt;

    public ASTTList(ASTType eltt)
    {
        elt = eltt;
    }
    
    public String toStr() {
        return "list<"+elt.toStr()+">";
    }

    public ASTType getElt() {
        return elt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass())
            return false;

        return ((ASTTList) obj).getElt().equals(this.elt);
    }
}
