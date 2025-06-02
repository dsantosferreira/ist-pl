package ASTTypes;

public class ASTTUnion implements ASTType {

    private TypeBindList ll;

    public ASTTUnion(TypeBindList llp) {
        ll = llp;
    }

    public String toStr() {
        return "union [ ... ]";
    }

}