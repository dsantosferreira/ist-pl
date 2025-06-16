package ASTTypes;

import environment.Environment;
import errors.IncompatibleTypes;

public class ASTTString implements ASTType {
    public ASTTString() {}

    public String toStr() {
        return "string";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass() == this.getClass();
    }
}
