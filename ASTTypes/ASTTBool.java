package ASTTypes;

import errors.IncompatibleTypes;

public class ASTTBool implements ASTType {
    public ASTTBool() {
    }

    public String toStr() {
        return "bool";
    }

    @Override
    public boolean isSubtypeOf(ASTType other) {
        return other instanceof ASTTBool;
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