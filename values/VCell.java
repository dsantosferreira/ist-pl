package values;

public class VCell implements IValue {
    IValue val;

    public VCell(IValue val) {
        this.val = val;
    }

    public IValue getVal() {
        return this.val;
    }

    public void storeVal(IValue val) {
        this.val = val;
    }

    @Override
    public String toStr() {
        return "ref@" + this;
    }
}
