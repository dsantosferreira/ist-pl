package ASTTypes;

import environment.Environment;
import errors.IncompatibleTypes;

public class ASTTInt implements ASTType {
    public String toStr() {
        return "int";
    }

    @Override
    public boolean isSubtypeOf(ASTType other) {
        return other instanceof ASTTInt;
    }

    @Override
    public ASTType getMostGeneral(ASTType other) {
        if (!this.isSubtypeOf(other))
            throw new IncompatibleTypes("Cannot take most general type of " + this.toStr() + " and " + other.toStr());
        return this;
    }

    @Override
    public ASTType reduce(Environment<ASTType> e) {
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


