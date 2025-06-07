package ASTTypes;

import errors.IncompatibleTypes;

public interface ASTType  {
    String toStr();
    boolean isSubtypeOf(ASTType other);
    ASTType getMostGeneral(ASTType other) throws IncompatibleTypes;
}


