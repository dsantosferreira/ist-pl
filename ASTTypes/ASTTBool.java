package ASTTypes;

import environment.Environment;
import errors.IncompatibleTypes;

public class ASTTBool implements ASTType {
    public ASTTBool() {
    }

    public String toStr() {
        return "bool";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass() == this.getClass();
    }
}