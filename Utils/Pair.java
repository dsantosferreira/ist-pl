package Utils;

public class Pair <T, U> {
    private final T fst;
    private final U snd;

    public Pair(T fst, U snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public T getFst() {
        return fst;
    }

    public U getSnd() {
        return snd;
    }
}
