package values;

public class VOption implements IValue {
    String id;
    IValue expr;

    public VOption(String id, IValue expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public String toStr() {
        return id + "(" + expr.toStr() + ")";
    }

    public String getId() {
        return id;
    }

    public IValue getExpr() {
        return expr;
    }
}
