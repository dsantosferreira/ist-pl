package ASTTypes;

import java.util.Objects;

public class TypePair {
    ASTType t1, t2;

    public TypePair(ASTType t1, ASTType t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public ASTType getFst() {
        return t1;
    }

    public ASTType getSnd() {
        return t2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass())
            return false;

        TypePair other = (TypePair) obj;

        return t1.equals(other.getFst()) && t2.equals(other.getSnd());
    }
}
