package ASTTypes;

import environment.Environment;
import errors.IncompatibleTypes;

import java.util.Objects;

public class ASTTUnit implements ASTType {
    public ASTTUnit() {}

    public String toStr() {
        return "()";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass() == this.getClass();
    }
}