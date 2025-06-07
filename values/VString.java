package values;

public class VString implements IValue {
    private final String val;

    public VString(String val) {
        this.val = val;
    }

    @Override
    public String toStr() {
        return val;
    }
}
