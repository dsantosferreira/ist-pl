package ASTTypes;

import environment.Environment;
import errors.IncompatibleTypes;

public class ASTTUnit implements ASTType {
    public ASTTUnit() {}

    public String toStr() {
        return "()";
    }

    // TODO: Think about this
    @Override
    public boolean isSubtypeOf(ASTType other) {
        return other instanceof ASTTUnit;
    }

    @Override
    public ASTType getMostGeneral(ASTType other) throws IncompatibleTypes {
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