package ASTTypes;

public class ASTTAny implements ASTType {
    @Override
    public String toStr() {
        return "any";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        return obj.getClass() == this.getClass();
    }
}
