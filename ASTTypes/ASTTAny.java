package ASTTypes;

import environment.Environment;
import errors.IncompatibleTypes;

public class ASTTAny implements ASTType {
    @Override
    public String toStr() {
        return "any";
    }

    @Override
    public boolean isSubtypeOf(ASTType other) {
        return true;
    }

    @Override
    public ASTType getMostGeneral(ASTType other) throws IncompatibleTypes {
        return other;
    }

    @Override
    public ASTType reduce(Environment<ASTType> e) {
        return this;
    }
}
