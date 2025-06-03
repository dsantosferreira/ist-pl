package ASTTypes;

public class ASTTUnion implements ASTType {

    private final TypeBindList ll;

    public ASTTUnion(TypeBindList llp) {
        ll = llp;
    }

    public String toStr() {
        return "union [ ... ]";
    }

}