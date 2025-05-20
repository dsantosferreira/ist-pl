package values;

public class VList implements IValue {
    IValue head, tail;

    public VList(IValue head, IValue tail) {
        this.head = head; this.tail = tail;
    }

    public IValue getHead() {
        return this.head;
    }

    public IValue getTail() {
        return this.tail;
    }

    @Override
    public String toStr() {
        return head.toStr() + "::" + tail.toStr();
    }
}
