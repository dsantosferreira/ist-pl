package ASTTypes;

import errors.IncompatibleTypes;

public class ASTTArrow implements ASTType {
    ASTType dom;
    ASTType codom;

    public ASTTArrow(ASTType d, ASTType co) {
        dom = d;
        codom = co;
    }

    public String toStr() {
        return dom.toStr()+"->"+codom.toStr();
    }

    @Override
    public boolean isSubtypeOf(ASTType other) {
        if (!(other instanceof ASTTArrow otherT))
            return false;

        return otherT.getDom().isSubtypeOf(this.dom) && this.codom.isSubtypeOf(otherT.getCodom());
    }

    @Override
    public ASTType getMostGeneral(ASTType other) throws IncompatibleTypes {
        if (this.isSubtypeOf(other))
            return other;
        else if (other.isSubtypeOf(this))
            return this;
        throw new IncompatibleTypes("Cannot take most general type of " + this.toStr() + " and " + other.toStr());
    }

    public ASTType getDom() {
        return dom;
    }

    public ASTType getCodom() {
        return codom;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass())
            return false;

        return ((ASTTArrow) obj).getDom().equals(this.dom) && ((ASTTArrow) obj).getCodom().equals(this.codom);
    }
}

