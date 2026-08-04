/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from Su
 */
public abstract class su_1 {
    protected ArrayList bLo = new ArrayList();

    public void clear() {
        this.bLo.clear();
    }

    public void reset() {
        this.bLo.clear();
    }

    public ArrayList afk() {
        return this.bLo;
    }

    protected abstract void k(ArrayList var1);

    public ArrayList q(ArrayList arrayList) {
        this.bLo.clear();
        this.k(arrayList);
        return this.bLo;
    }
}

