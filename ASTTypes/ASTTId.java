package ASTTypes;

import errors.IncompatibleTypes;

public class ASTTId implements ASTType	{
    String id;
    
    public ASTTId(String id)	{
        this.id = id;
    }
    public String toStr() {
        return id;
    }

    @Override
    public boolean isSubtypeOf(ASTType other) {
        return false;
    }

    @Override
    public ASTType getMostGeneral(ASTType other) throws IncompatibleTypes {
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass() == this.getClass();
    }
}
