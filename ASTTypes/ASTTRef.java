package ASTTypes;

import environment.Environment;
import errors.IncompatibleTypes;

public class ASTTRef implements ASTType {
    private final ASTType type;

    public ASTTRef(ASTType type) {
        this.type = type;
    }
    
    public ASTType getType() {
        return type;
    }
    public String toStr() {
        return "ref<"+type.toStr()+">";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass())
            return false;

        return ((ASTTRef) obj).getType().equals(this.type);
    }
}