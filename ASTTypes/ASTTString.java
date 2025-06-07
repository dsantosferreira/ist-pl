package ASTTypes;

import errors.IncompatibleTypes;

public class ASTTString implements ASTType {
    public ASTTString() {}

    public String toStr() {
        return "string";
    }

    @Override
    public boolean isSubtypeOf(ASTType other) {
        return other instanceof ASTTString;
    }

    @Override
    public ASTType getMostGeneral(ASTType other) {
        if (!this.isSubtypeOf(other))
            throw new IncompatibleTypes("Cannot take most general type of " + this.toStr() + " and " + other.toStr());
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass() == this.getClass();
    }
}
