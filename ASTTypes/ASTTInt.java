package ASTTypes;

import environment.Environment;
import errors.IncompatibleTypes;

public class ASTTInt implements ASTType {
    public String toStr() {
        return "int";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass() == this.getClass();
    }
}


