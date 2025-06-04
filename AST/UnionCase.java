package AST;

public class UnionCase {
    private String unionType, id;
    private ASTNode expr;

    public UnionCase(String unionType, String id, ASTNode expr) {
        this.unionType = unionType;
        this.id = id;
        this.expr = expr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass())
            return false;

        UnionCase other = (UnionCase) obj;

        return unionType.equals(other.unionType);
    }

    public ASTNode getExpr() {
        return expr;
    }

    public String getId() {
        return id;
    }

    public String getUnionType() {
        return unionType;
    }

    @Override
    public int hashCode() {
        return unionType.hashCode();
    }
}
