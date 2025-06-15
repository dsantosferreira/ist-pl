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

    @Override
    public boolean isSubtypeOf(ASTType other) {
        if (!(other instanceof ASTTList otherT))
            return false;

        return this.elt.isSubtypeOf(otherT.getElt());
    }

    @Override
    public ASTType getMostGeneral(ASTType other) throws IncompatibleTypes {
        if (this.isSubtypeOf(other))
            return other;
        else if (other.isSubtypeOf(this))
            return this;
        throw new IncompatibleTypes("Cannot take most general type of " + this.toStr() + " and " + other.toStr());
    }

    @Override
    public ASTType reduce(Environment<ASTType> e) {
        return new ASTTList(elt.reduce(e));
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
