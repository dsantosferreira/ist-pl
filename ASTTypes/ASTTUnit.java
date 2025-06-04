package ASTTypes;

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