package values;

public class VBool implements IValue {
    boolean b;

    public VBool(boolean b) {
        this.b = b;
    }

    public boolean getVal() {
        return b;
    }

    public String toStr() {
        return Boolean.toString(b);
    }
}
